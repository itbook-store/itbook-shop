package shop.itbook.itbookshop.book.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

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

    private final BookRepository bookRepository;

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
                .filter(product -> product.getIsExposed() == Boolean.TRUE)
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
    public Long addBook(BookRequestDto requestDto, Long productNo) {
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        return productNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyBook(BookRequestDto requestDto, Long productNo) {
        Book book = updateBook(requestDto, productNo);
        bookRepository.save(book);
    }

    private Book updateBook(BookRequestDto requestDto, Long productNo) {
        Book book = this.findBookEntity(productNo);

        book.setIsbn(requestDto.getIsbn());
        book.setPageCount(requestDto.getPageCount());
        book.setBookCreatedAt(LocalDateTime.parse(requestDto.getBookCreatedAt()));
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
    public BookRequestDto toBookRequestDto(ProductBookRequestDto requestDto) {
        return BookRequestDto.builder().isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount()).bookCreatedAt(requestDto.getBookCreatedAt())
            .isEbook(requestDto.getIsEbook()).ebookUrl(requestDto.getFileEbookUrl())
            .publisherName(requestDto.getPublisherName()).authorName(requestDto.getAuthorName())
            .build();
    }


}
