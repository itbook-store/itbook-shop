package shop.itbook.itbookshop.cart.dummy;

import shop.itbook.itbookshop.cart.entity.Cart;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 강명관
 * @since 1.0
 */
public class CartDummy {
    private CartDummy() {

    }

    public static Cart getCart(Member member, Product product) {
        return new Cart(member, product);
    }


}
