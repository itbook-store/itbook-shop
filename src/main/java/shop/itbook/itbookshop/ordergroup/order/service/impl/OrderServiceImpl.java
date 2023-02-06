package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.util.LinkedList;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.repository.OrderProductHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * OrderAdminService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductHistoryRepository orderProductHistoryRepository;
    private final OrderMemberRepository orderMemberRepository;

    private final MemberService memberService;
    private final OrderStatusService orderStatusService;
    private final ProductService productService;
    
    public void addOrder() {
        // TODO: 2023/02/04 쿠폰 이력 추가
        // TODO: 2023/02/04 포인트 이력 추가

        // TODO: 2023/02/04 주문 상태 변경
    }

    @Override
    @Transactional
    public void addOrderOfMember(OrderAddRequestDto orderAddRequestDto, Long memberNo) {

        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        orderRepository.save(order);

        Member member = memberService.findMemberByMemberNo(memberNo);
        OrderMember orderMember = new OrderMember(order, member);
        orderMemberRepository.save(orderMember);

        Queue<Integer> productCntQueue = new LinkedList<>(orderAddRequestDto.getProductCountList());

        orderAddRequestDto.getProductNoList().stream().map(
                productNo -> OrderProduct.builder()
                    .order(order)
                    .product(productService.findProductEntity(productNo))
                    .count(productCntQueue.poll())
                    .isHidden(false)
                    .build())
            .map(orderproduct -> {
                orderProductRepository.save(orderproduct);
                return new OrderProductHistory(orderproduct,
                    orderStatusService.findByOrderStatusEnum(
                        OrderStatusEnum.WAITING_FOR_PAYMENT));
            }).forEach(orderProductHistoryRepository::save);

    }

    @Override
    public Page<OrderListMemberViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                               Long memberNo) {
        return orderRepository.getOrderListOfMemberWithStatus(pageable, memberNo);
    }
}
