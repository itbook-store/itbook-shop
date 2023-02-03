package shop.itbook.itbookshop.pointgroup.pointhistory.repository;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointHistoryRepositoryTest {

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
    PointHistory dummyPointHistory;

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

        dummyPointHistory = PointHistoryDummy.getPointHistory();
        dummyPointHistory.setMember(member1);
        dummyPointHistory.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("포인트 내역 저장이 잘 이루어진다.")
    @Test
    void save() {

        PointHistory pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setMember(member1);
        pointHistory.setPointIncreaseDecreaseContent(orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(pointHistory);

        entityManager.flush();
        entityManager.clear();

        PointHistory actualPointHistory =
            entityManager.find(PointHistory.class, pointHistory.getPointHistoryNo());

        assertThat(actualPointHistory.getPointHistoryNo())
            .isEqualTo(pointHistory.getPointHistoryNo());
        assertThat(actualPointHistory.getRemainedPoint())
            .isEqualTo(pointHistory.getRemainedPoint());
        assertThat(actualPointHistory.getHistoryCreatedAt())
            .isEqualTo(pointHistory.getHistoryCreatedAt());
    }

    @DisplayName("저장된 포인트내역을 잘 불러온다.")
    @Test
    void find() {
        PointHistory actual =
            pointHistoryRepository.findById(dummyPointHistory.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(dummyPointHistory.getPointHistoryNo());
        assertThat(actual.getHistoryCreatedAt())
            .isEqualTo(dummyPointHistory.getHistoryCreatedAt());
        assertThat(actual.getIncreaseDecreasePoint())
            .isEqualTo(dummyPointHistory.getIncreaseDecreasePoint());
    }
}