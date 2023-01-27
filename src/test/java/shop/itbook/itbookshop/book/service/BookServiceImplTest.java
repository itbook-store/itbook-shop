package shop.itbook.itbookshop.book.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.impl.BookServiceImpl;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BookServiceImpl.class, FileService.class})
class BookServiceImplTest {

    @Autowired
    BookService bookService;

    @MockBean
    FileService fileService;

    @MockBean
    BookRepository mockBookRepository;

    BookRequestDto bookRequestDto;

    @BeforeEach
    void setUp() {
        bookRequestDto = new BookRequestDto("test", 1,
            "2022-01-25T12:12:12", false, null, "test", "test");

    }

    @Test
    @DisplayName("도서 등록 테스트")
    void addProductTest() {
        Long productNo = 1L;
        Book book = BookTransfer.dtoToEntityAdd(bookRequestDto, productNo);
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(book);

        Long actual = bookService.addBook(bookRequestDto, productNo);

        Assertions.assertThat(actual).isEqualTo(book.getProductNo());
    }
}