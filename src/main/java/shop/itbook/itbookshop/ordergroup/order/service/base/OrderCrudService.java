package shop.itbook.itbookshop.ordergroup.order.service.base;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderCrudService {

    /**
     * 주문 번호로 조회한 Order 엔티티의 인스턴스를 반환합니다
     *
     * @param orderNo 조회할 주문 번호
     * @return 조회에 성공한 주문 엔티티 인스턴스
     * @author 정재원
     */
    Order findOrderEntity(Long orderNo);

    /**
     * 배송 번호로 주문 엔티티를 찾습니다.
     *
     * @param deliveryNo 배송 번호
     * @return 조회에 성공한 주문 엔티티 인스턴스
     * @author 정재원
     */
    Optional<Order> findOrderByDeliveryNo(Long deliveryNo);


    /**
     * 회원의 주문 리스트를 조회합니다.
     * 다양한 상태들이 포함됩니다.
     *
     * @param pageable 페이징을 위한 객체
     * @param memberNo 조회할 회원의 번호
     * @return 회원의 주문 리스트 페이지 객체
     * @author 정재원
     */
    Page<OrderListMemberViewResponseDto> findOrderListOfMemberWithStatus(Pageable pageable,
                                                                         Long memberNo);

    /**
     * 주문 상세보기 처리
     *
     * @param orderNo 조회할 주문 번호
     * @return 주문 상세보기에 필요한 정보를 담은 Dto
     * @author 정재원, 강명관 *
     */
    OrderDetailsResponseDto findOrderDetails(Long orderNo);

    /**
     * 관리자에게 보여질 주문 목록을 찾습니다.
     *
     * @param pageable 페이징을 위한 객체
     * @return 요청 받은 관리자 목록의 페이지
     * @author 정재원
     */
    Page<OrderListAdminViewResponseDto> findOrderListAdmin(Pageable pageable);

    /**
     * 주문 구문확정 처리.
     *
     * @param orderNo 주문번호.
     * @author 강명관
     */
    void orderPurchaseComplete(Long orderNo);

    /**
     * 구독 상품 결제완료인 상태의 주문을 매달 배송 대기로 만들기 위한 메서드입니다.
     *
     * @author 강명관
     */
    void addOrderStatusHistorySubscriptionProductDeliveryWait();

    /**
     * 관리자가 모든 구독 상품 주문 리스트에 대해 조회하는 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @return 페징된 DTO 객체.
     * @author 강명관
     */
    Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListByAdmin(Pageable pageable);

    /**
     * 회원이 마이페이지 에서 구독 상품 주문 리스트에 대해 조회하는 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @param memberNo 회원 번호
     * @return 페이징된 DTO 객체.
     * @author 강명관
     */
    Page<OrderSubscriptionListDto> findAllSubscriptionOrderListByMember(Pageable pageable,
                                                                        Long memberNo);

    /**
     * 구독 주문의 상세 정보를 가져 옵니다.
     *
     * @param orderNo 구독 주문의 첫 주문의 주문 번호
     * @return 해당 주문 구독 상품의 리스트.
     */
    List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsResponseDto(
        Long orderNo);

    void deleteOrderAndRollBackStock(Long orderNo);
}
