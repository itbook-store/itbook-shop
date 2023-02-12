package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
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
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class CouponIncreasePointHistoryRepositoryTest {

    @Autowired
    TestEntityManager entityManager;


    @Autowired
    CouponIncreasePointHistoryRepository couponIncreasePointHistoryRepository;

    @Autowired
    CouponIssueRepository couponIssueRepository;
    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    UsageStatusRepository usageStatusRepository;

    Coupon pointDummyCoupon;

    CouponType couponType;

    CouponIssue couponIssue;

    UsageStatus usageStatus;

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
    PointHistory dummyPointHistory2;


    @BeforeEach
    void setUp() {
        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        pointDummyCoupon = CouponDummy.getPointCoupon();
        pointDummyCoupon.setCouponType(couponType);

        pointDummyCoupon.setPoint(1000L);
        pointDummyCoupon = couponRepository.save(pointDummyCoupon);

        member1 = MemberDummy.getMember1();
        membership1 = MembershipDummy.getMembership();
        membershipRepository.save(membership1);

        normalMemberStatus1 = MemberStatusDummy.getNormalMemberStatus();
        normalMemberStatus1 = memberStatusRepository.save(normalMemberStatus1);

        member1.setMemberStatus(normalMemberStatus1);
        member1.setMembership(membership1);
        member1.setIsSocial(false);
        member1 = memberRepository.save(member1);

        usageStatus = usageStatusRepository.save(UsageStatusDummy.getAvailableUsageStatus());
        couponIssue = CouponIssueDummy.getCouponIssue();
        couponIssue.setCoupon(pointDummyCoupon);
        couponIssue.setMember(member1);
        couponIssue.setUsageStatus(usageStatus);
        couponIssue = couponIssueRepository.save(couponIssue);

        orderIncreasePointIncreaseDecreaseContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();
        orderIncreasePointIncreaseDecreaseContent =
            pointIncreaseDecreaseContentRepository.save(orderIncreasePointIncreaseDecreaseContent);

        dummyPointHistory = PointHistoryDummy.getPointHistory();
        dummyPointHistory.setMember(member1);
        dummyPointHistory.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        dummyPointHistory = pointHistoryRepository.save(dummyPointHistory);

        dummyPointHistory2 = PointHistoryDummy.getPointHistory();
        dummyPointHistory2.setMember(member1);
        dummyPointHistory2.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        dummyPointHistory2 = pointHistoryRepository.save(dummyPointHistory2);

        CouponIncreasePointHistory couponIncreasePointHistory =
            new CouponIncreasePointHistory(dummyPointHistory2.getPointHistoryNo(),
                dummyPointHistory,
                couponIssue);

        couponIncreasePointHistoryRepository.save(couponIncreasePointHistory);

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("쿠폰 적립 포인트내역이 잘 저장된다.")
    @Test
    void save() {

        // TODO : 매니저님
        // * 도대체 왜 persist로 이걸 저장할때 부모가 저장이 왜 안되는가?
        // -> id의 전략을 identity로 하면 된다.
        // * 이경우는 merge, persist를 모두 준 경우에만 됨 굳이 자식에서 부모를 바꿀 필요가 없는 경우에는 cascade 다지우고 아래 주석으로 하는 것 이 옳은 것인가?
//        CouponIncreasePointHistory couponIncreasePointHistory =
//            new CouponIncreasePointHistory(dummyPointHistory.getPointHistoryNo(), dummyPointHistory,
//                couponIssue);

        Long pointHistoryNo = dummyPointHistory.getPointHistoryNo();
        CouponIncreasePointHistory couponIncreasePointHistory =
            new CouponIncreasePointHistory(pointHistoryNo,
                couponIssue);

        couponIncreasePointHistory =
            couponIncreasePointHistoryRepository.save(couponIncreasePointHistory);

        entityManager.flush();
        entityManager.clear();

        CouponIncreasePointHistory actual = couponIncreasePointHistoryRepository.findById(
            couponIncreasePointHistory.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(couponIncreasePointHistory.getPointHistoryNo());
    }


    @DisplayName("저장된 dummyPointHistorty를 잘 가져온다.")
    @Test
    void find() {
        CouponIncreasePointHistory actual =
            couponIncreasePointHistoryRepository.findById(dummyPointHistory2.getPointHistoryNo())
                .get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(dummyPointHistory2.getPointHistoryNo());
    }


    @DisplayName("쿠폰 포인트 적립에 대한 상세 정보를 잘 불러온다.")
    @Test
    void findPointHistoryCouponDetailsDto() {
        PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsDto =
            pointHistoryRepository.findPointHistoryCouponDetailsDto(
                dummyPointHistory2.getPointHistoryNo());

        assertThat(pointHistoryCouponDetailsDto.getCouponPoint())
            .isEqualTo(pointDummyCoupon.getPoint());
        assertThat(pointHistoryCouponDetailsDto.getCouponName())
            .isEqualTo(pointDummyCoupon.getName());
        assertThat(pointHistoryCouponDetailsDto.getRemainedPoint())
            .isEqualTo(dummyPointHistory2.getRemainedPoint());
        assertThat(pointHistoryCouponDetailsDto.getMemberId())
            .isEqualTo(member1.getMemberId());
    }

    @DisplayName("특정 멤버에 대한 쿠폰 포인트 적립에 대한 상세 정보를 잘 불러온다.")
    @Test
    void findMyPointHistoryCouponDetailsDto() {
        PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsDto =
            pointHistoryRepository.findMyPointHistoryCouponDetailsDto(
                dummyPointHistory2.getPointHistoryNo(), member1.getMemberNo());

        assertThat(pointHistoryCouponDetailsDto.getCouponPoint())
            .isEqualTo(pointDummyCoupon.getPoint());
        assertThat(pointHistoryCouponDetailsDto.getCouponName())
            .isEqualTo(pointDummyCoupon.getName());
        assertThat(pointHistoryCouponDetailsDto.getRemainedPoint())
            .isEqualTo(dummyPointHistory2.getRemainedPoint());
        assertThat(pointHistoryCouponDetailsDto.getMemberId())
            .isEqualTo(member1.getMemberId());
    }
}