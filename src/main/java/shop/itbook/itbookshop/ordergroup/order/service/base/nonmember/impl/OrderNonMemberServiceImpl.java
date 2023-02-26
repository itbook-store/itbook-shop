package shop.itbook.itbookshop.ordergroup.order.service.base.nonmember.impl;

import static shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudServiceImpl.setSellingPriceOfDetailsDto;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.exception.InvalidOrderCodeException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.base.nonmember.OrderNonMemberService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderNonMemberServiceImpl implements OrderNonMemberService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDetailsResponseDto findNonMemberOrderDetails(Long orderNo, String orderCode) {
        OrderDetailsResponseDto orderDetailsResponseDto =
            orderRepository.findOrderDetailOfNonMember(orderNo);

        setSellingPriceOfDetailsDto(orderDetailsResponseDto);

        if (Objects.isNull(orderDetailsResponseDto.getNonMemberOrderCode())
            || !orderDetailsResponseDto.getNonMemberOrderCode().equals(orderCode)) {
            throw new InvalidOrderCodeException();
        }

        return orderDetailsResponseDto;
    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findNonMemberSubscriptionOrderDetails(
        Long orderNo, String orderCode) {

        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsListOfNonMember =
            orderRepository.findOrderSubscriptionDetailsOfNonMember(orderNo);

        if (orderSubscriptionDetailsListOfNonMember.isEmpty()
            || !orderSubscriptionDetailsListOfNonMember.get(0).getNonMemberOrderCode()
            .equals(orderCode)) {
            throw new InvalidOrderCodeException();
        }

        return orderSubscriptionDetailsListOfNonMember;
    }
}
