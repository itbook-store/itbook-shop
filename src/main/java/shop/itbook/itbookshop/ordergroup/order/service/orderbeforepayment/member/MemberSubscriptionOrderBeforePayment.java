package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.request.ProductDetailsDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class MemberSubscriptionOrderBeforePayment
    extends AbstractMemberOrderBeforePayment {

    private final OrderRepository orderRepository;
    private final OrderSubscriptionRepository orderSubscriptionRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderProductService orderProductService;
    private final ProductService productService;

    public MemberSubscriptionOrderBeforePayment(
        MemberService memberService,
        ProductService productService,
        OrderProductService orderProductService,
        OrderStatusHistoryService orderStatusHistoryService,
        OrderMemberRepository orderMemberRepository,
        OrderRepository orderRepository,
        CategoryCouponRepository categoryCouponRepository,
        ProductCategoryRepository productCategoryRepository,
        ProductCouponRepository productCouponRepository,
        CouponIssueRepository couponIssueRepository,
        OrderTotalCouponRepository orderTotalCouponRepository,
        RedisTemplate redisTemplate,
        ObjectMapper objectMapper, OrderRepository orderRepository1,
        OrderSubscriptionRepository orderSubscriptionRepository,
        OrderStatusHistoryService orderStatusHistoryService1,
        OrderProductService orderProductService1, ProductService productService1) {
        super(memberService, productService, orderProductService, orderStatusHistoryService,
            orderMemberRepository, orderRepository, categoryCouponRepository,
            productCategoryRepository,
            productCouponRepository, couponIssueRepository, orderTotalCouponRepository,
            redisTemplate,
            objectMapper);
        this.orderRepository = orderRepository1;
        this.orderSubscriptionRepository = orderSubscriptionRepository;
        this.orderStatusHistoryService = orderStatusHistoryService1;
        this.orderProductService = orderProductService1;
        this.productService = productService1;
    }

    protected void saveOrder(InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        OrderAddRequestDto orderAddRequestDto =
            infoForProcessOrderBeforePayment.getOrderAddRequestDto();

        Order order = OrderTransfer.addDtoToEntity(orderAddRequestDto);
        order.setSelectedDeliveryDate(LocalDate.now().plusMonths(1).withDayOfMonth(1));
        orderRepository.save(order);

        infoForProcessOrderBeforePayment.setOrder(order);

        OrderSubscription orderSubscription = OrderSubscription.builder().order(order)
            .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
            .sequence(1).subscriptionPeriod(orderAddRequestDto.getSubscriptionPeriod())
            .build();

        orderSubscriptionRepository.save(orderSubscription);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order,
            OrderStatusEnum.WAITING_FOR_PAYMENT);

        this.saveOrderSubscription(infoForProcessOrderBeforePayment);
    }

    private void saveOrderSubscription(
        InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {

        OrderAddRequestDto orderAddRequestDto =
            infoForProcessOrderBeforePayment.getOrderAddRequestDto();
        Integer subscriptionPeriod =
            infoForProcessOrderBeforePayment.getOrderAddRequestDto().getSubscriptionPeriod();

        ProductDetailsDto productDetailsDto =
            orderAddRequestDto.getProductDetailsDtoList().get(0);
        Product product = productService.findProductEntity(
            productDetailsDto.getProductNo());

        for (int i = 2; i <= subscriptionPeriod; i++) {

            Order orderChild = OrderTransfer.addDtoToEntity(orderAddRequestDto);
            orderChild.setSelectedDeliveryDate(LocalDate.now().plusMonths(i).withDayOfMonth(1));
            orderRepository.save(orderChild);

            OrderSubscription orderSubscriptionChild =
                OrderSubscription
                    .builder()
                    .order(orderChild)
                    .subscriptionStartDate(LocalDate.now().plusMonths(1).withDayOfMonth(1))
                    .sequence(i)
                    .subscriptionPeriod(orderAddRequestDto.getSubscriptionPeriod())
                    .build();

            orderSubscriptionRepository.save(orderSubscriptionChild);
            orderStatusHistoryService.addOrderStatusHistory(orderChild,
                OrderStatusEnum.WAITING_FOR_PAYMENT);

            orderProductService.addOrderProduct(orderChild, product, 0,
                0L);
        }
    }
}
