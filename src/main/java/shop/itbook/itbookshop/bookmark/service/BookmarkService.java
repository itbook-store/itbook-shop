package shop.itbook.itbookshop.bookmark.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 즐겨찾기 비지니스 로직을 담당하는 서비스 인터페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface BookmarkService {

    /**
     * 증겨찾기에 상품을 등록하는 메서드 입니다.
     *
     * @param bookmarkRequestDto 회원번호, 상품번호
     * @return 등록되면 true, 등록 되지 않으면 false
     * @author 강명관
     */
    boolean addProductInBookmark(BookmarkRequestDto bookmarkRequestDto);


    /**
     * 즐겨찾기에 특정 상품을 삭제하는 메서드 입니다.
     *
     * @param bookmarkRequestDto 회원번호, 상품번호
     * @author 강명관
     */
    void deleteProductInBookmark(BookmarkRequestDto bookmarkRequestDto);


    /**
     * 즐겨찾기에 해당 회원의 모든 상품을 삭제하는 메서드 입니다.
     *
     * @param memberNo 회원번호
     * @author 강명관
     */
    void deleteAllProductInBookmark(Long memberNo);

    /**
     * 회원번호를 통해 회원의 즐겨찾기 상품 리스트를 가져오는 메서드 입니다.
     *
     * @param pageable 페이징을 위한 객체입니다.
     * @param memberNo 회원 번호
     * @return 페이징 처리된 상품 정보 리스트
     */
    Page<ProductDetailsResponseDto> getAllProductInBookmark(Pageable pageable, Long memberNo);


}
