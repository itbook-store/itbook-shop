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
import shop.itbook.itbookshop.book.dto.request.BookAddRequestDto;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookBooleanResponseDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.AladinApiService;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

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
    private final ProductCategoryService productCategoryService;
    private final BookRepository bookRepository;
    private final AladinApiService aladinApiService;

    @Value("${object.storage.folder-path.ebook}")
    private String folderPathEbook;

    @Value("${object.storage.folder-path.thumbnail}")
    private String folderPathThumbnail;

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
            String fileUrl = fileService.uploadFile(ebook, folderPathEbook);
            requestDto.setFileEbookUrl(fileUrl);
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
    public void modifyBook(Long productNo, BookModifyRequestDto requestDto,
                           MultipartFile thumbnails, MultipartFile ebook) {

        if (!Objects.isNull(thumbnails)) {
            String fileUrl = fileService.uploadFile(thumbnails, folderPathThumbnail);
            requestDto.setFileThumbnailsUrl(fileUrl);
        }

        if (!Objects.isNull(ebook)) {
            String fileUrl = fileService.uploadFile(ebook, folderPathEbook);
            requestDto.setFileEbookUrl(fileUrl);
        }

        Product product = productService.updateProduct(requestDto, productNo);
        Book book = this.updateBook(requestDto, productNo, product);

        if (!Objects.isNull(requestDto.getCategoryNoList())) {
            productCategoryService.modifyProductCategory(product, requestDto.getCategoryNoList());
        }
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
    public BookAddRequestDto toBookRequestDto(
        ProductBookRequestDto requestDto) {
        return BookAddRequestDto.builder()
            .isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount())
            .bookCreatedAt(requestDto.getBookCreatedAt())
            .isEbook(requestDto.getIsEbook())
            .ebookUrl(requestDto.getFileEbookUrl())
            .publisherName(requestDto.getPublisherName())
            .authorName(requestDto.getAuthorName())
            .build();
    }

    public Book updateBook(BookModifyRequestDto requestDto, Long productNo, Product product) {
        Book book = this.findBookEntity(productNo);

        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(requestDto.getBookCreatedAt(), DATEFORMATTER);
        LocalDateTime date = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        book.setProduct(product);
        book.setIsbn(requestDto.getIsbn());
        book.setPageCount(requestDto.getPageCount());
        book.setBookCreatedAt(date);
        if (!Objects.isNull(requestDto.getFileEbookUrl())) {
            book.setEbookUrl(requestDto.getFileEbookUrl());
        }
        book.setPublisherName(requestDto.getPublisherName());
        book.setAuthorName(requestDto.getAuthorName());
        bookRepository.save(book);
        return book;
    }
}
