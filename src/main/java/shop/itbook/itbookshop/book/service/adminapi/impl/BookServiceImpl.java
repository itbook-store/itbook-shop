package shop.itbook.itbookshop.book.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.book.dto.request.AddBookRequestDto;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.adminapi.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;

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
    public Long addBook(AddBookRequestDto requestDto, Long productNo) {
        Book book = BookTransfer.dtoToEntityAdd(requestDto, productNo);
        bookRepository.save(book);
        return productNo;
    }

    @Override
    public FindBookResponseDto findBook(Long id) {
        return bookRepository.findBook(id);
    }

    @Override
    public AddBookRequestDto toBookRequestDto(AddProductBookRequestDto requestDto) {
        return AddBookRequestDto.builder().isbn(requestDto.getIsbn())
            .pageCount(requestDto.getPageCount()).bookCreatedAt(requestDto.getBookCreatedAt())
            .isEbook(requestDto.isEbook()).ebookUrl(requestDto.getFileEbookUrl())
            .publisherName(requestDto.getPublisherName()).authorName(requestDto.getAuthorName())
            .build();
    }


}
