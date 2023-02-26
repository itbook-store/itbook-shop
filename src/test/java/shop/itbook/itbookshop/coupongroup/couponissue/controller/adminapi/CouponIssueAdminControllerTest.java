package shop.itbook.itbookshop.coupongroup.couponissue.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.responseDummy.AdminCouponIssueListResponseDtoDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(CouponIssueAdminController.class)
class CouponIssueAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CouponIssueAdminController couponIssueAdminController;

    @MockBean
    CouponIssueService couponIssueService;

    @Autowired
    ObjectMapper objectMapper;

    private static String COUPON_ISSUE_URL = "/api/admin/coupon-issues";

    @Test
    void allCouponIssueList() throws Exception {

        AdminCouponIssueListResponseDto coupon1 = new AdminCouponIssueListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        AdminCouponIssueListResponseDto coupon2 = AdminCouponIssueListResponseDtoDummy.getDto(5L, 5L, 5L);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(couponIssueService.findAllCouponIssue(any()))
            .willReturn(page);

        mvc.perform(get(COUPON_ISSUE_URL+"/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(5)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("name")));
    }

    @Test
    void searchCouponIssueList() throws Exception {

        AdminCouponIssueListResponseDto coupon1 = new AdminCouponIssueListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        AdminCouponIssueListResponseDto coupon2 = AdminCouponIssueListResponseDtoDummy.getDto(5L, 5L, 5L);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(couponIssueService.findCouponIssueSearch(any(), anyString(), anyString()))
            .willReturn(page);

        mvc.perform(get(COUPON_ISSUE_URL+"/search?searchTarget=검색타겟&keyword=검색어"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(5)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("name")));
    }
}