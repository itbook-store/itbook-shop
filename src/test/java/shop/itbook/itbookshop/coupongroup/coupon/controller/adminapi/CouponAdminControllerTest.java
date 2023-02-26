package shop.itbook.itbookshop.coupongroup.coupon.controller.adminapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
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
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDtoDummy;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.resultmessageenum.CouponIssueResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.coupontype.service.CouponTypeService;

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
    CouponService couponService;

    @Autowired
    ObjectMapper objectMapper;

    CouponRequestDto couponRequestDto;

    private static String COUPON_URL = "/api/admin/coupons";

    @BeforeEach
    void setup(){
        couponRequestDto = new CouponRequestDto();
        ReflectionTestUtils.setField(couponRequestDto, "couponType", "일반쿠폰");
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
        ReflectionTestUtils.setField(couponRequestDto, "isDuplicateUse", false);
        ReflectionTestUtils.setField(couponRequestDto, "totalQuantity", 0);
    }
    @Test
    void coupon_add_success_test() throws Exception {
        //given
        given(couponService.addCoupon(any(CouponRequestDto.class))).willReturn(0L);

        //when then
        mvc.perform(post(COUPON_URL+"/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.couponNo", equalTo(0)));
    }

    @Test
    void couponList() throws Exception {
        List<AdminCouponListResponseDto> couponList =
            List.of(AdminCouponListResponseDtoDummy.getDto(1L), AdminCouponListResponseDtoDummy.getDto(3L));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(couponList, pageRequest, 10);
        given(couponService.findByCouponList(any()))
            .willReturn(page);

        mvc.perform(get(COUPON_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(
                CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage())))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("name")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(3)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("name")));

    }

    @Test
    void couponTypeList() throws Exception {
        List<AdminCouponListResponseDto> couponList =
            List.of(AdminCouponListResponseDtoDummy.getDto(1L), AdminCouponListResponseDtoDummy.getDto(3L));
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(couponList, pageRequest, 10);
        given(couponService.findByCouponAtCouponTypeList(any(), anyString()))
            .willReturn(page);

        mvc.perform(get(COUPON_URL+"/list/couponType"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(
                CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage())))
            .andExpect(jsonPath("$.result.content[0].couponNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo("name")))
            .andExpect(jsonPath("$.result.content[1].couponNo", equalTo(3)))
            .andExpect(jsonPath("$.result.content[1].name", equalTo("name")));

    }

    @Test
    void couponTypeListAll() throws Exception {
            List<AdminCouponListResponseDto> couponList =
                List.of(AdminCouponListResponseDtoDummy.getDto(1L), AdminCouponListResponseDtoDummy.getDto(3L));
            given(couponService.findByAvailableCouponDtoByCouponType(anyString()))
                .willReturn(couponList);

            mvc.perform(get(COUPON_URL+"/list/all/couponType"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.header.resultMessage", equalTo(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage())))
                .andExpect(jsonPath("$.result.[0].couponNo", equalTo(1)))
                .andExpect(jsonPath("$.result.[0].name", equalTo("name")))
                .andExpect(jsonPath("$.result.[1].couponNo", equalTo(3)))
                .andExpect(jsonPath("$.result.[1].name", equalTo("name")));

        }
}