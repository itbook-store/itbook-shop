package shop.itbook.itbookshop.coupongroup.couponissue.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDtoDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDtoDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.responseDummy.AdminCouponIssueListResponseDtoDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.resultmessageenum.CouponIssueResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;

/**
 *
 *
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(CouponIssueServiceController.class)
class CouponIssueServiceControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CouponIssueServiceController couponIssueServiceController;

    @MockBean
    CouponIssueService couponIssueService;

    @Autowired
    ObjectMapper objectMapper;

    private static String COUPON_ISSUE_URL = "/api/coupon-issues";
    @Test
    void userCouponIssueList() throws Exception {

        UserCouponIssueListResponseDto coupon1 = new UserCouponIssueListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponIssueNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        UserCouponIssueListResponseDto coupon2 = UserCouponIssueListResponseDtoDummy.getDto(5L);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(couponIssueService.findCouponIssueListByMemberNo(any(), anyLong(), anyString()))
            .willReturn(page);

        mvc.perform(get(COUPON_ISSUE_URL+"/1?usageStatus=test"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(
                CouponIssueResultMessageEnum.COUPON_ISSUE_LIST_SUCCESS_MESSAGE.getSuccessMessage())))
            .andExpect(jsonPath("$.result.content[0].couponIssueNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[1].couponIssueNo", equalTo(5)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("name")));

    }

    @Test
    void addCouponIssue() throws Exception {
        long couponNo = 1L;
        given(couponIssueService.addCouponIssueByCoupon(anyLong(), anyLong())).willReturn(couponNo);

        mvc.perform(post(COUPON_ISSUE_URL+"/1/1/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponIssueNo", equalTo((int)couponNo)));

    }

    @Test
    void useUserPointCoupon() throws Exception {
        long couponNo = 1L;

        mvc.perform(put(COUPON_ISSUE_URL+"/1/point-coupon-use")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(
                CouponIssueResultMessageEnum.POINT_COUPON_USE_SUCCESS_MESSAGE.getSuccessMessage())));
    }

    @Test
    void findAvailableProductCategoryCouponByMemberNoAndProductNo() throws Exception {

        OrderCouponSimpleListResponseDto coupon1 = new OrderCouponSimpleListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponIssueNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        OrderCouponSimpleListResponseDto coupon2 = OrderCouponSimpleListResponseDtoDummy.getDto(5L, 2L);

        List<OrderCouponSimpleListResponseDto> result = List.of(coupon1, coupon2);
        given(couponIssueService.findAvailableProductCategoryCouponByMemberNoAndProductNo(anyLong(), anyLong()))
            .willReturn(result);

        mvc.perform(get(COUPON_ISSUE_URL+"/1/order/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.[0].couponIssueNo", equalTo(1)))
            .andExpect(jsonPath("$.result.[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.[1].couponIssueNo", equalTo(5)))
            .andExpect(jsonPath("$.result.[1].name", equalTo("name")));

    }

    @Test
    void findAvailableProductCategoryCouponByMemberNo() throws Exception {

        OrderCouponSimpleListResponseDto coupon1 = new OrderCouponSimpleListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponIssueNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        OrderCouponSimpleListResponseDto coupon2 = OrderCouponSimpleListResponseDtoDummy.getDto(5L, 2L);

        List<OrderCouponSimpleListResponseDto> result = List.of(coupon1, coupon2);
        given(couponIssueService.findAvailableOrderTotalCouponByMemberNo(anyLong()))
            .willReturn(result);

        mvc.perform(get(COUPON_ISSUE_URL+"/1/order/total"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.[0].couponIssueNo", equalTo(1)))
            .andExpect(jsonPath("$.result.[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.[1].couponIssueNo", equalTo(5)))
            .andExpect(jsonPath("$.result.[1].name", equalTo("name")));
    }
}