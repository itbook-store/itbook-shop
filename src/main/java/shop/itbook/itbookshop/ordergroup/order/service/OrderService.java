package shop.itbook.itbookshop.ordergroup.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListViewResponseDto;

/**
 * 주문 관련 비즈니스 로직을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderService {
    Page<OrderListViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                  Long memberNo);
}
