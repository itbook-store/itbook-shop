package shop.itbook.itbookshop.book.repository.impl;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.dummy.BookDummy;
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
class BookRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    TestEntityManager entityManager;

    Product dummyProduct;

    Book dummyBook;


    @BeforeEach
    void setUp() {
        dummyProduct = ProductDummy.getProductSuccess();
        Product savedProduct = productRepository.save(dummyProduct);
        dummyBook = BookDummy.getBookSuccess();
        dummyBook.setProductNo(savedProduct.getProductNo());
        bookRepository.save(dummyBook);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("도서(상품) 번호로 도서 조회 성공 테스트")
    void Find_Book_ByProductNo() {

        Optional<Book> book =
            bookRepository.findById(dummyBook.getProductNo());

        Assertions.assertThat(book).isPresent();
        Assertions.assertThat(book.get().getProductNo())
            .isEqualTo(dummyBook.getProductNo());
    }

    @Test
    @DisplayName("도서(상품) 번호로 도서 및 상품 상세 정보 조회 성공 테스트")
    void Find_BookDetails_ByProductNo() {

        Optional<BookDetailsResponseDto> book =
            bookRepository.findBook(dummyBook.getProductNo());

        Assertions.assertThat(book).isPresent();
        Assertions.assertThat(book.get().getProductNo())
            .isEqualTo(dummyBook.getProductNo());
        Assertions.assertThat(book.get().getIsbn())
            .isEqualTo(dummyBook.getIsbn());
    }

    @Test
    @DisplayName("도서 수정 테스트")
    void Modify_Book() {
        dummyBook.setIsbn("modify-isbn");
        bookRepository.save(dummyBook);
        Assertions.assertThatNoException();
        Assertions.assertThat(dummyBook.getIsbn()).isEqualTo("modify-isbn");
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void Delete_Book_ByProductNo() {
        bookRepository.deleteById(dummyBook.getProductNo());
        Optional<Book> book =
            bookRepository.findById(dummyBook.getProductNo());
        Assertions.assertThat(book).isNotPresent();
    }

    @Test
    @DisplayName("모든 도서 리스트 조회 성공 테스트")
    void Find_BookList() {

        List<BookDetailsResponseDto> bookList = bookRepository.findBookList();
        Assertions.assertThat(bookList).isNotEmpty();
        BookDetailsResponseDto productDetailsResponseDtoActual = bookList.get(0);

        Assertions.assertThat(bookList).hasSize(1);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyBook.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsbn())
            .isEqualTo(dummyBook.getIsbn());
    }
}