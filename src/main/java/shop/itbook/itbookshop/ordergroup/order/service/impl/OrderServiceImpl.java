package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderNotFoundException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;
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
    private final OrderMemberRepository orderMemberRepository;
    private final OrderNonMemberRepository orderNonMemberRepository;
    private final PaymentRepository paymentRepository;

    private final OrderProductService orderProductService;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final DeliveryService deliveryService;
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderSubscriptionRepository orderSubscriptionRepository;

    private final CouponIssueService couponIssueService;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;


    /**
     * The Origin url.
     */
    @Value("${payment.origin.url}")
    public String ORIGIN_URL;


    @Override
    public Order findOrderEntity(Long orderNo) {
        return orderRepository.findById(orderNo).orElseThrow(OrderNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderPaymentDto addOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                 Optional<Long> memberNo) {

        if (Objects.nonNull(orderAddRequestDto.getIsSubscription())) {
            // 구독 처리
            return orderSubscription(orderAddRequestDto, memberNo);
        }

        // 주문 엔티티 인스턴스 생성 후 저장
        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        orderRepository.save(order);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAITING_FOR_PAYMENT);

//        // 배송 상태 생성 후 저장
//        deliveryService.registerDelivery(order);

        // 회원, 비회원 구분해서 저장
        checkMemberAndSaveOrder(order, memberNo);

        // 주문_상품 테이블 저장 및 가격 계산
        return getOrderPaymentDtoForMakingPaymentForm(orderAddRequestDto, order);
    }

    private OrderPaymentDto orderSubscription(OrderAddRequestDto orderAddRequestDto,
                                              Optional<Long> memberNo) {

        int sequence = 1;
        long orderNo = 0;

        while (sequence <= orderAddRequestDto.getSubscriptionPeriod()) {

            Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
            order.setSelectedDeliveryDate(LocalDate.now().plusMonths(sequence).withDayOfMonth(1));
            orderRepository.save(order);
            orderNo = order.getOrderNo();

            OrderSubscription orderSubscription = OrderSubscription.builder()
                .order(order)
                .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
                .sequence(sequence)
                .subscriptionPeriod(orderAddRequestDto.getSubscriptionPeriod())
                .build();
            orderSubscriptionRepository.save(orderSubscription);

            // 주문_상태_이력 테이블 저장
            orderStatusHistoryService.addOrderStatusHistory(order,
                OrderStatusEnum.WAITING_FOR_PAYMENT);

            // 회원, 비회원 구분해서 저장
            checkMemberAndSaveOrder(order, memberNo);
        }

        orderNo = orderNo - sequence;

        return getOrderPaymentDtoForMakingPaymentForm(orderAddRequestDto, findOrderEntity(orderNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderPaymentDto reOrder(OrderAddRequestDto orderAddRequestDto,
                                   Long orderNo) {

        Order order = findOrderEntity(orderNo);
        return getOrderPaymentDtoForMakingPaymentForm(orderAddRequestDto, order);
    }

    private OrderPaymentDto getOrderPaymentDtoForMakingPaymentForm(
        OrderAddRequestDto orderAddRequestDto,
        Order order) {
//
//        Queue<Integer> productCntQueue = new LinkedList<>(orderAddRequestDto.get());
//
//        StringBuilder stringBuilder = new StringBuilder();
//        AtomicReference<Long> amount = new AtomicReference<>(0L);
//
//        orderAddRequestDto.getProductNoList().forEach(
//            productNo -> {
//                Product product = productService.findProductEntity(productNo);
//                Integer productCnt = productCntQueue.poll();
//
//                // TODO: 2023/02/11 쿠폰 가져와서 가격계산 로직 추가.
//                // TODO: 2023/02/11 포인트 가져와서 가격계산 로직 추가.
//                Long productPrice =
//                    (long) (product.getFixedPrice() * (1 - product.getDiscountPercent() * 0.01));
//
//                amount.set(amount.get() + productPrice * productCnt);
//
//                if (stringBuilder.length() == 0) {
//                    stringBuilder.append(product.getName());
//                }
//
//                // 첫 주문 등록이냐 재 주문이냐에 따라 다른 로직 수행
//                orderProductService.addOrderProduct(order, product, productCnt, productPrice);
//            });
//
//        if (orderAddRequestDto.getProductDetailsDtoList().size() > 1) {
//            stringBuilder.append(" 외 ")
//                .append(orderAddRequestDto.getProductDetailsDtoList().size() - 1)
//                .append("건");
//        }
//
//        // 결제를 위한 order ID 생성
        String orderId = createOrderUUID(order);

        return OrderPaymentDto.builder()
            .orderNo(order.getOrderNo())
            .orderId(orderId)
            .orderName("testOrderName")
            .amount(1000L)
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo())
            .build();
    }

    private static String createOrderUUID(Order order) {
        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        String orderId = UUID.fromString(randomUuidString).toString();
        return orderId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void cancelOrderBeforePayment(Long orderNo) {
        Order order = findOrderEntity(orderNo);

        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.CANCELED);
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Order completeOrderPay(Long orderNo, List<Long> couponIssueNoList) {

        Order order = findOrderEntity(orderNo);

        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);

        usingCouponIssue(orderNo, couponIssueNoList);
        savePointHistoryAboutMember(order);
        return order;
    }

    private void usingCouponIssue(Long orderNo, List<Long> couponIssueNoList) {
//        List<Long> couponIssueNoListWhenOrderPayCompletion =
//            (List<Long>) session.getAttribute("couponIssueNoListWhenOrderPayCompletion_" + orderNo);

        if (Objects.nonNull(couponIssueNoList)) {
            for (Long couponIssueNo : couponIssueNoList) {
                couponIssueService.usingCouponIssue(couponIssueNo);
            }
        }
    }

    private void savePointHistoryAboutMember(Order order) {
        Optional<OrderMember> optionalOrderMember =
            orderMemberRepository.findById(order.getOrderNo());
        if (!optionalOrderMember.isPresent()) {
            return;
        }

        OrderMember orderMember = optionalOrderMember.get();
        Member member = orderMember.getMember();

        // db 반정규화
//        Long increasePoint =
//            (Long) session.getAttribute("increasePointToUseWhenOrderPayCompletion_" + orderNo);
//        Long decreasePoint =
//            (Long) session.getAttribute("decreasePointToUseWhenOrderPayCompletion_" + orderNo);

        Long increasePoint = null;
        Long decreasePoint = null;

        if (!Objects.isNull(decreasePoint)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderDecrease(member,
                order, decreasePoint);
        }

        if (!Objects.isNull(increasePoint)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderIncrease(member,
                order,
                increasePoint);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDetailsResponseDto findOrderDetails(Long orderNo) {

        List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList =
            orderProductService.findOrderProductsByOrderNo(orderNo);
        List<OrderDestinationDto> orderDestinationList =
            orderRepository.findOrderDestinationsByOrderNo(orderNo);
        PaymentCardResponseDto paymentCardResponseDto =
            paymentRepository.findPaymentCardByOrderNo(orderNo);
        String orderStatus = orderRepository.findOrderStatusByOrderNo(orderNo);

        return new OrderDetailsResponseDto(orderProductDetailResponseDtoList, orderDestinationList,
            paymentCardResponseDto, orderStatus);
    }
}