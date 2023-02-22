package shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.PointHistoryAdminService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(PointHistoryAdminServiceImpl.class)
class PointHistoryAdminServiceImplTest {

    @Autowired
    PointHistoryAdminService pointHistoryAdminService;

    @MockBean
    PointHistoryRepository pointHistoryRepository;

    @MockBean
    OrderService orderService;
    PointIncreaseDecreaseContentEnum couponPointEnum;

    PointIncreaseDecreaseContentEnum orderPointEnum;

    PointHistoryListResponseDto pointHistoryListResponseDtoOfCouponIncrease;

    PointHistoryListResponseDto pointHistoryListResponseDtoOfOrderDecrease;

    Member member;

    @BeforeEach
    void setUp() {

        couponPointEnum = PointIncreaseDecreaseContentEnum.COUPON;
        orderPointEnum = PointIncreaseDecreaseContentEnum.ORDER;

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);

        pointHistoryListResponseDtoOfCouponIncrease = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "memberNo",
            member.getMemberNo());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "memberId",
            member.getMemberId());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "memberName",
            member.getName());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "pointHistoryNo",
            1L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "isDecrease",
            false);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease,
            "increaseDecreasePoint", 1000L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "content",
            couponPointEnum.getContent());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease, "remainedPoint",
            1000L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfCouponIncrease,
            "historyCreatedAt",
            LocalDateTime.now());

        pointHistoryListResponseDtoOfOrderDecrease = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "memberNo",
            member.getMemberNo());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "memberId",
            member.getMemberId());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "memberName",
            member.getName());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "pointHistoryNo",
            2L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "isDecrease",
            true);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease,
            "increaseDecreasePoint", 300L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "content",
            orderPointEnum.getContent());
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "remainedPoint",
            700L);
        ReflectionTestUtils.setField(pointHistoryListResponseDtoOfOrderDecrease, "historyCreatedAt",
            LocalDateTime.now());

    }

    @DisplayName("쿠폰적립, 주문차감 포인트 내역을 조회에 대한 서비스 로직이 잘 동작한다.")
    @Test
    void findPointHistoryList() {

        PageRequest pageable = PageRequest.of(0, 10);

        // given
        given(pointHistoryRepository.findPointHistoryListResponseDto(pageable, couponPointEnum))
            .willReturn(new PageImpl(List.of(pointHistoryListResponseDtoOfOrderDecrease,
                pointHistoryListResponseDtoOfCouponIncrease), pageable, 0));

        // when
        Page<PointHistoryListResponseDto> page =
            pointHistoryAdminService.findPointHistoryList(pageable, couponPointEnum);

        List<PointHistoryListResponseDto> pointList = page.getContent();

        // then
        assertThat(pointList.get(0).getPointHistoryNo())
            .isEqualTo(pointHistoryListResponseDtoOfOrderDecrease.getPointHistoryNo());
        assertThat(pointList.get(0).getMemberId())
            .isEqualTo(pointHistoryListResponseDtoOfOrderDecrease.getMemberId());
        assertThat(pointList.get(0).getContent())
            .isEqualTo(orderPointEnum.getContent());

        assertThat(pointList.get(1).getPointHistoryNo())
            .isEqualTo(pointHistoryListResponseDtoOfCouponIncrease.getPointHistoryNo());
        assertThat(pointList.get(1).getMemberId())
            .isEqualTo(pointHistoryListResponseDtoOfCouponIncrease.getMemberId());
        assertThat(pointList.get(1).getContent())
            .isEqualTo(couponPointEnum.getContent());
    }

    @DisplayName("쿠폰적립에 대한 서비스 로직이 잘 동작한다.")
    @Test
    void findPointHistoryListBySearch() {
        PageRequest pageable = PageRequest.of(0, 10);

        // given
        given(pointHistoryRepository.findPointHistoryListResponseDtoThroughSearch(pageable,
            couponPointEnum, member.getMemberId()))
            .willReturn(
                new PageImpl(List.of(pointHistoryListResponseDtoOfCouponIncrease), pageable, 0));

        // when
        Page<PointHistoryListResponseDto> page =
            pointHistoryAdminService.findPointHistoryListBySearch(pageable, couponPointEnum,
                member.getMemberId());

        List<PointHistoryListResponseDto> pointList = page.getContent();

        // then
        assertThat(pointList.get(0).getPointHistoryNo())
            .isEqualTo(pointHistoryListResponseDtoOfCouponIncrease.getPointHistoryNo());
        assertThat(pointList.get(0).getMemberId())
            .isEqualTo(pointHistoryListResponseDtoOfCouponIncrease.getMemberId());
        assertThat(pointList.get(0).getContent())
            .isEqualTo(couponPointEnum.getContent());
    }
}