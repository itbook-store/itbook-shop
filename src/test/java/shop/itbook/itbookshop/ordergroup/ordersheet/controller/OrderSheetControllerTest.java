package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 정재원
 * @since 1.0
 */
@WebMvcTest(OrderSheetController.class)
class OrderSheetControllerTest {

    @Autowired
    OrderSheetController orderSheetController;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;
    @MockBean
    MemberDestinationService memberDestinationService;
    @MockBean
    PointHistoryCommonService pointHistoryCommonService;
    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("비 회원의 일반 상품 주문서 정보를 잘 받아옵니다.")
    void orderSheetFindSuccess() throws Exception {

//        willDoNothing().given(productService).checkSellProductList(anyList(), anyList());
//
//        mockMvc.perform(get("/api/order-sheet")
//                .queryParam("productNoList", String.valueOf(123L))
//                .queryParam("productCntList", String.valueOf(1))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.result.content[0].deliveryFee", equalTo(0L)));
    }
}