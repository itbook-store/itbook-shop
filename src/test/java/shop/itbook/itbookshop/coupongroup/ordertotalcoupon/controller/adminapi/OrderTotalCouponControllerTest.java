package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.OrderTotalCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(OrderTotalCouponController.class)
class OrderTotalCouponControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    OrderTotalCouponController orderTotalCouponController;

    @MockBean
    OrderTotalCouponService orderTotalCouponService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void couponAdd_success() throws Exception {
        OrderTotalCouponRequestDto orderTotalCouponRequestDto = new OrderTotalCouponRequestDto();
        long couponNo = 1L;
        given(orderTotalCouponService.addOrderTotalCoupon(any())).willReturn(couponNo);

        mvc.perform(post("/api/admin/order-total-coupons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderTotalCouponRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo((int)couponNo)));
    }

    @Test
    void findCategoryCouponList_success() throws Exception {

        AdminCouponListResponseDto coupon1 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");

        AdminCouponListResponseDto coupon2 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon2, "couponNo", 2L);
        ReflectionTestUtils.setField(coupon2, "name", "쿠폰2");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(orderTotalCouponService.findTotalCouponPageList(any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/order-total-coupons/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("쿠폰2")));

    }
}