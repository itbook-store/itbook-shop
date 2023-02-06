package shop.itbook.itbookshop.ordergroup.ordermember.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.dummy.MemberDestinationDummy;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.ordermember.dummy.OrderMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;

/**
 * @author 정재원
 * @since 1.0
 */
@DataJpaTest
class OrderMemberRepositoryTest {

    @Autowired
    OrderMemberRepository orderMemberRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberDestinationRepository memberDestinationRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    TestEntityManager testEntityManager;

    Member member;
    Order order;

    @BeforeEach
    void setUp() {
        // MemberStatus, MemberShip
        MemberStatus normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);
        Membership memberShip = MembershipDummy.getMembership();
        membershipRepository.save(memberShip);

        testEntityManager.flush();

        // Member
        member = MemberDummy.getMember1();
        member.setMemberStatus(normalMemberStatus);
        member.setMembership(memberShip);
        memberRepository.save(member);

        testEntityManager.flush();

        // Order
        order = OrderDummy.getOrder();
        orderRepository.save(order);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("회원의 주문정보 테이블에 save 성공")
    void saveSuccessTest() {
        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);
        orderMember.setOrder(order);
        orderMember.setOrderNo(order.getOrderNo());

        OrderMember savedOrderMember = orderMemberRepository.save(orderMember);

        assertThat(savedOrderMember.getOrder().getOrderNo()).isEqualTo(
            orderMember.getOrder().getOrderNo());
    }

    @Test
    @DisplayName("주문 번호로 조회 성공")
    void findByOrder_OrderNo() {
        OrderMember orderMember = OrderMemberDummy.createOrderMember(order, member);

        orderMemberRepository.save(orderMember);

        OrderMember savedOrderMember =
            orderMemberRepository.findById(order.getOrderNo()).orElseThrow();

        assertThat(savedOrderMember.getOrder().getOrderNo()).isEqualTo(
            orderMember.getOrder().getOrderNo());
    }
}