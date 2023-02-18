package shop.itbook.itbookshop.ordergroup.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dummy.OrderTotalCouponDummy;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.dummy.OrderTotalCouponApplyDummy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import shop.itbook.itbookshop.deliverygroup.delivery.dummy.DeliveryDummy;
import shop.itbook.itbookshop.deliverygroup.delivery.entity.Delivery;
import shop.itbook.itbookshop.deliverygroup.delivery.repository.DeliveryRepository;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderTotalResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordermember.dummy.OrderMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy.OrderStatusHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.ordersubscription.dummy.OrderSubscriptionDummy;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderStatusHistoryRepository orderStatusHistoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    OrderMemberRepository orderMemberRepository;
    @Autowired
    UsageStatusRepository usageStatusRepository;
    @Autowired
    CouponTypeRepository couponTypeRepository;
    @Autowired
    CouponIssueRepository couponIssueRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;

    @Autowired
    OrderSubscriptionRepository orderSubscriptionRepository;

    @Autowired
    OrderTotalCouponRepository orderTotalCouponRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        testEntityManager.clear();
    }

    @Test
    @DisplayName("Order 엔티티 저장 성공")
    void saveSuccessTest() {
        Order order = OrderDummy.getOrder();

        orderRepository.save(order);
    }

    @Test
    @DisplayName("사용자가 주문 조회 했을 경우 상태값과 함께 반환 성공")
    void getOrderListOfMemberWithStatus() {
        Order order = orderRepository.save(OrderDummy.getOrder());
        Product product = productRepository.save(ProductDummy.getProductSuccess());
        OrderProduct orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        Membership membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
        orderStatusHistoryRepository.save(orderStatusHistory);

        Member member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        Delivery delivery = DeliveryDummy.createDelivery(order);
        deliveryRepository.save(delivery);

        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);
        orderMemberRepository.save(orderMember);

        PageRequest pageable = PageRequest.of(0, 10);

        Page<OrderListMemberViewResponseDto> orderListOfMemberWithStatus =
            orderRepository.getOrderListOfMemberWithStatus(pageable, member.getMemberNo());

        assertThat(orderListOfMemberWithStatus.getContent().get(0).getOrderNo()).isEqualTo(
            order.getOrderNo());
    }

    @DisplayName("주문들을 in절을 통해 잘 가져온다.")
    @Test
    void findOrdersByOrderNoIn() {
        Order order1 = orderRepository.save(OrderDummy.getOrder());
        Order order2 = orderRepository.save(OrderDummy.getOrder());
        testEntityManager.clear();
        List<Order> actual = orderRepository.findOrdersByOrderNoIn(
            List.of(order1.getOrderNo(), order2.getOrderNo()));

        assertThat(actual.get(0).getOrderNo())
            .isEqualTo(order1.getOrderNo());
        assertThat(actual.get(0).getRecipientName())
            .isEqualTo(order1.getRecipientName());
        assertThat(actual.get(1).getOrderNo())
            .isEqualTo(order2.getOrderNo());
        assertThat(actual.get(1).getRecipientName())
            .isEqualTo(order2.getRecipientName());

    }

