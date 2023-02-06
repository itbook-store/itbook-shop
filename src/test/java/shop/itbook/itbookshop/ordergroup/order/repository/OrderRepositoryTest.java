package shop.itbook.itbookshop.ordergroup.order.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordermember.dummy.OrderMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.dummy.OrderProductHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.repository.OrderProductHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
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
    OrderProductHistoryRepository orderProductHistoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    OrderMemberRepository orderMemberRepository;

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

        OrderStatus orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.DEPOSIT_COMPLETE);
        orderStatusRepository.save(orderStatus);

        Membership membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        OrderProductHistory orderProductHistory =
            OrderProductHistoryDummy.createOrderProductHistory(orderProduct, orderStatus);
        orderProductHistoryRepository.save(orderProductHistory);

        Member member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        Delivery delivery = DeliveryDummy.createDelivery(order);
        deliveryRepository.save(delivery);

        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);
        orderMemberRepository.save(orderMember);

        PageRequest pageable = PageRequest.of(0, 10);

        Page<OrderListViewResponseDto> orderListOfMemberWithStatus =
            orderRepository.getOrderListOfMemberWithStatus(pageable, 1L);

        assertThat(orderListOfMemberWithStatus.getContent().get(0).getMemberId()).isEqualTo(
            member.getMemberId());
    }
}