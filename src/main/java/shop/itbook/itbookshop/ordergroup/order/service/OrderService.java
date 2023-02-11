package shop.itbook.itbookshop.ordergroup.order.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
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
     * 결제 전 주문을 추가합니다.
     *
     * @param orderAddRequestDto 주문서에서 받아온 주문 등록할 정보 Dto
     * @param memberNo           회원 번호. 비회원일 경우 null
     * @return 결제 요청에 사용될 정보를 담은 Dto
     * @author 정재원 *
     */
    OrderPaymentDto addOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                          Optional<Long> memberNo);

    void cancelOrderBeforePayment(Long orderNo);

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
    Order completeOrderPay(Long orderNo);


    /**
     * 주문 상세보기 처리
     *
     * @param orderNo 조회할 주문 번호
     * @return 주문 상세보기에 필요한 정보를 담은 Dto
     * @author 정재원 *
     */
    OrderDetailsResponseDto findOrderDetails(Long orderNo);
}
