package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.CouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository.CouponIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.service.CouponIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponIncreasePointHistoryServiceImpl.class)
class CouponIncreasePointHistoryServiceImplTest {

    @Autowired
    CouponIncreasePointHistoryService couponIncreasePointHistoryService;
    @MockBean
    PointHistoryService pointHistoryService;
    @MockBean
    CouponIncreasePointHistoryRepository couponIncreasePointHistoryRepository;


    CouponIncreasePointHistory couponIncreasePointHistory;

    CouponIssue couponIssue;

    PointHistory pointHistory;

    Member member;

    PointIncreaseDecreaseContentEnum couponPointEnum;

    @BeforeEach
    void setUp() {
        couponPointEnum = PointIncreaseDecreaseContentEnum.COUPON;

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);
        pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setPointHistoryNo(1L);
        pointHistory.setMember(member);

        couponIssue = CouponIssueDummy.getCouponIssue();
        couponIssue.setCouponIssueNo(1L);
        couponIncreasePointHistory =
            new CouponIncreasePointHistory(pointHistory.getPointHistoryNo(), couponIssue);
    }

    @DisplayName("포인트 히스토리를 저장하고 쿠폰히스토리를 저장하는 서비스로직이 정상적으로 동작한다.")
    @Test
    void savePointHistoryAboutCouponIncrease() {

        // given
        given(pointHistoryService.getSavedIncreasePointHistory(any(Member.class), anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(pointHistory);

        given(couponIncreasePointHistoryRepository.save(any(CouponIncreasePointHistory.class)))
            .willReturn(couponIncreasePointHistory);


        // when
        CouponIncreasePointHistory actual =
            couponIncreasePointHistoryService.savePointHistoryAboutCouponIncrease(member,
                couponIssue, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(couponIncreasePointHistory.getPointHistoryNo());
        assertThat(actual.getCouponIssue().getCouponIssueNo())
            .isEqualTo(couponIncreasePointHistory.getCouponIssue().getCouponIssueNo());
    }
}