package shop.itbook.itbookshop.ordergroup.order.service.nonmember.impl;

import java.util.List;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.nonmember.OrderNonMemberService;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderNonMemberServiceImpl implements OrderNonMemberService {

    private OrderRepository orderRepository;

    @Override
    public OrderDetailsResponseDto findNonMemberOrderDetails(Long orderNo, String orderCode) {
        return null;
    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findNonMemberSubscriptionOrderDetails(
        Long orderNo, String orderCode) {
        return null;
    }
}
