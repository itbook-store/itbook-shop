package shop.itbook.itbookshop.ordergroup.order.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class ProductStockIsZeroException extends RuntimeException {
    public static final String MESSAGE = "상품의 재고가 모두 소진되었습니다.";

}