//    @DisplayName("결제완료 상태인 구독상품 리스트 가져오기")
//    @Test
//    void paymentCompleteSubscriptionProductStatusChangeWaitDelivery() {
//        // given
//        Order order = orderRepository.save(OrderDummy.getOrder());
//        OrderSubscription orderSubscription =
//            OrderSubscriptionDummy.createOrderSubscription(order);
//
//        orderSubscriptionRepository.save(orderSubscription);
//
//        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
//        orderStatusRepository.save(orderStatus);
//
//        OrderStatusHistory orderStatusHistory =
//            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
//        orderStatusHistoryRepository.save(orderStatusHistory);
//
//        // when
//        List<Order> orders =
//            orderRepository.paymentCompleteSubscriptionProductStatusChangeWaitDelivery();
//
//        // then
//        assertThat(orders.get(0)).isEqualTo(order);
//    }

    @DisplayName("구독 상품 페이지를위한 구독 상품 리스트 불러오기 테스트")
    @Test
    void findAllSubscriptionOrderListByAdminTest() {
        // given
        Order order = orderRepository.save(OrderDummy.getOrder());
        OrderSubscription orderSubscription =
            OrderSubscriptionDummy.createOrderSubscription(order);

        orderSubscriptionRepository.save(orderSubscription);

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
        orderStatusHistoryRepository.save(orderStatusHistory);

        Delivery delivery = DeliveryDummy.createDelivery(order);
        deliveryRepository.save(delivery);

        Membership membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        Member member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        Product product = productRepository.save(ProductDummy.getProductSuccess());
        OrderProduct orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);
        orderMemberRepository.save(orderMember);

        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<OrderSubscriptionAdminListDto> allSubscriptionOrderList =
            orderRepository.findAllSubscriptionOrderListByAdmin(pageable);

        // then
        assertThat(allSubscriptionOrderList.getContent().get(0).getOrderNo())
            .isEqualTo(order.getOrderNo());
    }

    @DisplayName("회원 주문 구독 리스트 조회 테스트 ")
    @Test
    void findAllSubscriptionOrderListByMemberTest() {
        // given
        Order order = orderRepository.save(OrderDummy.getOrder());
        OrderSubscription orderSubscription =
            OrderSubscriptionDummy.createOrderSubscription(order);

        orderSubscriptionRepository.save(orderSubscription);

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
        orderStatusHistoryRepository.save(orderStatusHistory);

        Delivery delivery = DeliveryDummy.createDelivery(order);
        deliveryRepository.save(delivery);

        Membership membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        Member member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        Product product = productRepository.save(ProductDummy.getProductSuccess());
        OrderProduct orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);
        orderMemberRepository.save(orderMember);

        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<OrderSubscriptionListDto> allSubscriptionOrderListByMember =
            orderRepository.findAllSubscriptionOrderListByMember(pageable, member.getMemberNo());

        // then
        OrderSubscriptionListDto orderSubscriptionListDto =
            allSubscriptionOrderListByMember.getContent().get(0);

        assertThat(orderSubscriptionListDto.getOrderNo())
            .isEqualTo(orderMember.getOrder().getOrderNo());
    }

    @Test
    @DisplayName("주문 구독 상세 Dto 조회 성공 테스트")
    void findOrderSubscriptionDetailsResponseDtoSuccessTest() {

        // given
        Order order = orderRepository.save(OrderDummy.getOrder());
        OrderSubscription orderSubscription =
            OrderSubscriptionDummy.createOrderSubscription(order);

        orderSubscriptionRepository.save(orderSubscription);

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
        orderStatusHistoryRepository.save(orderStatusHistory);

        Product product = ProductDummy.getProductSuccess();
        productRepository.save(product);

        OrderProduct orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        Delivery delivery = DeliveryDummy.createDelivery(order);
        deliveryRepository.save(delivery);

        Membership membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        Member member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        UsageStatus usageStatus = UsageStatusDummy.getUsageStatus();
        usageStatusRepository.save(usageStatus);

        CouponType couponType = CouponTypeDummy.getCouponType();
        couponTypeRepository.save(couponType);

        Coupon coupon = CouponDummy.getPercentCoupon();
        coupon.setCouponType(couponType);
        couponRepository.save(coupon);

        CouponIssue couponIssue = CouponIssueDummy.getCouponIssue2(member, coupon, usageStatus);

        couponIssueRepository.save(couponIssue);

        OrderTotalCoupon orderTotalCoupon = OrderTotalCouponDummy.getOrderTotalCouponDummy(coupon);
        orderTotalCouponRepository.save(orderTotalCoupon);

        OrderTotalCouponApply orderTotalCouponApply =
            OrderTotalCouponApplyDummy.getOrderTotalCouponApply(couponIssue, order);

        orderTotalCouponApplyRepositoy.save(orderTotalCouponApply);

        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsResponseDto =
            orderRepository.findOrderSubscriptionDetailsResponseDto(1L);

        assertThat(orderSubscriptionDetailsResponseDto.size()).isEqualTo(1);
    }

//    @DisplayName("주문 상세 조회 테스트")
//    @Test
//    void findOrderDetailTest() {
//        // given
//        Order order = orderRepository.save(OrderDummy.getOrder());
//        OrderSubscription orderSubscription =
//            OrderSubscriptionDummy.createOrderSubscription(order);
//
//        orderSubscriptionRepository.save(orderSubscription);
//
//        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
//        orderStatusRepository.save(orderStatus);
//
//        OrderStatusHistory orderStatusHistory =
//            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
//        orderStatusHistoryRepository.save(orderStatusHistory);
//
//        Delivery delivery = DeliveryDummy.createDelivery(order);
//        deliveryRepository.save(delivery);
//
//        Membership membership = MembershipDummy.getMembership();
//        membershipRepository.save(membership);
//
//        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
//        memberStatusRepository.save(normalMemberStatus);
//
//        Member member = MemberDummy.getMember1();
//        member.setMembership(membership);
//        member.setMemberStatus(normalMemberStatus);
//        memberRepository.save(member);
//
//        UsageStatus usageStatus = UsageStatusDummy.getUsageStatus();
//        usageStatusRepository.save(usageStatus);
//
//        CouponType couponType = CouponTypeDummy.getCouponType();
//        couponTypeRepository.save(couponType);
//
//        Coupon coupon = CouponDummy.getPercentCoupon();
//        coupon.setCouponType(couponType);
//        couponRepository.save(coupon);
//
//        CouponIssue couponIssue = CouponIssueDummy.getCouponIssue2(member, coupon, usageStatus);
//
//        couponIssueRepository.save(couponIssue);
//
//        OrderTotalCoupon orderTotalCoupon = OrderTotalCouponDummy.getOrderTotalCouponDummy(coupon);
//        orderTotalCouponRepository.save(orderTotalCoupon);
//
//        OrderTotalCouponApply orderTotalCouponApply =
//            OrderTotalCouponApplyDummy.getOrderTotalCouponApply(couponIssue, order);
//
//        orderTotalCouponApplyRepositoy.save(orderTotalCouponApply);
//
//        // when
//        OrderTotalResponseDto orderDetail = orderRepository.findOrderDetail(order.getOrderNo());
//
//        // then
//        assertThat(orderDetail.getOrderNo()).isEqualTo(order.getOrderNo());
//    }
}