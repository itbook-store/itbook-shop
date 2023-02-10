package shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
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
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
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
        increasePointHistory.setPointHistoryNo(1L);
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


        // when
        GiftIncreaseDecreasePointHistory actual =
            giftIncreaseDecreasePointHistoryService.savePointHistoryAboutGiftDecreaseAndIncrease(
                sender, receiver, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(giftIncreaseDecreasePointHistory.getPointHistoryNo());
        assertThat(actual.getMember().getMemberNo())
            .isEqualTo(giftIncreaseDecreasePointHistory.getMember().getMemberNo());

    }
}