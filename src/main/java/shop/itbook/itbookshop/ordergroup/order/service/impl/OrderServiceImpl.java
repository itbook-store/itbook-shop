package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderNotFoundException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.order.util.AmountCalculationBeforePaymentUtil;
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
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentException;
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
    private final CouponService couponService;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;


    /**
     * The Origin url.
     */
    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

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

    @Override
    public Order findOrderEntity(Long orderNo) {
        return orderRepository.findById(orderNo).orElseThrow(OrderNotFoundException::new);
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
    public OrderPaymentDto addOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                 Optional<Long> memberNo, HttpSession session) {


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
        return getOrderPaymentDtoForMakingPayment(orderAddRequestDto, order, session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderPaymentDto addOrderSubscriptionBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                             Optional<Long> memberNo,
                                                             HttpSession session) {

        if (Objects.isNull(orderAddRequestDto.getIsSubscription())) {
            throw new InvalidOrderException();
        }

        int sequence = 1;
        long orderNo = 0;

        while (sequence <= orderAddRequestDto.getSubscriptionPeriod()) {

            Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
            order.setSelectedDeliveryDate(LocalDate.now().plusMonths(sequence).withDayOfMonth(1));
            order = orderRepository.save(order);
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

            sequence++;
        }

        orderNo = orderNo - sequence + 2;

        Order orderEntity = findOrderEntity(orderNo);

        OrderPaymentDto orderPaymentDtoForMakingPayment =
            getOrderPaymentDtoForMakingPayment(orderAddRequestDto, orderEntity,
                session);

        return orderPaymentDtoForMakingPayment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addOrderSubscriptionAfterPayment(Long orderNo) {

        OrderSubscription orderSubscription =
            orderSubscriptionRepository.findByOrder_OrderNo(orderNo)
                .orElseThrow(InvalidPaymentException::new);

        Integer subscriptionPeriod = orderSubscription.getSubscriptionPeriod();

        Payment payment = paymentRepository.findPaymentByOrder_OrderNo(orderNo)
            .orElseThrow(InvalidPaymentException::new);

        while (subscriptionPeriod > 1) {
            subscriptionPeriod--;
            orderNo++;

            Order order = findOrderEntity(orderNo);

            Payment tempPayment = Payment.builder()
                .paymentStatus(payment.getPaymentStatus())
                .order(order)
                .card(payment.getCard())
                .totalAmount(payment.getTotalAmount())
                .paymentKey(payment.getPaymentKey())
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .receiptUrl(payment.getReceiptUrl())
                .requestedAt(payment.getRequestedAt())
                .approvedAt(payment.getApprovedAt())
                .country(payment.getCountry())
                .checkoutUrl(payment.getCheckoutUrl())
                .vat(payment.getVat())
                .build();
            paymentRepository.save(tempPayment);
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderPaymentDto reOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                Long orderNo, HttpSession session) {

        Order order = findOrderEntity(orderNo);
        return getOrderPaymentDtoForMakingPayment(orderAddRequestDto, order, session);
    }

    private OrderPaymentDto getOrderPaymentDtoForMakingPayment(
        OrderAddRequestDto orderAddRequestDto,
        Order order, HttpSession session) {

        StringBuilder stringBuilder = new StringBuilder();

        // TODO jun : 카테고리쿠폰의 상품번호와 실제 상품번호가 맞는지 확인
        // TODO jun : 상품쿠폰의 상품번호와 실제 상품번호가 맞는지 확인
        // TODO jun : 주문총액쿠폰이랍시고 넘어온놈이 진짜 주문총액쿠폰종류인지 확인
        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            orderAddRequestDto.getProductDetailsDtoList();

        amount = this.calculateAmountAboutOrderProductCoupon(order, stringBuilder, amount,
            productDetailsDtoList, session);
        if (Objects.nonNull(orderAddRequestDto.getOrderTotalCouponNo())) {
            amount = this.calculateAmountAboutOrderTotalAmountCoupon(orderAddRequestDto, amount);
        }
        Long decreasePoint = orderAddRequestDto.getDecreasePoint();
        amount = this.calculateAmountAboutPoint(amount, decreasePoint);
        order.setDecreasePoint(decreasePoint);

        return OrderPaymentDto.builder()
            .orderNo(order.getOrderNo())
            .orderId(this.createOrderUUID(order))
            .orderName(stringBuilder.toString())
            .amount(amount)
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo())
            .build();
    }

    private long calculateAmountAboutOrderProductCoupon(Order order, StringBuilder stringBuilder,
                                                        long amount,
                                                        List<ProductDetailsDto> productDetailsDtoList,
                                                        HttpSession session) {

        List<Long> couponIssueNoList = new ArrayList<>();

        for (ProductDetailsDto productDetailsDto : productDetailsDtoList) {

            Product product = productService.findProductEntity(productDetailsDto.getProductNo());
            Integer productCnt = productDetailsDto.getProductCnt();
            long productPrice =
                (long) (product.getFixedPrice() * (1 - product.getDiscountPercent() * 0.01));

            long totalPriceOfSameProducts = productPrice * productCnt;
            amount += totalPriceOfSameProducts;

            if (stringBuilder.length() == 0) {
                stringBuilder.append(product.getName());
            }

            Coupon coupon =
                this.getAvailableCoupon(productDetailsDto.getCouponIssueNo(), productPrice);
            if (AmountCalculationBeforePaymentUtil.isUnavailableCoupon(coupon)) {
                orderProductService.addOrderProduct(order, product, productCnt,
                    totalPriceOfSameProducts);
                continue;
            }

            couponIssueNoList.add(productDetailsDto.getCouponIssueNo());
            Long totalPriceOfSameProductsWithCouponApplied =
                AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
                    totalPriceOfSameProducts, productPrice);
            long discountedPrice =
                totalPriceOfSameProducts - totalPriceOfSameProductsWithCouponApplied;
            amount = AmountCalculationBeforePaymentUtil.subAmountToDiscountedPriceAndNegativeCheck(
                amount, discountedPrice);

            orderProductService.addOrderProduct(order, product, productCnt,
                totalPriceOfSameProductsWithCouponApplied);

            this.increasePointPerOrderProduct(order, product, productPrice);
        }

        session.setAttribute("couponIssueNoList_" + order.getOrderNo(), couponIssueNoList);

        if (productDetailsDtoList.size() > 1) {
            stringBuilder.append(" 외 ")
                .append(productDetailsDtoList.size() - 1)
                .append("건");
        }

        return amount;
    }

    private void increasePointPerOrderProduct(Order order, Product product, long productPrice) {

        if (product.getIsPointApplying()) {
            int increasePointPercent = product.getIncreasePointPercent() / 100;
            Long increasePoint = 0L;
            if (Objects.nonNull(increasePoint)) {
                increasePoint = order.getIncreasePoint();
            }

            if (product.getIsPointApplyingBasedSellingPrice()) {
                order.setIncreasePoint(increasePoint + (productPrice * increasePointPercent));
            } else {
                order.setIncreasePoint(
                    increasePoint + (product.getFixedPrice() * increasePointPercent));
            }
        }
    }

    private Coupon getAvailableCoupon(Long couponIssueNo,
                                      Long basePriceToCompareAboutStandardAmount) {

        CouponIssue couponIssue =
            couponIssueService.findCouponIssueByCouponIssueNo(
                couponIssueNo);

        return AmountCalculationBeforePaymentUtil.getAvailableCoupon(couponIssue,
            basePriceToCompareAboutStandardAmount);
    }

    private long calculateAmountAboutOrderTotalAmountCoupon(OrderAddRequestDto orderAddRequestDto,
                                                            long amount) {
        Coupon coupon =
            this.getAvailableCoupon(orderAddRequestDto.getOrderTotalCouponNo(), amount);
        if (!AmountCalculationBeforePaymentUtil.isUnavailableCoupon(coupon)) {
            amount = AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
                amount, amount);
        }
        return amount;
    }

    private long calculateAmountAboutPoint(long amount, long pointToBeDiscounted) {
        return AmountCalculationBeforePaymentUtil.subAmountToDiscountedPriceAndNegativeCheck(amount,
            pointToBeDiscounted);
    }

    private String createOrderUUID(Order order) {
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
    public Order processAfterOrderPaymentSuccess(Long orderNo, HttpSession session) {

        Order order = findOrderEntity(orderNo);
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);

        List<Long> couponIssueNoList =
            (List<Long>) session.getAttribute("couponIssueNoList_" + orderNo);

        usingCouponIssue(couponIssueNoList);
        savePointHistoryAboutMember(order);
        return order;
    }

    private void usingCouponIssue(List<Long> couponIssueNoList) {

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


        Long increasePoint = order.getIncreasePoint();
        Long decreasePoint = order.getDecreasePoint();

        if (!Objects.equals(decreasePoint, 0L)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderDecrease(member,
                order, decreasePoint);
        }

        if (!Objects.equals(increasePoint, 0L)) {
            orderIncreaseDecreasePointHistoryService.savePointHistoryAboutOrderIncrease(member,
                order,
                increasePoint);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void processAfterOrderCancelPaymentSuccess(Long orderNo) {
        // TODO jun : 주문취소시에 쿠폰들 다시 넣어주고 포인트 되돌리는거 추가
        Order order = findOrderEntity(orderNo);
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.CANCELED);
    }

    @Override
    public Page<OrderListAdminViewResponseDto> findOrderListAdmin() {

        // todo won : 레포지토리 로직 추가.

        return null;
    }
}