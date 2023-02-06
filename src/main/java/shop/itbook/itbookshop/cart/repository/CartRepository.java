package shop.itbook.itbookshop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.cart.entity.Cart;

/**
 * 장바구니 레포지토리 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface CartRepository extends JpaRepository<Cart, Cart.Pk>, CustomCartRepository {
}

