package shop.itbook.itbookshop.ordergroup.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dummy.CategoryCouponDummy;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.dummy.CategoryCouponApplyDummy;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
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
import shop.itbook.itbookshop.coupongroup.productcoupon.dummy.ProductCouponDummy;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.dummy.ProductCouponApplyDummy;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
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
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordermember.dummy.OrderMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.dummy.OrderStatusHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.repository.OrderStatusHistoryRepository;
import shop.itbook.itbookshop.ordergroup.ordersubscription.dummy.OrderSubscriptionDummy;
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.OrderSubscription;
import shop.itbook.itbookshop.ordergroup.ordersubscription.repository.OrderSubscriptionRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author ?????????
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
    ProductCouponRepository productCouponRepository;

    @Autowired
    ProductCouponApplyRepository productCouponApplyRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryCouponRepository categoryCouponRepository;

    @Autowired
    CategoryCouponApplyRepository categoryCouponApplyRepository;

    @Autowired
    TestEntityManager testEntityManager;

    OrderStatus dummyOrderStatus;

    Order dummyOrder;

    OrderStatusHistory dummyOrderStatusHistory;

    Member dummyMember;

    Membership dummyMembership;

    MemberStatus dummyMemberStatus;

    OrderProduct dummyOrderProduct;

    Product dummyProduct;

    Delivery dummyDelivery;

    OrderSubscription dummyOrderSubscription;

    @BeforeEach
    void setUp() {

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("Order ????????? ?????? ??????")
    void saveSuccessTest() {
        Order order = OrderDummy.getOrder();

        orderRepository.save(order);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ?????? ?????? ???????????? ?????? ?????? ??????")
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

    @Test
    void getOrderListOfAdminWithStatus() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<OrderListAdminViewResponseDto> page =
            orderRepository.getOrderListOfAdminWithStatus(pageRequest);

        List<OrderListAdminViewResponseDto> orderList = page.getContent();

        assertThat(orderList.size()).isEqualTo(0);
    }

    @DisplayName("???????????? in?????? ?????? ??? ????????????.")
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

//    @DisplayName("???????????? ????????? ???????????? ????????? ????????????")
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

    @DisplayName("?????? ?????? ?????????????????? ?????? ?????? ????????? ???????????? ?????????")
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
            orderRepository.findAllSubscriptionOrderListOfAdmin(pageable);

        // then
        assertThat(allSubscriptionOrderList.getContent().get(0).getOrderNo())
            .isEqualTo(order.getOrderNo());
    }

    @DisplayName("?????? ?????? ?????? ????????? ?????? ????????? ")
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
            orderRepository.findAllSubscriptionOrderListOfMember(pageable, member.getMemberNo());

        // then
        OrderSubscriptionListDto orderSubscriptionListDto =
            allSubscriptionOrderListByMember.getContent().get(0);

        assertThat(orderSubscriptionListDto.getOrderNo())
            .isEqualTo(orderMember.getOrder().getOrderNo());
    }

    @Test
    @DisplayName("?????? ?????? ?????? Dto ?????? ?????? ?????????")
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

        CouponType couponType = CouponTypeDummy.getCouponType2();
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
            orderRepository.findOrderSubscriptionDetailsResponseDto(order.getOrderNo());

        assertThat(orderSubscriptionDetailsResponseDto.size()).isEqualTo(1);
    }

    @DisplayName("?????? ?????? ?????? ?????? ?????? ?????????")
    @Test
    void findOrderDetailTest() {
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

        UsageStatus usageStatus = UsageStatusDummy.getUsageStatus();
        usageStatusRepository.save(usageStatus);

        CouponType couponType = CouponTypeDummy.getCouponType2();
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

        ProductCoupon productCoupon = ProductCouponDummy.getProductCoupon(coupon, product);
        productCouponRepository.save(productCoupon);

        ProductCouponApply productCouponApply =
            ProductCouponApplyDummy.getProductCouponApply(couponIssue, orderProduct);
        productCouponApplyRepository.save(productCouponApply);

        Category category = CategoryDummy.getCategoryNoHiddenBook();
        categoryRepository.save(category);

        CategoryCoupon categoryCoupon = CategoryCouponDummy.getCategoryCoupon(coupon, category);
        categoryCouponRepository.save(categoryCoupon);

        CategoryCouponApply categoryCouponApply =
            CategoryCouponApplyDummy.getCategoryCouponApply(couponIssue, orderProduct);
        categoryCouponApplyRepository.save(categoryCouponApply);

        // when
        OrderDetailsResponseDto orderDetail = orderRepository.findOrderDetail(order.getOrderNo());

        // then
        assertThat(orderDetail.getOrderNo()).isEqualTo(order.getOrderNo());
    }

    @DisplayName("?????? ?????? ????????? Order ?????? ???????????? ?????? ?????????")
    @Test
    void findOrderOfLatestStatusTest() {
        //given
        Order order = orderRepository.save(OrderDummy.getOrder());
        OrderSubscription orderSubscription =
            OrderSubscriptionDummy.createOrderSubscription(order);

        orderSubscriptionRepository.save(orderSubscription);

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        OrderStatusHistory orderStatusHistory =
            OrderStatusHistoryDummy.createOrderStatusHistory(order, orderStatus);
        orderStatusHistoryRepository.save(orderStatusHistory);

        // when
        Optional<Order> orderOfLatestStatus =
            orderRepository.findOrderOfLatestStatus(order.getOrderNo());

        // then
        assertThat(orderOfLatestStatus.get().getOrderNo()).isEqualTo(order.getOrderNo());

    }

    @Test
    void findOrderDetailOfNonMember() {

    }

}