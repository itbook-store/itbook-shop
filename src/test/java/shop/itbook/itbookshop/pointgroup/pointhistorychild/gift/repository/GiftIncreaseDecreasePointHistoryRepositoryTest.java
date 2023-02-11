package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.repository;

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
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class GiftIncreaseDecreasePointHistoryRepositoryTest {

    @Autowired
    GiftIncreaseDecreasePointHistoryRepository giftIncreaseDecreasePointHistoryRepository;

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

    Member member2;
    Membership membership2;
    MemberStatus normalMemberStatus2;
    PointIncreaseDecreaseContent orderIncreasePointIncreaseDecreaseContent;
    PointHistory dummyPointHistory1;
    PointHistory dummyPointHistory2;
    GiftIncreaseDecreasePointHistory giftIncreaseDecreasePointHistory1;
    GiftIncreaseDecreasePointHistory giftIncreaseDecreasePointHistory2;


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

        member2 = MemberDummy.getMember2();

        member2.setMemberStatus(normalMemberStatus1);
        member2.setMembership(membership1);
        memberRepository.save(member2);

        dummyPointHistory1 = PointHistoryDummy.getPointHistory();
        dummyPointHistory1.setMember(member1);
        dummyPointHistory1.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory1);

        dummyPointHistory2 = PointHistoryDummy.getPointHistory();
        dummyPointHistory2.setMember(member2);
        dummyPointHistory2.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory2);


        giftIncreaseDecreasePointHistory2 = new GiftIncreaseDecreasePointHistory(
            dummyPointHistory2.getPointHistoryNo(), member1);

        giftIncreaseDecreasePointHistoryRepository.save(giftIncreaseDecreasePointHistory2);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("선물적립 포인트내역이 잘 저장된다.")
    @Test
    void save() {
        giftIncreaseDecreasePointHistory1 = new GiftIncreaseDecreasePointHistory(
            dummyPointHistory1.getPointHistoryNo(), member1);

        giftIncreaseDecreasePointHistoryRepository.save(giftIncreaseDecreasePointHistory1);

        entityManager.flush();
        entityManager.clear();

        GiftIncreaseDecreasePointHistory actual =
            entityManager.find(GiftIncreaseDecreasePointHistory.class,
                giftIncreaseDecreasePointHistory1.getPointHistoryNo());

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(giftIncreaseDecreasePointHistory1.getPointHistoryNo());
    }

    @DisplayName("선물적립 포인트내역이 잘 조회된다.")
    @Test
    void find() {
        GiftIncreaseDecreasePointHistory actual =
            giftIncreaseDecreasePointHistoryRepository.findById(
                giftIncreaseDecreasePointHistory2.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(giftIncreaseDecreasePointHistory2.getPointHistoryNo());
    }

    @DisplayName("선물 포인트내역을 조인해서 상세정보를 잘 받아온다.")
    @Test
    void findPointHistoryGiftDetailsResponseDto() {

        PointHistoryGiftDetailsResponseDto actual =
            pointHistoryRepository.findPointHistoryGiftDetailsResponseDto(
                dummyPointHistory2.getPointHistoryNo());

        assertThat(actual.getPoint())
            .isEqualTo(dummyPointHistory2.getIncreaseDecreasePoint());

        assertThat(actual.getRemainedPoint())
            .isEqualTo(dummyPointHistory2.getRemainedPoint());

        assertThat(actual.getMainMemberId())
            .isEqualTo(member2.getMemberId());

        assertThat(actual.getSubMemberId())
            .isEqualTo(member1.getMemberId());
    }

    @DisplayName("특정 멤버의 선물 포인트내역을 조인해서 상세정보를 잘 받아온다.")
    @Test
    void findMyPointHistoryGiftDetailsResponseDto() {

        PointHistoryGiftDetailsResponseDto actual =
            pointHistoryRepository.findMyPointHistoryGiftDetailsResponseDto(
                dummyPointHistory2.getPointHistoryNo(), member2.getMemberNo());

        assertThat(actual.getPoint())
            .isEqualTo(dummyPointHistory2.getIncreaseDecreasePoint());

        assertThat(actual.getRemainedPoint())
            .isEqualTo(dummyPointHistory2.getRemainedPoint());

        assertThat(actual.getMainMemberId())
            .isEqualTo(member2.getMemberId());

        assertThat(actual.getSubMemberId())
            .isEqualTo(member1.getMemberId());
    }


}