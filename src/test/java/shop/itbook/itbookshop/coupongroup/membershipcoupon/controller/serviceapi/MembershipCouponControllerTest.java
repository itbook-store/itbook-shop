package shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.ServiceCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.adminapi.MembershipCouponAdminController;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(MembershipCouponController.class)
class MembershipCouponControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MembershipCouponController membershipCouponController;

    @MockBean
    MembershipCouponService membershipCouponService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findMembershipCoupon() throws Exception {
        ServiceCouponListResponseDto coupon1 = new ServiceCouponListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);

        ServiceCouponListResponseDto coupon2 = new ServiceCouponListResponseDto();
        ReflectionTestUtils.setField(coupon2, "couponNo", 2L);
        String membershipGrade1 = "test";
        String membershipGrade2 = "vip";
        MembershipCouponResponseDto membershipCoupon1 = new MembershipCouponResponseDto(membershipGrade1, coupon1);
        MembershipCouponResponseDto membershipCoupon2 = new MembershipCouponResponseDto(membershipGrade1, coupon2);

        Map<String, List<MembershipCouponResponseDto>> membershipCouponMap = new HashMap<>();
        membershipCouponMap.put(membershipGrade1, List.of(membershipCoupon1, membershipCoupon2));
        membershipCouponMap.put(membershipGrade2, List.of(membershipCoupon1, membershipCoupon2));

        given(membershipCouponService.findAvailableMembershipCouponList()).willReturn(membershipCouponMap);

        mvc.perform(get("/api/membership-coupons/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo("등급 쿠폰 목록을 불러오는데 성공했습니다.")))
            .andExpect(jsonPath("$.result.test.[0].coupon.couponNo", equalTo((coupon1.getCouponNo().intValue()))))
            .andExpect(jsonPath("$.result.test.[1].coupon.couponNo", equalTo((coupon2.getCouponNo().intValue()))))
            .andExpect(jsonPath("$.result.vip.[0].coupon.couponNo", equalTo((coupon1.getCouponNo().intValue()))))
            .andExpect(jsonPath("$.result.vip.[1].coupon.couponNo", equalTo((coupon2.getCouponNo().intValue()))));
    }

    @Test
    void addMembershipCouponIssue() throws Exception {
        long couponIssueNo = 123L;

        given(membershipCouponService.membershipCouponDownload(anyLong(), anyLong())).willReturn(
            couponIssueNo);

        mvc.perform(post("/api/membership-coupons/1/1/add")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponIssueNo", equalTo((int) couponIssueNo)));
    }
}