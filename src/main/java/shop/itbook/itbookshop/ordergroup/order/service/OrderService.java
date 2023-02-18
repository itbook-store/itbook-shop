package shop.itbook.itbookshop.ordergroup.order.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {

    /**
     * 주문 번호로 조회한 Order 엔티티의 인스턴스를 반환합니다
     *
     * @param orderNo 조회할 주문 번호
     * @return 조회에 성공한 주문 엔티티 인스턴스
     * @author 정재원 *
     */
    Order findOrderEntity(Long orderNo);

    /**
     * 배송 번호로 주문 엔티티를 찾습니다.
     *
     * @param deliveryNo 배송 번호
     * @return 조회에 성공한 주문 엔티티 인스턴스
     * @author 정재원 *
     */
    Optional<Order> findOrderByDeliveryNo(Long deliveryNo);

    /**
     * 구독 주문 인지 검사합니다.
     *
     * @param orderNo the order no
     * @return 구독이면 true 아니면 false
     * @author 정재원 *
     */
    boolean isSubscription(Long orderNo);

    /**
     * 결제 전 주문을 추가합니다.
     *
     * @param orderAddRequestDto 주문서에서 받아온 주문 정보 Dto
     * @param memberNo           회원 번호. 비회원일 경우 null
     * @return 결제 요청에 사용될 정보를 담은 Dto
     * @author 정재원 *
     */
    OrderPaymentDto addOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                          Optional<Long> memberNo);

    /**
     * 결제 전 구독 주문을 추가합니다.
     *
     * @param orderAddRequestDto 주문서에서 받아온 주문 정보 Dto
     * @param memberNo           회원 번호. 비회원일 경우 null
     * @return 결제 요청에 사용될 정보를 담은 Dto
     * @author 정재원 *
     */
    OrderPaymentDto addOrderSubscriptionBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                      Optional<Long> memberNo);

    /**
     * 결제 완료 후 구독 주문의 결제 정보를 등록합니다.
     *
     * @param orderNo 구독 시작 번호
     * @author 정재원 *
     */
    void addOrderSubscriptionAfterPayment(Long orderNo);

    /**
     * 결제를 완료하지 않은 주문에 대해 다시 주문을 진행합니다.
     *
     * @param orderAddRequestDto 주문서에서 받아온 주문 정보 Dto
     * @param orderNo            재주문 할 주문 번호 - 결제 대기 상태
     * @return 결제 요청에 사용될 정보를 담은 Dto
     * @author 정재원 *
     */
    OrderPaymentDto reOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                         Long orderNo);

    /**
     * Process after order cancel payment success.
     *
     * @param orderNo the order no
     * @author 정재원 *
     */
    void processBeforeOrderCancelPayment(Long orderNo);

    /**
     * 회원의 주문 리스트를 조회합니다.
     * 다양한 상태들이 포함됩니다.
     *
     * @param pageable 페이징을 위한 객체
     * @param memberNo 조회할 회원의 번호
     * @return 회원의 주문 리스트 페이지 객체
     * @author 정재원 *
     */
    Page<OrderListMemberViewResponseDto> findOrderListOfMemberWithStatus(Pageable pageable,
                                                                         Long memberNo);

    /**
     * 결제 완료후 로직 처리를 진행합니다.
     *
     * @param orderNo 결제 완료 처리할 주문 번호
     * @return 결제 완료된 주문 엔티티의 인스턴스
     * @author 정재원 *
     */
    Order processAfterOrderPaymentSuccess(Long orderNo);


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
     * @author 정재원 *
     */
    Page<OrderListAdminViewResponseDto> findOrderListAdmin(Pageable pageable);

    /**
     * 주문 구문확정 처리.
     *
     * @param orderNo 주문번호.
     * @author 강명관 *
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
}
