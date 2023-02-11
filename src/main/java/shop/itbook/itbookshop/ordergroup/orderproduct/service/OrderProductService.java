package shop.itbook.itbookshop.ordergroup.orderproduct.service;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 주문 상품 엔티티의 비즈니스 로직을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderProductService {
    /**
     * 주문 등록 시 주문_상품 등록 처리를 합니다.
     *
     * @param order        주문 엔티티
     * @param product      상품 엔티티
     * @param productCnt   상품 개수
     * @param productPrice 상품의 결제할 가격
     * @author 정재원 *
     */
    void processOrderProduct(Order order, Product product, Integer productCnt,
                             Long productPrice);
}
