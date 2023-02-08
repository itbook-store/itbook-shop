package shop.itbook.itbookshop.ordergroup.orderproduct.dummy;

import static org.junit.jupiter.api.Assertions.*;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 정재원
 * @since 1.0
 */
public class OrderProductDummy {
    public static OrderProduct createOrderProduct(Order order, Product product) {
        return OrderProduct.builder()
            .order(order)
            .product(product)
            .count(1)
            .isHidden(false)
            .build();
    }
}