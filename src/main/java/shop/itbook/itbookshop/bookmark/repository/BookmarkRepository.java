package shop.itbook.itbookshop.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.bookmark.entity.Bookmark;

/**
 * 즐겨찾기 엔티티에 대한 JPA Repository 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface BookmarkRepository
    extends JpaRepository<Bookmark, Long>, CustomBookmarkRepository {

    boolean existsByMember_MemberNoAndProduct_ProductNo(Long memberNo, Long productNo);
}
