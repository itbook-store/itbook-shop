package shop.itbook.itbookshop.book.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.book.entity.Book;

/**
 * 도서 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    Optional<Book> findById(Long productNo);

    void deleteById(Long productNo);

    /**
     * ISBN으로 도서를 조회해 DB에 해당 도서가 존재하는지를 반환하는 기능을 담당합니다.
     *
     * @param isbn 조회할 도서(상품) isbn입니다.
     * @return isbn으로 조회한 도서의 존재 여부를 반환합니다.
     * @author 이하늬
     */

    boolean existsBookByIsbn(String isbn);

}
