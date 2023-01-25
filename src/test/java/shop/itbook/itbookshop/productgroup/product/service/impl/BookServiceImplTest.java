package shop.itbook.itbookshop.productgroup.product.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
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
import shop.itbook.itbookshop.book.dto.request.AddBookRequestDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.book.service.adminapi.BookService;
import shop.itbook.itbookshop.book.service.adminapi.impl.BookServiceImpl;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

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

    AddBookRequestDto addBookRequestDto;

    @BeforeEach
    void setUp() {
        addBookRequestDto = new AddBookRequestDto("test", 1,
            "2022-01-25T12:12:12", false, null, "test", "test");

    }

    @Test
    @DisplayName("도서 등록 테스트")
    void addProductTest() {
        Long productNo = 1L;
        Book book = BookTransfer.dtoToEntityAdd(addBookRequestDto, productNo);
        given(mockBookRepository.save(any(Book.class)))
            .willReturn(book);

        Long actual = bookService.addBook(addBookRequestDto, productNo);

        Assertions.assertThat(actual).isEqualTo(book.getProductNo());
    }
}