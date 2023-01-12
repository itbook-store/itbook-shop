package shop.itbook.itbookshop.book.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 도서와 관련하여 DB 기능 처리를 담당하는 상품 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long productNo);

    void deleteById(Long productNo);

}
