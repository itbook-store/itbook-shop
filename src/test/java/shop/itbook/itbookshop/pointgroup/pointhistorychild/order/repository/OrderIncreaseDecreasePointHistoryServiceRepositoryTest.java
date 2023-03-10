package shop.itbook.itbookshop.pointgroup.pointhistorychild.order.repository;

import static org.assertj.core.api.Assertions.assertThat;

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
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author ?????????
 * @since 1.0
 */
@DataJpaTest
class OrderIncreaseDecreasePointHistoryServiceRepositoryTest {


    @Autowired
    OrderIncreaseDecreasePointHistoryRepository orderIncreaseDecreasePointHistoryRepository;

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

    OrderIncreaseDecreasePointHistory increasePointHistory;
    OrderIncreaseDecreasePointHistory decreasePointHistory;


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

        decreasePointHistory = new OrderIncreaseDecreasePointHistory(
            dummyPointHistory2.getPointHistoryNo(), order2);

        orderIncreaseDecreasePointHistoryRepository.save(decreasePointHistory);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("???????????? ?????????????????? ??? ????????????.")
    @Test
    void save() {
        increasePointHistory = new OrderIncreaseDecreasePointHistory(
            dummyPointHistory1.getPointHistoryNo(), order1);

        orderIncreaseDecreasePointHistoryRepository.save(increasePointHistory);

        entityManager.flush();
        entityManager.clear();

        OrderIncreaseDecreasePointHistory actual =
            entityManager.find(OrderIncreaseDecreasePointHistory.class,
                increasePointHistory.getPointHistoryNo());

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(increasePointHistory.getPointHistoryNo());
    }

    @DisplayName("???????????? ?????????????????? ??? ????????????.")
    @Test
    void find() {
        OrderIncreaseDecreasePointHistory actual =
            orderIncreaseDecreasePointHistoryRepository.findById(
                decreasePointHistory.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(decreasePointHistory.getPointHistoryNo());
    }
}