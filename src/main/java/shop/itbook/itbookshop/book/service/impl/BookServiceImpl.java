package shop.itbook.itbookshop.book.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookBooleanResponseDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.service.AladinApiService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * BookService 인터페이스를 구현한 도서 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final FileService fileService;
    private final ProductService productService;
    private final BookRepository bookRepository;
    private final AladinApiService aladinApiService;

    @Value("${object.storage.folder-path.ebook}")
    private String folderPathEbook;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDetailsResponseDto> findBookList(boolean isFiltered) {
        List<BookDetailsResponseDto> bookList = bookRepository.findBookList();
        for (BookDetailsResponseDto book : bookList) {
            setExtraFields(book);
        }

        if (isFiltered) {
            return bookList.stream()
                .filter(product -> product.getIsSelled() == Boolean.TRUE)
                .collect(Collectors.toList());
        }

        return bookList;
    }

    private void setExtraFields(BookDetailsResponseDto book) {
        book.setSelledPrice(
            (long) (book.getFixedPrice() * ((100 - book.getDiscountPercent()) * 0.01)));
        String fileThumbnailsUrl = book.getFileThumbnailsUrl();
        book.setThumbnailsName(
            fileThumbnailsUrl.substring(fileThumbnailsUrl.lastIndexOf("/") + 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addBook(ProductBookRequestDto requestDto, MultipartFile thumbnails,
                        MultipartFile ebook) {

        Long productNo =
            productService.addProduct(productService.toProductRequestDto(requestDto), thumbnails);

        if (Objects.nonNull(ebook)) {
            uploadAndSetFile(requestDto, ebook);
        }

        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);

        return productNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyBook(Long productNo, ProductBookRequestDto requestDto, MultipartFile ebook) {
        if (!Objects.isNull(ebook)) {
            uploadAndSetFile(requestDto, ebook);
        }

        BookRequestDto bookRequestDto = this.toBookRequestDto(requestDto);

        Book book = updateBook(bookRequestDto, productNo);
        bookRepository.save(book);
    }

    private void uploadAndSetFile(ProductBookRequestDto requestDto, MultipartFile ebook) {
        String ebookUrl = fileService.uploadFile(ebook, folderPathEbook);
        requestDto.setFileEbookUrl(ebookUrl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookBooleanResponseDto isExistsInDBByIsbn(String isbn) {
        return new BookBooleanResponseDto(bookRepository.existsBookByIsbn(isbn));
    }

    @Override
    public BookBooleanResponseDto isExistsInAladinByIsbn(String isbn) {

        if (!Objects.isNull(aladinApiService.getBookDetails(isbn))) {
            return new BookBooleanResponseDto(Boolean.TRUE);
        }
        return new BookBooleanResponseDto(Boolean.FALSE);
    }

    private Book updateBook(shop.itbook.itbookshop.book.dto.request.BookRequestDto requestDto,
                            Long productNo) {
        Book book = this.findBookEntity(productNo);

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(requestDto.getBookCreatedAt(), DATEFORMATTER);
        LocalDateTime date = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        book.setIsbn(requestDto.getIsbn());
        book.setPageCount(requestDto.getPageCount());
        book.setBookCreatedAt(date);
        book.setIsEbook(requestDto.getIsEbook());
        book.setEbookUrl(requestDto.getEbookUrl());
        book.setPublisherName(requestDto.getPublisherName());
        book.setAuthorName(requestDto.getAuthorName());

        return book;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookDetailsResponseDto findBook(Long productNo) {
        BookDetailsResponseDto book = bookRepository.findBook(productNo)
            .orElseThrow(BookNotFoundException::new);
        this.setExtraFields(book);
        return book;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book findBookEntity(Long productNo) {
        return bookRepository.findById(productNo)
            .orElseThrow(BookNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public shop.itbook.itbookshop.book.dto.request.BookRequestDto toBookRequestDto(
        ProductBookRequestDto requestDto) {
        return shop.itbook.itbookshop.book.dto.request.BookRequestDto.builder()
            .isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount())
            .bookCreatedAt(requestDto.getBookCreatedAt())
            .isEbook(requestDto.getIsEbook())
            .ebookUrl(requestDto.getFileEbookUrl())
            .publisherName(requestDto.getPublisherName())
            .authorName(requestDto.getAuthorName())
            .build();
    }


}
