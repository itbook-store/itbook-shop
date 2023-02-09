package shop.itbook.itbookshop.bookmark.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;

/**
 * QueryDsl 을 사용하기위한 인터페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomBookmarkRepository {

    void deleteAllByMemberNo(Long memberNo);

    void deleteByMemberNoAndProductNo(Long memberNo, Long productNo);

    Page<BookmarkResponseDto> findAllProductDetailsByMemberNo(Pageable pageable,
                                                              Long memberNo);

}
