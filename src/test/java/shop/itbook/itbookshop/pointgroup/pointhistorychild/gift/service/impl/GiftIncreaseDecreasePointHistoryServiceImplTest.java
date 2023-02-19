package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.exception.LackOfPointException;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.repository.GiftIncreaseDecreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.GiftIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(GiftIncreaseDecreasePointHistoryServiceImpl.class)
class GiftIncreaseDecreasePointHistoryServiceImplTest {

    @Autowired
    GiftIncreaseDecreasePointHistoryService giftIncreaseDecreasePointHistoryService;
    @MockBean
    PointHistoryService pointHistoryService;
    @MockBean
    GiftIncreaseDecreasePointHistoryRepository giftIncreaseDecreasePointHistoryRepository;

    @MockBean
    PointHistoryCommonService pointHistoryCommonService;

    GiftIncreaseDecreasePointHistory giftIncreaseDecreasePointHistory;

    PointHistory increasePointHistory;
    PointHistory decreasePointHistory;

    Member sender;
    Member receiver;

    PointIncreaseDecreaseContentEnum orderCancelPointEnum;

    @BeforeEach
    void setUp() {
        orderCancelPointEnum = PointIncreaseDecreaseContentEnum.GIFT;

        sender = MemberDummy.getMember1();
        sender.setMemberNo(1L);

        receiver = MemberDummy.getMember2();
        receiver.setMemberNo(2L);

        increasePointHistory = PointHistoryDummy.getPointHistory();
        increasePointHistory.setPointHistoryNo(10L);
        increasePointHistory.setMember(sender);
        increasePointHistory.setIsDecrease(true);

        decreasePointHistory = PointHistoryDummy.getPointHistory();
        decreasePointHistory.setPointHistoryNo(2L);
        decreasePointHistory.setMember(receiver);
        decreasePointHistory.setIsDecrease(false);

        giftIncreaseDecreasePointHistory =
            new GiftIncreaseDecreasePointHistory(increasePointHistory.getPointHistoryNo(),
                receiver);
    }

    @DisplayName("포인트 선물에 대한 서비스 로직이 정상작동한다.")
    @Test
    void savePointHistoryAboutGiftDecreaseAndIncrease() {

        // given
        given(pointHistoryService.getSavedDecreasePointHistory(any(Member.class),
            anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(increasePointHistory);

        given(pointHistoryService.getSavedIncreasePointHistory(any(Member.class),
            anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(decreasePointHistory);

        given(giftIncreaseDecreasePointHistoryRepository.save(
            any(GiftIncreaseDecreasePointHistory.class)))
            .willReturn(giftIncreaseDecreasePointHistory);

        given(pointHistoryCommonService.findRecentlyPoint(any(Member.class)))
            .willReturn(200L);

        // when
        GiftIncreaseDecreasePointHistory actual =
            giftIncreaseDecreasePointHistoryService.savePointHistoryAboutGiftDecreaseAndIncrease(
                sender, receiver, 100L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(giftIncreaseDecreasePointHistory.getPointHistoryNo());
        assertThat(actual.getMember().getMemberNo())
            .isEqualTo(giftIncreaseDecreasePointHistory.getMember().getMemberNo());

    }


    @DisplayName("선물을 보내려는 사람에 대한 포인트내역을 저장할때 본인 포인트보다 큰 값이 차감되려고 하는 정상적인 상황에서 LackOfPointException 이 잘 발생한다.(100원 포인트남은 상태에서 100000000원 차감 시도)")
    @Test
    void getSavedDecreasePointHistory_fail_LackOfPointException() {

        // given
        given(pointHistoryCommonService.findRecentlyPoint(any(Member.class)))
            .willReturn(100L);

        // when then
        assertThatExceptionOfType(LackOfPointException.class).isThrownBy(
                () -> giftIncreaseDecreasePointHistoryService.savePointHistoryAboutGiftDecreaseAndIncrease(
                    sender, receiver, 100000000L))
            .withMessageContaining(LackOfPointException.MESSAGE);
    }
}