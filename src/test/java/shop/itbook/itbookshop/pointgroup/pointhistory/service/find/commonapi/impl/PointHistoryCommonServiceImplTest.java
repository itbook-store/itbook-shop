package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
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
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(PointHistoryCommonServiceImpl.class)
class PointHistoryCommonServiceImplTest {

    @Autowired
    PointHistoryCommonService pointHistoryCommonService;

    @MockBean
    PointHistoryRepository pointHistoryRepository;

    PointHistory pointHistory;

    Member member;


    @BeforeEach
    void setUp() {

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);
        pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setPointHistoryNo(1L);
    }

    @DisplayName("가장 최근에 있는 포인트 히스토리를 가져오는 서비스 로직이 정상 동작한다.")
    @Test
    void findRecentlyPointHistory() {

        // given
        given(pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(any()))
            .willReturn(Optional.of(pointHistory));

        // when
        Optional<PointHistory> actual =
            pointHistoryCommonService.findRecentlyPointHistory(member);

        // then
        assertThat(actual.isPresent())
            .isTrue();
        assertThat(actual.get().getPointHistoryNo())
            .isEqualTo(pointHistory.getPointHistoryNo());
        assertThat(actual.get().getRemainedPoint())
            .isEqualTo(pointHistory.getRemainedPoint());
    }

    @DisplayName("가장 최근의 포인트내역이 있는경우에 잔여포인트를 가져오는 서비스 로직이 잘 동작한다.")
    @Test
    void findRecentlyPoint_nonnull() {
        // given
        given(pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(any()))
            .willReturn(Optional.of(pointHistory));

        // when
        Long actual = pointHistoryCommonService.findRecentlyPoint(member);

        // then
        assertThat(actual)
            .isEqualTo(pointHistory.getRemainedPoint());
    }

    @DisplayName("가장 최근의 포인트내역이 없는경우에 잔여포인트 0L을 가져오는 서비스 로직이 잘 동작한다.")
    @Test
    void findRecentlyPoint_null() {
        // given
        given(pointHistoryRepository.findFirstByMemberOrderByPointHistoryNoDesc(any()))
            .willReturn(Optional.ofNullable(null));

        // when
        Long actual = pointHistoryCommonService.findRecentlyPoint(member);

        // then
        assertThat(actual)
            .isEqualTo(0L);
    }
}