package shop.itbook.itbookshop.ordergroup.orderproduct.service;

import java.util.List;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
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
     * @return
     * @author 정재원 *
     */
    OrderProduct addOrderProduct(Order order, Product product, Integer productCnt,
                                 Long productPrice);

    /**
     * 주문의 개별 상품 정보 리스트를 반환합니다.
     *
     * @param orderNo 주문 번호
     * @return 주문 상품의 정보를 담은 Dto 리스트
     * @author 정재원 *
     */
    List<OrderProductDetailResponseDto> findOrderProductsByOrderNo(Long orderNo);

    List<OrderProduct> findOrderProductsEntityByOrderNo(Long orderNo);
}
