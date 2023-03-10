package shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 관리자가 아닌 사용자의 배송 엔티티 관련 서비스 로직을 처리하기 위한 클래스입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface DeliveryService {
    /**
     * 배송 정보를 등록합니다.
     * 주문서 작성이 완료된 시점에 사용됩니다.
     * 배송 테이블에 값을 생성하고
     * 배송 상태 이력 테이블에 '배송 대기' 상태로 값을 생성합니다.
     *
     * @param order the order
     * @author 정재원 *
     */
    void registerDelivery(Order order);

    /**
     * 주문 번호를 받아 해당 주문의 운송장 번호를 조회합니다.
     *
     * @param orderNo 주문 번호
     * @return 운송장 번호
     * @author 정재원 *
     */
    String findTrackingNoByOrderNo(Long orderNo);

    /**
     * 주문의 배송 상태를 배송 완료로 변경합니다.
     *
     * @param orderNo 주문 번호
     * @author 정재원 *
     */
    void completeDelivery(Long orderNo);
}
