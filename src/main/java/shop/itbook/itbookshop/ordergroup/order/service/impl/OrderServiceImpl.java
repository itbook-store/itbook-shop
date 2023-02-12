package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.http.HttpSession;
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
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;
import shop.itbook.itbookshop.ordergroup.ordernonmember.repository.OrderNonMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.service.OrderStatusService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
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
    private final OrderProductRepository orderProductRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderMemberRepository orderMemberRepository;
    private final OrderNonMemberRepository orderNonMemberRepository;
    private final PaymentRepository paymentRepository;

    private final DeliveryService deliveryService;
    private final MemberService memberService;
    private final OrderStatusService orderStatusService;
    private final ProductService productService;

    private final CouponIssueService couponIssueService;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;


    @Value("${payment.origin.url}")
    public String ORIGIN_URL;


    @Override
    public Order findOrderEntity(Long orderNo) {
        return orderRepository.findById(orderNo).orElseThrow();
    }

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

        // 주문 엔티티 인스턴스 생성 후 저장
        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        orderRepository.save(order);

        // 주문_상태_이력 테이블 저장
        OrderStatus orderStatus =
            orderStatusService.findByOrderStatusEnum(OrderStatusEnum.WAITING_FOR_PAYMENT);
        OrderStatusHistory orderStatusHistory =
            new OrderStatusHistory(order, orderStatus, LocalDateTime.now());
        orderStatusHistoryRepository.save(orderStatusHistory);

//        // 배송 상태 생성 후 저장
//        deliveryService.registerDelivery(order);

        // 회원, 비회원 구분해서 저장
        checkMemberAndSaveOrder(order, memberNo);

        // 주문_상품 테이블 저장 및 가격 계산
        Queue<Integer> productCntQueue = new LinkedList<>(orderAddRequestDto.getProductCntList());

        StringBuilder stringBuilder = new StringBuilder();
        AtomicReference<Long> amount = new AtomicReference<>(0L);

        orderAddRequestDto.getProductNoList().stream().forEach(
            productNo -> {
                Product product = productService.findProductEntity(productNo);
                Integer productCnt = productCntQueue.poll();

                // TODO: 2023/02/11 쿠폰 적용 로직 추가.
                Long productPrice =
                    (long) (product.getFixedPrice() * (1 - product.getDiscountPercent() * 0.01));

                amount.set(amount.get() + productPrice * productCnt);

                if (stringBuilder.length() == 0) {
                    stringBuilder.append(product.getName());
                }

                // 주문_상품 테이블 저장
                OrderProduct orderProduct = OrderProduct.builder()
                    .order(order)
                    .product(product)
                    .count(productCnt)
                    .productPrice(productPrice)
                    .build();
                orderProductRepository.save(orderProduct);
            });

        // 결제를 위한 order ID 생성
        String orderNoString = String.valueOf(order.getOrderNo());
        String randomUuidString = UUID.randomUUID().toString();
        randomUuidString = orderNoString + randomUuidString.substring(orderNoString.length());
        String orderId = UUID.fromString(randomUuidString).toString();

        if (orderAddRequestDto.getProductNoList().size() > 1) {
            stringBuilder.append(" 외 ").append(orderAddRequestDto.getProductNoList().size() - 1)
                .append("건");
        }

        return OrderPaymentDto.builder()
            .orderNo(order.getOrderNo())
            .orderId(orderId)
            .orderName(stringBuilder.toString())
            .amount(amount.get())
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo())
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
    public Order completeOrderPay(Long orderNo, HttpSession session) {

        Order order = orderRepository.findById(orderNo).orElseThrow();
        OrderStatus orderStatus = orderStatusService.findByOrderStatusEnum(
            OrderStatusEnum.PAYMENT_COMPLETE);

        OrderStatusHistory orderStatusHistory =
            new OrderStatusHistory(order, orderStatus, LocalDateTime.now());
        orderStatusHistoryRepository.save(orderStatusHistory);

        usingCouponIssue(orderNo, session);
        savePointHistoryAboutMember(orderNo, session, order);
        return order;
    }

    private void usingCouponIssue(Long orderNo, HttpSession session) {
        List<Long> couponIssueNoListWhenOrderPayCompletion =
            (List<Long>) session.getAttribute("couponIssueNoListWhenOrderPayCompletion_" + orderNo);

        for (Long couponIssueNo : couponIssueNoListWhenOrderPayCompletion) {
            couponIssueService.usingCouponIssue(couponIssueNo);
        }
    }

    private void savePointHistoryAboutMember(Long orderNo, HttpSession session, Order order) {
        Optional<OrderMember> optionalOrderMember = orderMemberRepository.findById(orderNo);
        if (!optionalOrderMember.isPresent()) {
            return;
        }

        OrderMember orderMember = optionalOrderMember.get();
        Member member = orderMember.getMember();

        // db 반정규화
        Long increasePoint =
            (Long) session.getAttribute("increasePointToUseWhenOrderPayCompletion_" + orderNo);
        Long decreasePoint =
            (Long) session.getAttribute("decreasePointToUseWhenOrderPayCompletion_" + orderNo);

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

    @Override
    public OrderDetailsResponseDto findOrderDetails(Long orderNo) {

        List<OrderProductDetailResponseDto> orderProductDetailResponseDtoList =
            orderProductRepository.findOrderProductsByOrderNo(orderNo);
        List<OrderDestinationDto> orderDestinationList =
            orderRepository.findOrderDestinationsByOrderNo(orderNo);
        PaymentCardResponseDto paymentCardResponseDto =
            paymentRepository.findPaymentCardByOrderNo(orderNo);
        String orderStatus = orderRepository.findOrderStatusByOrderNo(orderNo);

        return new OrderDetailsResponseDto(orderProductDetailResponseDtoList, orderDestinationList,
            paymentCardResponseDto, orderStatus);
    }
}