package shop.itbook.itbookshop.coupongroup.productcoupon.controller;

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
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(ProductCouponAdminController.class)
class ProductCouponAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductCouponAdminController productCouponAdminController;

    @MockBean
    ProductCouponService productCouponService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void couponAdd_success() throws Exception {
        long couponNo = 1L;
        ProductCouponRequestDto productCouponRequestDto = new ProductCouponRequestDto(1L, new CouponRequestDto());
        given(productCouponService.addProductCoupon(any())).willReturn(couponNo);

        mvc.perform(post("/api/admin/product-coupons/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCouponRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo((int)couponNo)));
    }

    @Test
    void findProductCouponList_success() throws Exception {

        AdminCouponListResponseDto coupon1 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon1, "couponNo", 1L);
        ReflectionTestUtils.setField(coupon1, "name", "쿠폰");
        ReflectionTestUtils.setField(coupon1, "productNo", 2L);
        ReflectionTestUtils.setField(coupon1, "productName", "상품");

        AdminCouponListResponseDto coupon2 = new AdminCouponListResponseDto();
        ReflectionTestUtils.setField(coupon2, "couponNo", 2L);
        ReflectionTestUtils.setField(coupon2, "name", "쿠폰2");
        ReflectionTestUtils.setField(coupon2, "productNo", 1L);
        ReflectionTestUtils.setField(coupon2, "productName", "도서");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(coupon1, coupon2), pageRequest, 10);
        given(productCouponService.findProductCouponPageList(any()))
            .willReturn(page);

        mvc.perform(get("/api/admin/product-coupons/list"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("쿠폰")))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[0].productName", equalTo("상품")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("쿠폰2")))
            .andExpect(jsonPath("$.result.content[1].productNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[1].productName", equalTo("도서")));

    }

}