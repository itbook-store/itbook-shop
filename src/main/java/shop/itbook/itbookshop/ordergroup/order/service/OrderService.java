package shop.itbook.itbookshop.ordergroup.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {
    void addOrderOfMember(OrderAddRequestDto orderAddRequestDto, Long memberNo);

    Page<OrderListMemberViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                        Long memberNo);
}
