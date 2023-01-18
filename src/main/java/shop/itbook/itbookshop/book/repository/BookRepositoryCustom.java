package shop.itbook.itbookshop.book.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface BookRepositoryCustom {
    List<FindBookResponseDto> findBookList();

    FindBookResponseDto findBook(Long id);
}
