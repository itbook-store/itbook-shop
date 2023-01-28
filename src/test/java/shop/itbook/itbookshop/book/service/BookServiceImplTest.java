package shop.itbook.itbookshop.book.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.book.dto.request.BookRequestDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.impl.BookServiceImpl;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

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

    ProductBookRequestDto bookRequestDto;
    ProductBookRequestDto modifyBookRequestDto;

    @BeforeEach
    void setUp() {
        bookRequestDto = ProductBookRequestDummy.getProductBookRequest();
        modifyBookRequestDto = ProductBookRequestDummy.getProductBookRequest();
        ReflectionTestUtils.setField(modifyBookRequestDto, "isbn", "test-isbn");
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void add_Product() {
        BookRequestDto bookRequestDto =
            bookService.toBookRequestDto(ProductBookRequestDummy.getProductBookRequest());

        Long productNo = 1L;
        Book book = BookTransfer.dtoToEntityAdd(bookRequestDto, productNo);
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(book);

        Long actual = bookService.addBook(bookRequestDto, productNo);

        Assertions.assertThat(actual).isEqualTo(book.getProductNo());
    }

    @Test
    @DisplayName("도서 수정 테스트")
    void modify_Product() {
        Book book = mock(Book.class);
        BookRequestDto bookRequestDto =
            bookService.toBookRequestDto(modifyBookRequestDto);
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.of(book));
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(BookTransfer.dtoToEntityAdd(bookRequestDto, anyLong()));

        bookService.modifyBook(bookRequestDto, 1L);

        then(mockBookRepository).should().findById(anyLong());
        then(book).should().setIsbn(anyString());
        then(book).should().setPageCount(anyInt());
        then(book).should().setAuthorName(anyString());
        then(book).should().setIsEbook(anyBoolean());
    }

    @Test
    @DisplayName("도서 단건 조회 시 실패 테스트 - 도서가(상품이) 없을 시 예외 발생")
    void find_BookEntity_Failure() {
        given(mockBookRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> bookService.findBookEntity(1L))
            .isInstanceOf(BookNotFoundException.class)
            .hasMessage(BookNotFoundException.MESSAGE);
    }

//    @Test
//    @DisplayName("상품 엔티티 단건 조회 테스트")
//    void find_ProductEntity_Success() {
//        BookRequestDto bookRequestDto =
//            bookService.toBookRequestDto(ProductBookRequestDummy.getProductBookRequest());
//
//        Book book = BookTransfer.dtoToEntityAdd(bookRequestDto, anyLong());
//
//        given(mockBookRepository.findById(anyLong())).willReturn(Optional.of(product));
//
//        Optional<Product> actualProduct = mockProductRepository.findById(1L);
//
//        Assertions.assertThat(actualProduct).isPresent();
//        Assertions.assertThat(actualProduct.get().getProductNo())
//            .isEqualTo(product.getProductNo());
//    }

}