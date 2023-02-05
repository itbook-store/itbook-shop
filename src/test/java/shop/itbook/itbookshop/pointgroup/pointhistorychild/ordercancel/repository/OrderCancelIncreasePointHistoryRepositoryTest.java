package shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.OrderIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.repository.OrderIncreaseDecreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class OrderCancelIncreasePointHistoryRepositoryTest {

    @Autowired
    OrderCancelIncreasePointHistoryRepository orderIncreaseDecreasePointHistoryRepository;

    @Autowired
    OrderRepository orderRepository;
    Order order1;
    Order order2;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    Member member1;
    Membership membership1;
    MemberStatus normalMemberStatus1;
    PointIncreaseDecreaseContent orderIncreasePointIncreaseDecreaseContent;
    PointHistory dummyPointHistory1;
    PointHistory dummyPointHistory2;
    OrderCancelIncreasePointHistory orderCancelIncreasePointHistory1;
    OrderCancelIncreasePointHistory orderCancelIncreasePointHistory2;


    @BeforeEach
    void setUp() {
        member1 = MemberDummy.getMember1();

        orderIncreasePointIncreaseDecreaseContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();

        pointIncreaseDecreaseContentRepository.save(orderIncreasePointIncreaseDecreaseContent);

        membership1 = MembershipDummy.getMembership();
        membershipRepository.save(membership1);

        normalMemberStatus1 = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus1);

        member1.setMemberStatus(normalMemberStatus1);
        member1.setMembership(membership1);
        memberRepository.save(member1);

        dummyPointHistory1 = PointHistoryDummy.getPointHistory();
        dummyPointHistory1.setMember(member1);
        dummyPointHistory1.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory1);

        dummyPointHistory2 = PointHistoryDummy.getPointHistory();
        dummyPointHistory2.setMember(member1);
        dummyPointHistory2.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory2);

        order1 = OrderDummy.getOrder();
        orderRepository.save(order1);

        order2 = OrderDummy.getOrder();
        orderRepository.save(order2);

        orderCancelIncreasePointHistory2 = new OrderCancelIncreasePointHistory(
            dummyPointHistory2.getPointHistoryNo(), order2);

        orderIncreaseDecreasePointHistoryRepository.save(orderCancelIncreasePointHistory2);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("주문취소적립 포인트내역이 잘 저장된다.")
    @Test
    void save() {
        orderCancelIncreasePointHistory1 = new OrderCancelIncreasePointHistory(
            dummyPointHistory1.getPointHistoryNo(), order1);

        orderIncreaseDecreasePointHistoryRepository.save(orderCancelIncreasePointHistory1);

        entityManager.flush();
        entityManager.clear();

        OrderCancelIncreasePointHistory actual =
            entityManager.find(OrderCancelIncreasePointHistory.class,
                orderCancelIncreasePointHistory1.getPointHistoryNo());

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(orderCancelIncreasePointHistory1.getPointHistoryNo());
    }

    @DisplayName("주문취소적립 포인트내역이 잘 조회된다.")
    @Test
    void find() {
        OrderCancelIncreasePointHistory actual =
            orderIncreaseDecreasePointHistoryRepository.findById(
                orderCancelIncreasePointHistory2.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(orderCancelIncreasePointHistory2.getPointHistoryNo());
    }
}