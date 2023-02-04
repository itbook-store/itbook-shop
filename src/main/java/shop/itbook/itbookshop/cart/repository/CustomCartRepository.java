package shop.itbook.itbookshop.cart.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;

/**
 * 장바구니 queryDsl을 사용하기위한 Custom Repository 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCartRepository {

    /**
     * 회원번호를 통해 장바구니 상품을 조회하는 쿼리 입니다.
     *
     * @param memberNo 회원번호
     * @return 상품 상세 정보 리스트
     * @author 강명관
     */
    List<CartProductDetailsResponseDto> findProductCartListByMemberNo(Long memberNo);

    /**
     * 회원번호를 통해 상품을 삭제하는 쿼리 입니다.
     *
     * @param memberNo 회원번호
     * @author 강명관
     */
    void deleteAllByMemberNo(Long memberNo);

}
