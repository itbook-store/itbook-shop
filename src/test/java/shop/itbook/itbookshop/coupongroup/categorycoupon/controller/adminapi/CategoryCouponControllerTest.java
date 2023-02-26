package shop.itbook.itbookshop.coupongroup.categorycoupon.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.resultmessageenum.CategoryCouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(CategoryCouponController.class)
class CategoryCouponControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CategoryCouponController categoryCouponController;

    @MockBean
    CategoryCouponService categoryCouponService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findCategoryCouponList_success() throws Exception {

        AdminCouponListResponseDto coupon1 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");
        ReflectionTestUtils.setField(coupon1, "categoryNo", 1);
        ReflectionTestUtils.setField(coupon1, "categoryName", "도서");

        AdminCouponListResponseDto coupon2 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon2, "couponNo", 2L);
        ReflectionTestUtils.setField(coupon2, "name", "쿠폰2");
        ReflectionTestUtils.setField(coupon2, "categoryNo", 1);
        ReflectionTestUtils.setField(coupon2, "categoryName", "도서");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(categoryCouponService.findCategoryCouponList(any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/category-coupons/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[0].categoryNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].categoryName", equalTo("도서")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("쿠폰2")))
            .andExpect(jsonPath("$.result.content[1].categoryNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[1].categoryName", equalTo("도서")));

    }

    @Test
    void couponAdd_success() throws Exception {
        CategoryCouponRequestDto categoryCouponRequestDto = new CategoryCouponRequestDto();
        long couponNo = 1L;
        given(categoryCouponService.addCategoryCoupon(any())).willReturn(couponNo);

        mvc.perform(post("/api/admin/category-coupons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryCouponRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo((int)couponNo)));
    }

}