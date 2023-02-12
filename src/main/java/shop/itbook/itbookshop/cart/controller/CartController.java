package shop.itbook.itbookshop.cart.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.auth.annotation.IsAdmin;
import shop.itbook.itbookshop.auth.annotation.IsUser;
import shop.itbook.itbookshop.cart.dto.request.CartModifyRequestDto;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookshop.cart.resultmessage.CartResultMessageEnum;
import shop.itbook.itbookshop.cart.service.CartService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 장바구니 RestAPI 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니 상품 등록에 대한 API 입니다.
     *
     * @param cartRequestDto 회원번호, 상품번호
     * @return 성공시 StatusCode 201, return True
     * @author 강명관
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> productRegisterToCart(
        @Valid @RequestBody CartRequestDto cartRequestDto
    ) {

        boolean result = cartService.registerCart(cartRequestDto);

        SuccessfulResponseDto successfulResponseDto = new SuccessfulResponseDto();
        successfulResponseDto.setIsSuccessful(result);

        CommonResponseBody<SuccessfulResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CartResultMessageEnum.CART_REGISTER_SUCCESS_MESSAGE.getSuccessMessage()
            ),
            successfulResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 회원 번호를 통해 해당 장바구니의 상품의 디테일정보를 넘겨주는 API 입니다.
     *
     * @param memberNo 회원번호
     * @return StatusCode 200, 상품 상세 정보 리스트
     * @author 강명과
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<List<CartProductDetailsResponseDto>>> cartGetProductList(
        @PathVariable(value = "memberNo") Long memberNo
    ) {

        List<CartProductDetailsResponseDto> productList = cartService.getProductList(memberNo);

        CommonResponseBody<List<CartProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CartResultMessageEnum.CART_LIST_SUCCESS_MESSAGE.getSuccessMessage()
                ),
                productList
            );

        cartService.deleteAllProduct(memberNo);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 회원 넘버, 상품 넘버를 받아 장바구니의 해당 상품을 삭제하는 API 입니다.
     *
     * @param cartRequestDto 회워번호, 상품번호
     * @return 성공시 204 status 코드와, 메시지 반환
     * @author 강명관
     */
    @DeleteMapping
    public ResponseEntity<CommonResponseBody<Void>> cartDeleteProduct(
        @Valid @RequestBody CartRequestDto cartRequestDto
    ) {

        cartService.deleteProduct(cartRequestDto);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CartResultMessageEnum.CART_DELETE_SUCCESS_MESSAGE.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }

    /**
     * 회원 번호를 통해 해당 회원의 장바구니 상품을 모두 삭제는 API 입니다.
     *
     * @param memberNo 회원번호
     * @return StatuesCode 200, message
     * @author 강명관
     */
    @DeleteMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<Void>> cartDeleteAllProduct(
        @PathVariable(value = "memberNo") Long memberNo
    ) {

        cartService.deleteAllProduct(memberNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CartResultMessageEnum.CART_DELETE_ALL_SUCCESS_MESSAGE.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }

    /**
     * 회원번호, 상품번호, 상품갯수를 DTO로 받아 해당 장바구니의 상품의 갯수를 수정하는 API 입니다.
     *
     * @param cartModifyRequestDto 회원번호, 상품번호, 상품갯수
     * @return StatusCode 200, message
     * @author 강명관
     */
    @PutMapping
    public ResponseEntity<CommonResponseBody<Void>> cartModifyProductCount(
        @Valid @RequestBody CartModifyRequestDto cartModifyRequestDto
    ) {

        log.info("cartModifyRequestDto {}", cartModifyRequestDto);

        cartService.modifyProductCount(cartModifyRequestDto);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CartResultMessageEnum.CART_MODIFY_PRODUCT_COUNT_SUCCESS_MESSAGE.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/save-all")
    public ResponseEntity<CommonResponseBody<Void>> cartSaveAllProduct(
        @RequestBody List<CartModifyRequestDto> cartRequestDtoList
    ) {

        cartService.saveAllCartProduct(cartRequestDtoList);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CartResultMessageEnum.CART_REGISTER_SUCCESS_MESSAGE.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
