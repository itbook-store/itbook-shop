package shop.itbook.itbookshop.cart.service;

import java.util.List;
import shop.itbook.itbookshop.cart.dto.request.CartModifyRequestDto;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;

/**
 * 장바구니 서비스 인터스페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface CartService {

    /**
     * 장바구니에 상품을 등록하는 메서드입니다.
     *
     * @param cartRequestDto cartRequestDto
     * @return 이미 등록되어있으면 false, 등록되어 있지 않다면 등록 true
     * @author 강명관
     */
    boolean registerCart(CartRequestDto cartRequestDto);

    /**
     * 회원번호를 통해 회원의 장바구니 상품 리스트를 가져오는 메서드 입니다.
     *
     * @param memberNo 회원번호
     * @return 상품 상세 정보 리스트
     * @author 강명관
     */
    List<CartProductDetailsResponseDto> getProductList(Long memberNo);

    /**
     * 회원번호와, 상품번호를 통해 장바구니의 특정 상품을 삭제하는 메서드 입니다.
     *
     * @param cartRequestDto 회원번호, 상품번호
     * @author 강명관
     */
    void deleteProduct(CartRequestDto cartRequestDto);


    /**
     * 회원번호를 통해 해당 회원의 장바구니 상품을 모두 삭제하는 메서드 입니다.
     *
     * @param memberNo 회원번호
     * @author 강명관
     */
    void deleteAllProduct(Long memberNo);


    /**
     * DTO를 통해 넘어온 정보로 장바구니 상품의 갯수를 수정하는 메서드 입니다.
     *
     * @param cartModifyRequestDto 회원번호, 상품번호, 상품 갯수
     * @author 강명관
     */
    void modifyProductCount(CartModifyRequestDto cartModifyRequestDto);


    /**
     * 스케쥴러를 통해넘어온 정보를 모두 저장하는 메서드 입니다.
     *
     * @param cartRequestDtoList 회원번호, 상품번호, 상품 카운트 세개를 갖고 있는 리스트 입니다.
     */
    void saveAllCartProduct(List<CartModifyRequestDto> cartRequestDtoList);
}
