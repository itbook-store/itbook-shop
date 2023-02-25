package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.models.auth.In;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum.OrderSheetMessageEnum;
import shop.itbook.itbookshop.ordergroup.ordersheet.transfer.OrderSheetTransfer;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 정재원
 * @author 이하늬
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
    ProductService mockProductService;
    @MockBean
    MemberDestinationService memberDestinationService;
    @MockBean
    PointHistoryCommonService pointHistoryCommonService;
    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("비회원의 일반 상품 주문서 정보를 잘 받아옵니다.")
    void orderSheetForNonMemberFindSuccess() throws Exception {
        ProductDetailsResponseDto product = ProductDummy.getProductDetailsResponseDto();
        product.setSelledPrice(10000L);

        List<Long> productNoList = List.of(1L, 2L, 3L);
        List<Integer> productCntList = List.of(1, 2, 3);

        given(mockProductService.findProduct(anyLong())).willReturn(product);

        mockProductService.checkSellProductList(productNoList, productCntList);
        mockMvc.perform(get("/api/order-sheet?productNoList=1,2,3&productCntList=1,2,3"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage())));
    }

    @Test
    @DisplayName("회원의 일반 상품 주문서 정보를 잘 받아옵니다.")
    void orderSheetForMemberFindSuccess() throws Exception {
        ProductDetailsResponseDto product = ProductDummy.getProductDetailsResponseDto();
        product.setSelledPrice(10000L);

        List<Long> productNoList = List.of(1L, 2L, 3L);
        List<Integer> productCntList = List.of(1, 2, 3);

        given(mockProductService.findProduct(anyLong())).willReturn(product);

        mockProductService.checkSellProductList(productNoList, productCntList);
        mockMvc.perform(get("/api/order-sheet?productNoList=1,2,3&productCntList=1,2,3&memberNo=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(
                        OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage())));
    }
}