package shop.itbook.itbookshop.book.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;

/**
 * 쿼리 dsl을 사용하기 위한 BookRepository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface BookRepositoryCustom {

    /**
     * 모든 도서들의 상세 정보를 담아 리스트로 반환하는 기능을 담당합니다.
     *
     * @return 모든 도서 리스트를 반환합니다.
     * @author 이하늬
     */
    List<BookDetailsResponseDto> findBookList();

    /**
     * 도서(상품) 번호로 도서를 조회해 도서의 상세정보를 담아 반환하는 기능을 담당합니다.
     *
     * @param productNo 조회할 도서(상품) 번호입니다.
     * @return 조회한 도서의 상세정보를 반환합니다.
     * @author 이하늬
     */
    Optional<BookDetailsResponseDto> findBook(Long productNo);


}
