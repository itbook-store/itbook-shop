package shop.itbook.itbookshop.ordergroup.ordermember.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderService;
import shop.itbook.itbookshop.ordergroup.ordermember.dummy.OrderMemberDummy;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordermember.service.OrderMemberService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(OrderMemberServiceImpl.class)
class OrderMemberServiceImplTest {

    @Autowired
    OrderMemberService orderMemberService;

    @MockBean
    OrderService orderService;

    @MockBean
    MemberService memberService;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    OrderMemberRepository orderMemberRepository;

    Order dummyOrder;

    Member dummyMember;

    OrderMember dummyOrderMember;

    Membership dummyMembership;

    MemberStatus dummyMemberStatus;


    @BeforeEach
    void setUp() {
        dummyMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(dummyMemberStatus);

        dummyMembership = MembershipDummy.getMembership();
        membershipRepository.save(dummyMembership);

        dummyMember = MemberDummy.getMember1();
        dummyMember.setMemberStatus(dummyMemberStatus);
        dummyMember.setMembership(dummyMembership);
        memberRepository.save(dummyMember);

        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyOrderMember = OrderMemberDummy.createOrderMember(dummyOrder, dummyMember);
        orderMemberRepository.save(dummyOrderMember);
    }

    @Test
    void findOptionalOrderMember() {
        given(orderMemberRepository.findById(any())).willReturn(Optional.of(dummyOrderMember));

        assertThat(orderMemberService.findOptionalOrderMember(1L).get().getOrderNo()).isEqualTo(
            dummyOrderMember.getOrder().getOrderNo());
    }
}