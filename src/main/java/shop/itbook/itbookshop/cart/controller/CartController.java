package shop.itbook.itbookshop.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.cart.service.CartService;

/**
 * 장바구니 RestAPI 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

}
