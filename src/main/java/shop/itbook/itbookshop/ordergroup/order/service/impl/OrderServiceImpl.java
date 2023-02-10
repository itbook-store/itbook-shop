package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.repository.OrderProductHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
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
    private final OrderNonMemberRepository orderNonMemberRepository;

    private final DeliveryService deliveryService;
    private final MemberService memberService;
    private final OrderStatusService orderStatusService;
    private final ProductService productService;

    /**
     * Add order.
     */
    public void addOrder() {
        // TODO: 2023/02/04 쿠폰 이력 추가
        // TODO: 2023/02/04 포인트 이력 추가

        // TODO: 2023/02/04 주문 상태 변경
    }

    @Override
    @Transactional
    public OrderPaymentDto addOrder(OrderAddRequestDto orderAddRequestDto,
                                    Optional<Long> memberNo) {

        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        orderRepository.save(order);

        deliveryService.registerDelivery(order);

        checkMemberAndSaveOrder(order, memberNo);

        Queue<Integer> productCntQueue = new LinkedList<>(orderAddRequestDto.getProductCntList());

        StringBuilder stringBuilder = new StringBuilder();
        AtomicReference<Long> amount = new AtomicReference<>(0L);

        orderAddRequestDto.getProductNoList().stream().map(
                productNo -> {
                    Product product = productService.findProductEntity(productNo);
                    Integer productCnt = productCntQueue.poll();

                    amount.set((long) (amount.get() +
                        (product.getFixedPrice() * (1 - product.getDiscountPercent() * 0.01) *
                            productCnt)));

                    if (stringBuilder.length() == 0) {
                        stringBuilder.append(product.getName());
                    }

                    return OrderProduct.builder()
                        .order(order)
                        .product(product)
                        .count(productCnt)
                        .isHidden(false)
                        .build();
                })
            .map(orderproduct -> {
                orderProductRepository.save(orderproduct);
                return new OrderProductHistory(orderproduct,
                    orderStatusService.findByOrderStatusEnum(
                        OrderStatusEnum.WAITING_FOR_PAYMENT));
            }).forEach(orderProductHistoryRepository::save);

        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        String orderId = UUID.fromString(randomUuidString).toString();

        if (orderAddRequestDto.getProductNoList().size() > 1) {
            stringBuilder.append(" 외 ").append(orderAddRequestDto.getProductNoList().size() - 1)
                .append("건");
        }

        return OrderPaymentDto.builder()
            .orderId(orderId)
            .orderName(stringBuilder.toString())
            .amount(amount.get())
            .build();
    }

    private void checkMemberAndSaveOrder(Order order,
                                         Optional<Long> memberNo) {

        if (memberNo.isPresent()) {
            Member member = memberService.findMemberByMemberNo(memberNo.get());
            OrderMember orderMember = new OrderMember(order, member);
            orderMemberRepository.save(orderMember);

            return;
        }

        OrderNonMember orderNonMember =
            new OrderNonMember(order, 12345678L);
        orderNonMemberRepository.save(orderNonMember);

    }

    @Override
    public Page<OrderListMemberViewResponseDto> findOrderListOfMemberWithStatus(Pageable pageable,
                                                                                Long memberNo) {
        return orderRepository.getOrderListOfMemberWithStatus(pageable, memberNo);
    }

    @Override
    @Transactional
    public void completeOrderPay(Long orderNo) {

        Order order = orderRepository.findById(orderNo).orElseThrow();

        OrderProductHistory savedOrderProductHistory =
            orderProductHistoryRepository.findByOrderProduct_Order(order).orElseThrow();
        OrderStatus orderStatus = orderStatusService.findByOrderStatusEnum(
            OrderStatusEnum.PAYMENT_COMPLETE);

        OrderProductHistory orderProductHistory = new OrderProductHistory(
            savedOrderProductHistory.getOrderProduct(),
            orderStatus
        );

        orderProductHistoryRepository.save(orderProductHistory);
    }
}