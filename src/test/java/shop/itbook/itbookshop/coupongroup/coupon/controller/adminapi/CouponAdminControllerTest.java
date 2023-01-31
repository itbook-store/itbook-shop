package shop.itbook.itbookshop.coupongroup.coupon.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.fileservice.init.TokenInterceptor;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(CouponAdminController.class)
class CouponAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CouponAdminController couponAdminController;

    @MockBean
    TokenInterceptor tokenInterceptor;

    @MockBean
    CouponService couponService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void coupon_add_success_test() throws Exception {
        //given
        CouponRequestDto couponRequestDto = new CouponRequestDto();
        ReflectionTestUtils.setField(couponRequestDto, "name", "쿠폰이름");
        ReflectionTestUtils.setField(couponRequestDto, "amount", 1000L);
        ReflectionTestUtils.setField(couponRequestDto, "percent", 0);
        ReflectionTestUtils.setField(couponRequestDto, "point", 0L);
        ReflectionTestUtils.setField(couponRequestDto, "couponCreatedAt",
            LocalDateTime.now().toString());
        ReflectionTestUtils.setField(couponRequestDto, "couponExpiredAt",
            LocalDateTime.now().toString());
        ReflectionTestUtils.setField(couponRequestDto, "couponModifiedAt",
            LocalDateTime.now().toString());
        ReflectionTestUtils.setField(couponRequestDto, "code", UUID.randomUUID().toString());
        ReflectionTestUtils.setField(couponRequestDto, "isReserved", false);

        given(couponService.addCoupon(any(CouponRequestDto.class))).willReturn(0L);

        //when then
        mvc.perform(post("/api/admin/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo(0)));
    }
}