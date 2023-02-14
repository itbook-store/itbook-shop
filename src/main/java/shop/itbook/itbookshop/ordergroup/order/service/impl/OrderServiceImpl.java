package shop.itbook.itbookshop.ordergroup.order.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.CouponApplyDto;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForCouponIssueApply;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchCategoryNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchProductNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.NotOrderTotalCouponException;
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
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
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
import shop.itbook.itbookshop.pointgroup.pointhistory.service.impl.PointHistoryServiceImpl;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.service.OrderIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

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
    private final MemberService memberService;
    private final ProductService productService;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final CouponIssueService couponIssueService;
    private final OrderIncreaseDecreasePointHistoryService orderIncreaseDecreasePointHistoryService;
    private final CategoryCouponRepository categoryCouponRepository;
    private final ProductCouponRepository productCouponRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final OrderTotalCouponRepository orderTotalCouponRepository;
    private final CategoryCouponApplyRepository categoryCouponApplyRepository;
    private final ProductCouponApplyRepository productCouponApplyRepository;
    private final OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;
    private final OrderCancelIncreasePointHistoryService orderCancelIncreasePointHistoryService;
    private final CouponIssueRepository couponIssueRepository;
    private final OrderTotalCouponApplyService orderTotalCouponApplyService;


    /**
     * The Origin url.
     */
    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    private final RedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSubscription(Long orderNo) {

        return orderSubscriptionRepository.findByOrder_OrderNo(orderNo).isPresent();
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
                                                 Optional<Long> memberNo) {


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
        return getOrderPaymentDtoForMakingPayment(orderAddRequestDto, order, memberNo);
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
    public OrderPaymentDto addOrderSubscriptionBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                             Optional<Long> memberNo) {

        if (Objects.isNull(orderAddRequestDto.getIsSubscription())) {
            throw new InvalidOrderException();
        }

        int sequence = 1;

        OrderPaymentDto orderPaymentDto = null;

        while (sequence <= orderAddRequestDto.getSubscriptionPeriod()) {

            Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
            order.setSelectedDeliveryDate(LocalDate.now().plusMonths(sequence).withDayOfMonth(1));
            orderRepository.save(order);

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

            OrderPaymentDto temp =
                getOrderPaymentDtoForMakingPayment(orderAddRequestDto, order,
                    memberNo);

            if (Objects.isNull(orderPaymentDto)) {
                orderPaymentDto = temp;
            }
        }

        if (Objects.isNull(orderPaymentDto)) {
            throw new InvalidOrderException();
        }

        return orderPaymentDto;
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

            // 완료 처리.
            // 주문 상품번호리스트가져와서 각각 상품쿠폰, 카테고리 쿠폰 있는지 확인 후 있으면 사용전상태로 변경
            processAfterOrderPaymentSuccess(orderNo);
        } //end while
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public OrderPaymentDto reOrderBeforePayment(OrderAddRequestDto orderAddRequestDto,
                                                Long orderNo) {

        Order order = findOrderEntity(orderNo);
        Optional<OrderMember> optionalOrderMember = orderMemberRepository.findById(orderNo);

        Optional<Long> optionalMemberNo = Optional.empty();
        if (optionalOrderMember.isPresent()) {
            optionalMemberNo =
                Optional.ofNullable(optionalOrderMember.get().getMember().getMemberNo());
        }

        return getOrderPaymentDtoForMakingPayment(orderAddRequestDto, order,
            optionalMemberNo);
    }

    private OrderPaymentDto getOrderPaymentDtoForMakingPayment(
        OrderAddRequestDto orderAddRequestDto,
        Order order, Optional<Long> optionalMemberNo) {

        StringBuilder stringBuilder = new StringBuilder();

        long amount = 0L;
        List<ProductDetailsDto> productDetailsDtoList =
            orderAddRequestDto.getProductDetailsDtoList();

        amount = this.calculateAmountAboutOrderProductCoupon(order, stringBuilder, amount,
            productDetailsDtoList);
        amount = this.calculateAmountAboutOrderTotalAmountCoupon(orderAddRequestDto, amount,
            order.getOrderNo());

        if (optionalMemberNo.isPresent()) {
            amount = doProcessPointDecreaseAndGetAmount(orderAddRequestDto, order, amount);
        }

        return OrderPaymentDto.builder()
            .orderNo(order.getOrderNo())
            .orderId(this.createOrderUUID(order))
            .orderName(stringBuilder.toString())
            .amount(amount)
            .successUrl(ORIGIN_URL + "orders/success/" + order.getOrderNo())
            .failUrl(ORIGIN_URL + "orders/fail" + order.getOrderNo())
            .build();
    }

    private long doProcessPointDecreaseAndGetAmount(OrderAddRequestDto orderAddRequestDto,
                                                    Order order, long amount) {

        Long decreasePoint = orderAddRequestDto.getDecreasePoint();
        if (Objects.equals(decreasePoint, 0L)) {
            return amount;
        }
        if (amount < decreasePoint) {
            decreasePoint = amount;
        }

        amount = this.calculateAmountAboutPoint(amount, decreasePoint);
        order.setDecreasePoint(decreasePoint);
        return amount;
    }

    private long calculateAmountAboutOrderProductCoupon(Order order, StringBuilder stringBuilder,
                                                        long amount,
                                                        List<ProductDetailsDto> productDetailsDtoList) {

        List<InfoForCouponIssueApply> infoForCouponIssueApplyList = new ArrayList<>();

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
                this.increasePointPerOrderProduct(order, product, productPrice, productCnt);
                continue;
            }

            checkMismatchAboutRequestedProductAndCoupon(product, coupon);

            Long totalPriceOfSameProductsWithCouponApplied =
                AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
                    totalPriceOfSameProducts, productPrice);
            long discountedPrice =
                totalPriceOfSameProducts - totalPriceOfSameProductsWithCouponApplied;

            amount = AmountCalculationBeforePaymentUtil.subAmountToDiscountedPriceAndNegativeCheck(
                amount, discountedPrice);

            OrderProduct orderProduct =
                orderProductService.addOrderProduct(order, product, productCnt,
                    totalPriceOfSameProductsWithCouponApplied);

            InfoForCouponIssueApply infoCouponIssueApply =
                new InfoForCouponIssueApply(productDetailsDto.getCouponIssueNo(),
                    orderProduct.getOrderProductNo());
            infoForCouponIssueApplyList.add(infoCouponIssueApply);

            this.increasePointPerOrderProduct(order, product, productPrice, productCnt);
        }


        if (!infoForCouponIssueApplyList.isEmpty()) {
            try {
                redisTemplate.opsForHash()
                    .put("productAndCategoryCouponApplyDto", String.valueOf(order.getOrderNo()),
                        objectMapper.writeValueAsString(
                            new CouponApplyDto(infoForCouponIssueApplyList)));

            } catch (JsonProcessingException e) {
                // TODO jun : 클래스 예외처리 커스톰
                throw new RuntimeException();
            }
        }

        if (productDetailsDtoList.size() > 1) {
            stringBuilder.append(" 외 ")
                .append(productDetailsDtoList.size() - 1)
                .append("건");
        }

        return amount;
    }

    private void checkMismatchAboutRequestedProductAndCoupon(Product product, Coupon coupon) {

        Optional<CategoryCoupon> optionalCategoryCoupon =
            categoryCouponRepository.findById(coupon.getCouponNo());

        if (optionalCategoryCoupon.isPresent()) {
            Integer categoryNoAboutCoupon =
                optionalCategoryCoupon.get().getCategory().getCategoryNo();
            Optional<ProductCategory> optionalProductCategory =
                productCategoryRepository.findById(
                    new ProductCategory.Pk(product.getProductNo(), categoryNoAboutCoupon));
            if (optionalProductCategory.isEmpty()) {
                throw new MismatchCategoryNoWhenCouponApplyException();
            }
        } else {
            Optional<ProductCoupon> optionalProductCoupon =
                productCouponRepository.findById(coupon.getCouponNo());
            if (optionalProductCoupon.isPresent()) {
                Long productNoAboutCoupon =
                    optionalProductCoupon.get().getProduct().getProductNo();
                if (!Objects.equals(product.getProductNo(), productNoAboutCoupon)) {
                    throw new MismatchProductNoWhenCouponApplyException();
                }
            }
        }
    }

    private void increasePointPerOrderProduct(Order order, Product product, long productPrice,
                                              Integer productCnt) {

        if (product.getIsPointApplying()) {
            int increasePointPercent = product.getIncreasePointPercent() / 100;
            Long increasePoint = 0L;
            if (Objects.nonNull(order.getIncreasePoint())) {
                increasePoint = order.getIncreasePoint();
            }

            if (product.getIsPointApplyingBasedSellingPrice()) {
                order.setIncreasePoint(
                    increasePoint + ((productPrice * increasePointPercent)) * productCnt);
            } else {
                order.setIncreasePoint(
                    increasePoint + (product.getFixedPrice() * increasePointPercent));
            }
        }
    }

    private Coupon getAvailableCoupon(Long couponIssueNo,
                                      Long basePriceToCompareAboutStandardAmount) {


        if (Objects.isNull(couponIssueNo)) {
            return null;
        }

        Optional<CouponIssue> optionalCouponIssue = couponIssueRepository.findById(couponIssueNo);

        if (optionalCouponIssue.isEmpty()) {
            return null;
        }

        CouponIssue couponIssue = optionalCouponIssue.get();
        return AmountCalculationBeforePaymentUtil.getAvailableCoupon(couponIssue,
            basePriceToCompareAboutStandardAmount);
    }

    private long calculateAmountAboutOrderTotalAmountCoupon(OrderAddRequestDto orderAddRequestDto,
                                                            long amount, Long orderNo) {
        Coupon coupon =
            this.getAvailableCoupon(orderAddRequestDto.getOrderTotalCouponNo(), amount);
        if (AmountCalculationBeforePaymentUtil.isUnavailableCoupon(coupon)) {
            return amount;
        }

        checkMismatchAboutTypeOfOrderTotalCoupon(coupon);

        try {
            redisTemplate.opsForHash()
                .put("orderTotalCouponApplyDto", String.valueOf(orderNo),
                    objectMapper.writeValueAsString(new CouponApplyDto(List.of(
                        new InfoForCouponIssueApply(orderAddRequestDto.getOrderTotalCouponNo(),
                            null)))));

        } catch (JsonProcessingException e) {
            // TODO jun : 클래스 예외처리 커스톰
            throw new RuntimeException();
        }

        return AmountCalculationBeforePaymentUtil.getTotalPriceWithCouponApplied(coupon,
            amount, amount);
    }

    private void checkMismatchAboutTypeOfOrderTotalCoupon(Coupon coupon) {
        Optional<OrderTotalCoupon> optionalOrderTotalCoupon = orderTotalCouponRepository.findById(
            coupon.getCouponNo());
        if (optionalOrderTotalCoupon.isEmpty()) {
            throw new NotOrderTotalCouponException();
        }
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
    public Order processAfterOrderPaymentSuccess(Long orderNo) {

        Order order = findOrderEntity(orderNo);
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PAYMENT_COMPLETE);

        String productAndCategoryCouponApplyDtoJson =
            (String) redisTemplate.opsForHash()
                .get("productAndCategoryCouponApplyDto", String.valueOf(order.getOrderNo()));

        String orderTotalCouponApplyDtoJson =
            (String) redisTemplate.opsForHash()
                .get("orderTotalCouponApplyDto", String.valueOf(order.getOrderNo()));

        CouponApplyDto productAndCategoryCouponApplyDto = null;
        CouponApplyDto orderTotalCouponApplyDto = null;

        try {
            if (Objects.nonNull(productAndCategoryCouponApplyDtoJson)) {

                productAndCategoryCouponApplyDto =
                    objectMapper.readValue(productAndCategoryCouponApplyDtoJson,
                        CouponApplyDto.class);
            }

            if (Objects.nonNull(orderTotalCouponApplyDtoJson)) {

                orderTotalCouponApplyDto =
                    objectMapper.readValue(orderTotalCouponApplyDtoJson, CouponApplyDto.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        usingCouponIssue(productAndCategoryCouponApplyDto, orderTotalCouponApplyDto, order);
        savePointHistoryAboutMember(order);
        return order;
    }

    private void usingCouponIssue(CouponApplyDto productAndCategoryCouponApplyDto,
                                  CouponApplyDto orderTotalCouponApplyDto, Order order) {

        if (Objects.nonNull(productAndCategoryCouponApplyDto)) {
            for (InfoForCouponIssueApply info : productAndCategoryCouponApplyDto.getInfoForCouponIssueApplyList()) {
                couponIssueService.saveCouponApplyAboutCategoryAndProduct(info.getCouponIssueNo(),
                    info.getOrderProductNo());
            }
        }

        if (Objects.nonNull(orderTotalCouponApplyDto)) {
            orderTotalCouponApplyService.saveOrderTotalCouponApplyAndChangeCouponIssue(
                orderTotalCouponApplyDto.getInfoForCouponIssueApplyList().get(0).getCouponIssueNo(),
                order);
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

        Order order = findOrderEntity(orderNo);
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.CANCELED);

        // 주문 상품번호리스트가져와서 각각 상품쿠폰, 카테고리 쿠폰 있는지 확인 후 있으면 사용전상태로 변경
        this.changeCategoryAndProductCouponStatusByCancel(orderNo);

        // 주문번호로 주문총액상품쿠폰 있는지 확인하고 있으면 사용전상태로 변경
        this.changeOrderTotalAmountCouponStatusByCancel(orderNo);

        // 포인트 차감금액 있는지 확인해서 금액만큼 주문취소적립
        this.addOrderCancelIncreaseDecreasePointHistory(order,
            PointHistoryServiceImpl.INCREASE_POINT_HISTORY);

        // 먼저 주문취소차감 api 만들기 -> 포인트 적립금액 있는지 확인해서 주문취소차감
        this.addOrderCancelIncreaseDecreasePointHistory(order,
            PointHistoryServiceImpl.DECREASE_POINT_HISTORY);
    }

    private void changeCategoryAndProductCouponStatusByCancel(Long orderNo) {
        List<OrderProductDetailResponseDto> OrderProductDetailResponseDtoList =
            orderProductService.findOrderProductsByOrderNo(orderNo);

        for (OrderProductDetailResponseDto orderProductDetailResponseDto : OrderProductDetailResponseDtoList) {

            Optional<CategoryCouponApply> optionalCategoryCouponApply =
                categoryCouponApplyRepository.findById(
                    orderProductDetailResponseDto.getOrderProductNo());

            if (optionalCategoryCouponApply.isPresent()) {
                CategoryCouponApply categoryCouponApply = optionalCategoryCouponApply.get();
                couponIssueService.cancelCouponIssue(
                    categoryCouponApply.getCouponIssue().getCouponIssueNo());
            } else {
                Optional<ProductCouponApply> optionalProductCouponApply =
                    productCouponApplyRepository.findById(
                        orderProductDetailResponseDto.getOrderProductNo());

                if (optionalProductCouponApply.isPresent()) {
                    ProductCouponApply productCouponApply = optionalProductCouponApply.get();
                    couponIssueService.cancelCouponIssue(productCouponApply.getCouponIssueNo());
                }
            }
        }
    }

    private void changeOrderTotalAmountCouponStatusByCancel(Long orderNo) {

        Optional<OrderTotalCouponApply> optionalOrderTotalCouponApply =
            orderTotalCouponApplyRepositoy.findById(
                orderNo);

        if (optionalOrderTotalCouponApply.isEmpty()) {
            return;
        }

        OrderTotalCouponApply orderTotalCouponApply = optionalOrderTotalCouponApply.get();
        couponIssueService.cancelCouponIssue(orderTotalCouponApply.getCouponIssueNo());
    }

    private void addOrderCancelIncreaseDecreasePointHistory(Order order, boolean isDecrease) {
        if (Objects.equals(order.getIncreasePoint(), 0L)) {
            return;
        }

        Optional<OrderMember> optionalOrderMember =
            orderMemberRepository.findById(order.getOrderNo());

        if (optionalOrderMember.isEmpty()) {
            return;
        }

        OrderMember orderMember = optionalOrderMember.get();
        if (isDecrease) {
            orderCancelIncreasePointHistoryService.savePointHistoryAboutOrderCancelDecrease(
                orderMember.getMember(),
                order,
                order.getDecreasePoint());
        } else {
            orderCancelIncreasePointHistoryService.savePointHistoryAboutOrderCancelIncrease(
                orderMember.getMember(),
                order,
                order.getDecreasePoint());
        }
    }

    @Override
    public Page<OrderListAdminViewResponseDto> findOrderListAdmin() {

        // todo won : 레포지토리 로직 추가.

        return null;
    }
}