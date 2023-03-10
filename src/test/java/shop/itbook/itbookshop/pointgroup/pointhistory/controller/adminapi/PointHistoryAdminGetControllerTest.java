package shop.itbook.itbookshop.pointgroup.pointhistory.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.adminapi.impl.PointHistoryAdminServiceImpl;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * @author ?????????
 * @since 1.0
 */
@WebMvcTest(PointHistoryAdminGetController.class)
class PointHistoryAdminGetControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    PointHistoryAdminGetController pointHistoryAdminGetController;

    @MockBean
    PointHistoryAdminServiceImpl pointHistoryAdminService;

    @Autowired
    ObjectMapper objectMapper;

    PointHistoryListResponseDto orderIncreasePointHistory;
    PointHistoryListResponseDto orderDecreasePointHistory;

    @BeforeEach
    void setUp() {

        orderDecreasePointHistory = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberNo", 1L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberId", "z1?????????");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "memberName", "?????????");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "pointHistoryNo", 1L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "content", "??????");
        ReflectionTestUtils.setField(orderDecreasePointHistory, "isDecrease", false);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "increaseDecreasePoint", 500L);
        ReflectionTestUtils.setField(orderDecreasePointHistory, "remainedPoint", 500L);

        orderIncreasePointHistory = new PointHistoryListResponseDto();
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberNo", 1L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberId", "z1?????????");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "memberName", "?????????");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "pointHistoryNo", 2L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "content", "??????");
        ReflectionTestUtils.setField(orderIncreasePointHistory, "isDecrease", true);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "increaseDecreasePoint", 200L);
        ReflectionTestUtils.setField(orderIncreasePointHistory, "remainedPoint", 300L);

    }


    @Test
    void pointHistoryDetails() throws Exception {


    }

    @DisplayName("url??? ?????? ????????? ??? ???????????? PointHistoryListResponseDto ????????? List??? 200????????? ??? ????????????.")
    @Test
    void pointHistoryList() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page =
            new PageImpl(List.of(orderDecreasePointHistory, orderIncreasePointHistory), pageRequest,
                10);

        given(pointHistoryAdminService.findPointHistoryList(any(),
            any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/point-histories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].memberNo",
                equalTo(orderDecreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[0].pointHistoryNo",
                equalTo(orderDecreasePointHistory.getPointHistoryNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].memberNo",
                equalTo(orderIncreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].pointHistoryNo",
                equalTo(orderIncreasePointHistory.getPointHistoryNo()), Long.class));
    }

    @DisplayName("url??? ?????? ????????? ??? ???????????? PointHissotryListResponseDto ????????? 200????????? ??? ???????????? ??????.")
    @Test
    void pointHistoryListBySearch() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page =
            new PageImpl(List.of(orderDecreasePointHistory, orderIncreasePointHistory), pageRequest,
                10);

        given(pointHistoryAdminService.findPointHistoryListBySearch(any(),
            any(), any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/point-histories/search?searchWord=z1?????????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].memberNo",
                equalTo(orderDecreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[0].pointHistoryNo",
                equalTo(orderDecreasePointHistory.getPointHistoryNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].memberNo",
                equalTo(orderIncreasePointHistory.getMemberNo()), Long.class))
            .andExpect(jsonPath("$.result.content[1].pointHistoryNo",
                equalTo(orderIncreasePointHistory.getPointHistoryNo()), Long.class));
    }

    @DisplayName("???????????????????????? ?????? ???????????? ????????? url??? ?????? ??? ???????????? ???????????? ??? ?????????.")
    @Test
    void pointHistoryCouponDetails() throws Exception {

        PointHistoryCouponDetailsResponseDto pointHistoryCouponDetailsResponseDto =
            new PointHistoryCouponDetailsResponseDto("??????123", "?????????", "???????????????", "??????????????????", null, null,
                null, null, null, null, null);

        given(pointHistoryAdminService.findPointHistoryCouponDetailsDto(anyLong()))
            .willReturn(pointHistoryCouponDetailsResponseDto);

        mvc.perform(get("/api/admin/point-histories/1/coupon-details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.memberId",
                equalTo(pointHistoryCouponDetailsResponseDto.getMemberId())))
            .andExpect(jsonPath("$.result.memberName",
                equalTo(pointHistoryCouponDetailsResponseDto.getMemberName())))
            .andExpect(jsonPath("$.result.couponName",
                equalTo(pointHistoryCouponDetailsResponseDto.getCouponName())))
            .andExpect(jsonPath("$.result.couponType",
                equalTo(pointHistoryCouponDetailsResponseDto.getCouponType())));
    }

    @DisplayName("???????????????????????? ?????? ???????????? ????????? url??? ?????? ??? ???????????? ???????????? ??? ?????????.")
    @Test
    void pointHistoryReviewDetails() throws Exception {
        ReviewResponseDto reviewResponseDto = ReviewResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .productName("??????????????? ??????")
            .memberNo(1L)
            .starPoint(3)
            .content("???????????????.")
            .image("testUrl")
            .build();

        given(pointHistoryAdminService.findReviewResponseDtoForPointHistoryReviewDetails(anyLong()))
            .willReturn(reviewResponseDto);

        mvc.perform(get("/api/admin/point-histories/1/review-details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.orderProductNo",
                equalTo(reviewResponseDto.getOrderProductNo()), Long.class))
            .andExpect(jsonPath("$.result.productNo",
                equalTo(reviewResponseDto.getProductNo()), Long.class))
            .andExpect(jsonPath("$.result.productName",
                equalTo(reviewResponseDto.getProductName())))
            .andExpect(jsonPath("$.result.starPoint",
                equalTo(reviewResponseDto.getStarPoint())));

    }

    @DisplayName("???????????????????????? ?????? ???????????? ????????? url??? ?????? ??? ???????????? ???????????? ??? ?????????.")
    @Test
    void pointHistoryGiftDetails() throws Exception {

        PointHistoryGiftDetailsResponseDto pointHistoryGiftDetailsResponseDto =
            new PointHistoryGiftDetailsResponseDto("??????123", "?????????",
                "sub123", null, null, null,
                true);

        given(pointHistoryAdminService.findPointHistoryGiftDetailsDto(anyLong()))
            .willReturn(pointHistoryGiftDetailsResponseDto);

        mvc.perform(get("/api/admin/point-histories/1/gift-details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.mainMemberId",
                equalTo(pointHistoryGiftDetailsResponseDto.getMainMemberId())))
            .andExpect(jsonPath("$.result.mainMemberName",
                equalTo(pointHistoryGiftDetailsResponseDto.getMainMemberName())))
            .andExpect(jsonPath("$.result.subMemberId",
                equalTo(pointHistoryGiftDetailsResponseDto.getSubMemberId())))
            .andExpect(jsonPath("$.result.isDecrease",
                equalTo(pointHistoryGiftDetailsResponseDto.getIsDecrease())));
    }

    @DisplayName("???????????????????????? ?????? ???????????? ????????? url??? ?????? ??? ???????????? ???????????? ??? ?????????.")
    @Test
    void pointHistoryGradeDetails() throws Exception {

        PointHistoryGradeDetailsResponseDto pointHistoryGradeDetailsResponseDto =
            new PointHistoryGradeDetailsResponseDto("??????123", "?????????", 1, "??????", null, null,
                null, null, null, null);

        given(pointHistoryAdminService.findPointHistoryGradeDetailsDto(anyLong()))
            .willReturn(pointHistoryGradeDetailsResponseDto);

        mvc.perform(get("/api/admin/point-histories/1/grade-details")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.memberId",
                equalTo(pointHistoryGradeDetailsResponseDto.getMemberId())))
            .andExpect(jsonPath("$.result.memberName",
                equalTo(pointHistoryGradeDetailsResponseDto.getMemberName())))
            .andExpect(jsonPath("$.result.membershipNo",
                equalTo(pointHistoryGradeDetailsResponseDto.getMembershipNo())))
            .andExpect(jsonPath("$.result.membershipGrade",
                equalTo(pointHistoryGradeDetailsResponseDto.getMembershipGrade())));
    }
    
}