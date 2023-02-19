package shop.itbook.itbookshop.pointgroup.pointhistory.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.exception.LackOfPointException;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.service.PointIncreaseDecreaseContentService;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(PointHistoryServiceImpl.class)
class PointHistoryServiceImplTest {

    @Autowired
    PointHistoryService pointHistoryService;

    @MockBean
    PointHistoryRepository pointHistoryRepository;

    @MockBean
    PointIncreaseDecreaseContentService pointIncreaseDecreaseContentService;

    @MockBean
    PointHistoryCommonService pointHistoryCommonService;

    PointHistory pointHistory;

    Member member;

    PointIncreaseDecreaseContentEnum couponPointEnum;

    PointIncreaseDecreaseContentEnum orderPointEnum;

    PointIncreaseDecreaseContent orderPointContent;

    @BeforeEach
    void setUp() {

        couponPointEnum = PointIncreaseDecreaseContentEnum.COUPON;
        orderPointEnum = PointIncreaseDecreaseContentEnum.ORDER;

        orderPointContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();
        orderPointContent.setPointIncreaseDecreaseContentNo(1);

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);
        pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setPointHistoryNo(1L);
    }

    @DisplayName("주문적립 포인트내역을 저장할때 수행될 서비스 로직이 잘 수행된다.")
    @Test
    void getSavedIncreasePointHistory() {


        // given

        given(pointHistoryCommonService.findRecentlyPoint(any(Member.class)))
            .willReturn(0L);

        given(
            pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(orderPointContent);

        given(pointHistoryRepository.save(any(PointHistory.class)))
            .willReturn(pointHistory);

        // when
        PointHistory actual =
            pointHistoryService.getSavedIncreasePointHistory(member, 500L, orderPointEnum);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(pointHistory.getPointHistoryNo());
        assertThat(actual.getRemainedPoint())
            .isEqualTo(pointHistory.getRemainedPoint());
        assertThat(actual.getIsDecrease())
            .isEqualTo(false);
    }

    @DisplayName("주문차감 포인트내역을 저장할때 본인 포인트보다 적은 값이 차감되려고 하는 정상적인 상황에서 서비스가 잘 이루어진다.(1000원 포인트중 300원 차감)")
    @Test
    void getSavedDecreasePointHistory_success() {
        // given
        given(pointHistoryCommonService.findRecentlyPoint(any(Member.class)))
            .willReturn(1000L);

        given(
            pointIncreaseDecreaseContentService.findPointIncreaseDecreaseContentThroughContentEnum(
                any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(orderPointContent);

        pointHistory.setRemainedPoint(700L);
        pointHistory.setIsDecrease(true);
        given(pointHistoryRepository.save(any(PointHistory.class)))
            .willReturn(pointHistory);

        // when
        PointHistory actual =
            pointHistoryService.getSavedDecreasePointHistory(member, 300L, orderPointEnum);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(pointHistory.getPointHistoryNo());
        assertThat(actual.getRemainedPoint())
            .isEqualTo(700L);
        assertThat(actual.getIsDecrease())
            .isEqualTo(true);
    }

    
}