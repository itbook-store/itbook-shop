package shop.itbook.itbookshop.book.repository.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.book.BookDummy;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    Product dummyProductSuccess;

    Book dummyBookSuccess;

//    static final Integer DATA_SIZE = 5;


    @BeforeEach
    void setUp() {
        dummyProductSuccess = ProductDummy.getProductSuccess();
        Product savedProduct = productRepository.save(dummyProductSuccess);
        dummyBookSuccess = BookDummy.getBookSuccess();
        dummyBookSuccess.setProductNo(savedProduct.getProductNo());
        bookRepository.save(dummyBookSuccess);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("도서(상품) 번호로 도서 조회 성공 테스트")
    void Find_Book_ByProductNo() {

        Optional<Book> book =
            bookRepository.findById(dummyBookSuccess.getProductNo());

        Assertions.assertThat(book).isPresent();
        Assertions.assertThat(book.get().getProductNo())
            .isEqualTo(dummyBookSuccess.getProductNo());
    }

    @Test
    @DisplayName("도서 수정 테스트")
    void Modify_Book() {
        dummyBookSuccess.setIsbn("modify-isbn");
        bookRepository.save(dummyBookSuccess);
        Assertions.assertThatNoException();
        Assertions.assertThat(dummyBookSuccess.getIsbn()).isEqualTo("modify-isbn");
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void Delete_Book_ByProductNo() {
        bookRepository.deleteById(dummyBookSuccess.getProductNo());
        Optional<Book> book =
            bookRepository.findById(dummyBookSuccess.getProductNo());
        Assertions.assertThat(book).isNotPresent();
    }

    @Test
    @DisplayName("모든 도서 리스트 조회 성공 테스트")
    void Find_BookList() {

        List<BookDetailsResponseDto> bookList = bookRepository.findBookList();
        Assertions.assertThat(bookList).isNotEmpty();
//        BookDetailsResponseDto productDetailsResponseDtoActual = bookList.get(DATA_SIZE);
//
//        Assertions.assertThat(bookList).hasSize(DATA_SIZE + 1);
//        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
//            .isEqualTo(dummyBookSuccess.getProductNo());
//        Assertions.assertThat(productDetailsResponseDtoActual.getIsbn())
//            .isEqualTo(dummyBookSuccess.getIsbn());
    }
}