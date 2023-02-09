package shop.itbook.itbookshop.ordergroup.order.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {
    /**
     * 회
     *
     * @param orderAddRequestDto the order add request dto
     * @param memberNo           the member no
     * @return the order payment dto
     */
    OrderPaymentDto addOrder(OrderAddRequestDto orderAddRequestDto,
                             Optional<Long> memberNo);

    /**
     * Find order list of member with status page.
     *
     * @param pageable the pageable
     * @param memberNo the member no
     * @return the page
     */
    Page<OrderListMemberViewResponseDto> findOrderListOfMemberWithStatus(Pageable pageable,
                                                                         Long memberNo);

    /**
     * Complete order pay.
     *
     * @param orderNo the order no
     */
    void completeOrderPay(Long orderNo);
}
