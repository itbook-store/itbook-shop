package shop.itbook.itbookshop.book.service.adminapi.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.adminapi.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;

/**
 * BookService 인터페이스를 구현한 도서 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<FindBookResponseDto> findBookList() {
        List<FindBookResponseDto> bookList = bookRepository.findBookList();
        for (FindBookResponseDto b : bookList) {
            b.setSelledPrice((long) (b.getFixedPrice() * ((100 - b.getDiscountPercent()) * 0.01)));
            String fileThumbnailsUrl = b.getFileThumbnailsUrl();
            b.setThumbnailsName(
                fileThumbnailsUrl.substring(fileThumbnailsUrl.lastIndexOf("/") + 1));
        }
        return bookList;
    }

    @Override
    @Transactional
    public Long addBook(BookRequestDto requestDto, Long productNo) {
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        return productNo;
    }

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
        book.setIsEbook(requestDto.isEbook());
        book.setEbookUrl(requestDto.getEbookUrl());
        book.setPublisherName(requestDto.getPublisherName());
        book.setAuthorName(requestDto.getAuthorName());

        return book;
    }

    @Override
    public FindBookResponseDto findBook(Long id) {
        return bookRepository.findBook(id);
    }

    @Override
    public Book findBookEntity(Long productNo) {
        return bookRepository.findById(productNo)
            .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public BookRequestDto toBookRequestDto(ProductBookRequestDto requestDto) {
        return BookRequestDto.builder().isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount()).bookCreatedAt(requestDto.getBookCreatedAt())
            .isEbook(requestDto.isEbook()).ebookUrl(requestDto.getFileEbookUrl())
            .publisherName(requestDto.getPublisherName()).authorName(requestDto.getAuthorName())
            .build();
    }


}
