package shop.itbook.itbookshop.coupongroup.membershipcoupon.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.service.MembershipCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(MembershipCouponAdminController.class)
class MembershipCouponAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MembershipCouponAdminController membershipCouponAdminController;

    @MockBean
    MembershipCouponService membershipCouponService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addMembershipCoupon() throws Exception {
        long couponNo = 1L;
        String membershipGrade = "vip";

        given(membershipCouponService.addMembershipCoupon(anyLong(), anyString())).willReturn(1L);

        mvc.perform(post("/api/admin/membership-coupons/1/add?membershipGrade="+membershipGrade)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo((int)couponNo)));
    }
}