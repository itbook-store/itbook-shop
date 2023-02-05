package shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.repository;

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
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.entity.GradeIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class GradeIncreasePointHistoryRepositoryTest {
    @Autowired
    GradeIncreasePointHistoryRepository gradeIncreasePointHistoryRepository;


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
    GradeIncreasePointHistory gradeIncreasePointHistory1;
    GradeIncreasePointHistory gradeIncreasePointHistory2;


    Membership membership;

    @BeforeEach
    void setUp() {

        orderIncreasePointIncreaseDecreaseContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();

        pointIncreaseDecreaseContentRepository.save(orderIncreasePointIncreaseDecreaseContent);
        member1 = MemberDummy.getMember1();

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


        gradeIncreasePointHistory2 = new GradeIncreasePointHistory(
            dummyPointHistory2.getPointHistoryNo(), membership1);

        gradeIncreasePointHistoryRepository.save(gradeIncreasePointHistory2);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("등급적립 포인트내역이 잘 저장된다.")
    @Test
    void save() {
        gradeIncreasePointHistory1 = new GradeIncreasePointHistory(
            dummyPointHistory1.getPointHistoryNo(), membership1);

        gradeIncreasePointHistoryRepository.save(gradeIncreasePointHistory1);

        entityManager.flush();
        entityManager.clear();

        GradeIncreasePointHistory actual =
            entityManager.find(GradeIncreasePointHistory.class,
                gradeIncreasePointHistory1.getPointHistoryNo());

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(gradeIncreasePointHistory1.getPointHistoryNo());
    }

    @DisplayName("등급적립 포인트내역이 잘 조회된다.")
    @Test
    void find() {
        GradeIncreasePointHistory actual =
            gradeIncreasePointHistoryRepository.findById(
                gradeIncreasePointHistory2.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(gradeIncreasePointHistory2.getPointHistoryNo());
    }
}