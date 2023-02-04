package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
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

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CouponIssueRepositoryTest {

    @Autowired
    CouponIssueRepository couponIssueRepository;
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UsageStatusRepository usageStatusRepository;
    @Autowired
    CouponTypeRepository couponTypeRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    MembershipRepository membershipRepository;

    Membership membership;
    MemberStatus memberStatus;
    Coupon coupon;
    UsageStatus usageStatus;
    Member member;
    CouponIssue couponIssue;
    CouponType couponType;

    @BeforeEach
    void setup(){
        usageStatus = usageStatusRepository.save(UsageStatusDummy.getUsageStatus());
        memberStatus = memberStatusRepository.save(MemberStatusDummy.getWithdrawMemberStatus());
        membership = membershipRepository.save(MembershipDummy.getMembership());
        member = MemberDummy.getMember1();
        member.setMemberStatus(memberStatus);
        member.setMembership(membership);
        member = memberRepository.save(member);
        couponType = couponTypeRepository.save(CouponTypeDummy.getCouponType());
        coupon = CouponDummy.getPercentCoupon();
        coupon.setCouponType(couponType);
        coupon = couponRepository.save(coupon);

        couponIssue = CouponIssueDummy.getCouponIssue();
        couponIssue.setCoupon(coupon);
        couponIssue.setMember(member);
        couponIssue.setUsageStatus(usageStatus);
        couponIssueRepository.save(couponIssue);

        testEntityManager.flush();
        testEntityManager.clear();
    }
    @Test
    void find_by_id(){
        CouponIssue couponIssue1 = couponIssueRepository.findById(couponIssue.getCouponIssueNo()).orElseThrow();

        assertThat(couponIssue1.getCoupon().getCode()).isEqualTo(couponIssue.getCoupon().getCode());
    }
}