package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.nonmember;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.member.AbstractMemberOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.transfer.OrderTransfer;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
public class NonMemberGeneralOrderBeforePayment extends AbstractMemberOrderBeforePayment {


    private final OrderRepository orderRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    public NonMemberGeneralOrderBeforePayment(
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
        OrderStatusHistoryService orderStatusHistoryService1) {
        super(memberService, productService, orderProductService, orderStatusHistoryService,
            orderMemberRepository, orderRepository, categoryCouponRepository,
            productCategoryRepository,
            productCouponRepository, couponIssueRepository, orderTotalCouponRepository,
            redisTemplate,
            objectMapper);
        this.orderRepository = orderRepository1;
        this.orderStatusHistoryService = orderStatusHistoryService1;
    }

    @Override
    protected void saveOrder(InfoForProcessOrderBeforePayment infoForProcessOrderBeforePayment) {
        // 주문 엔티티 인스턴스 생성 후 저장
        Order order =
            OrderTransfer.addDtoToEntity(infoForProcessOrderBeforePayment.getOrderAddRequestDto());
        orderRepository.save(order);

        infoForProcessOrderBeforePayment.setOrder(order);

        // 주문_상태_이력 테이블 저장
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAITING_FOR_PAYMENT);
    }
}
