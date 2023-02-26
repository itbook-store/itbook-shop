package shop.itbook.itbookshop.ordergroup.order.service.base.nonmember;

import java.util.List;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderNonMemberService {
    OrderDetailsResponseDto findNonMemberOrderDetails(Long orderNo, String orderCode);

    List<OrderSubscriptionDetailsResponseDto> findNonMemberSubscriptionOrderDetails(Long orderNo,
                                                                                    String orderCode);
}
