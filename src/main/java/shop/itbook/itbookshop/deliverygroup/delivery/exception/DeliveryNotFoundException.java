package shop.itbook.itbookshop.deliverygroup.delivery.exception;

/**
 * 찾으려는 배송 정보가 없을 경우 발생하는 예외입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException() {
        super("배송 정보가 없습니다");
    }
}
