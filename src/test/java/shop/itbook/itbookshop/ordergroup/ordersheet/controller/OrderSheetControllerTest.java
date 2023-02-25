package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum.OrderSheetMessageEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
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


    ProductDetailsResponseDto productDetailsResponseDto;

    @BeforeEach
    void setUp() {
        productDetailsResponseDto = ProductDetailsResponseDto.builder()
            .productNo(9999L)
            .productName("주문서 테스트")
            .simpleDescription("테스트 중입니다.")
            .detailsDescription("주문서 테스트 중입니다.")
            .isSelled(false)
            .isForceSoldOut(false)
            .stock(99)
            .rawPrice(10000L)
            .fixedPrice(3333L)
            .discountPercent(10.0)
            .fileThumbnailsUrl("test")
            .isbn("1234")
            .pageCount(10)
            .bookCreatedAt(LocalDateTime.now())
            .isEbook(false)
            .fileEbookUrl("test eBook url")
            .publisherName("테스트출판사")
            .authorName("테스트저자")
            .isPointApplyingBasedSellingPrice(true)
            .isPointApplying(true)
            .isSubscription(false)
            .isDeleted(false)
            .dailyHits(1000L)
            .build();

        productDetailsResponseDto.setSelledPrice(900L);
        productDetailsResponseDto.setThumbnailsName("테북");
    }

    @Test
    @DisplayName("비 회원의 주문서 정보를 잘 받아옵니다.")
    void orderSheetFindSuccess() throws Exception {

        // given
        Long testProductNo = productDetailsResponseDto.getProductNo();

        String productNoList = String.valueOf(testProductNo);
        String productCntList = "2";

        willDoNothing().given(productService).checkSellProductList(anyList(), anyList());
        given(productService.findProduct(any())).willReturn(productDetailsResponseDto);

        mockMvc.perform(get("/api/order-sheet")
                .param("productNoList", productNoList)
                .param("productCntList", productCntList)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(
                    OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage())))
            .andExpect(jsonPath("$.result.productDetailsResponseDtoList[0].productNo",
                equalTo(9999)));
    }

    @Test
    @DisplayName("회원의 주문서 정보를 잘 받아옵니다.")
    void orderSheetMemberFindSuccess() throws Exception {

        // given
        Long testProductNo = 9999L;
        String productNoList = String.valueOf(testProductNo);
        String productCntList = "2";
        Long testMemberNo = 999L;

        willDoNothing().given(productService).checkSellProductList(anyList(), anyList());
        given(productService.findProduct(any())).willReturn(productDetailsResponseDto);

        MemberDestinationResponseDto memberDestinationResponseDto =
            MemberDestinationResponseDto.builder()
                .recipientDestinationNo(1L)
                .recipientName("테스트수령인")
                .recipientPhoneNumber("테스트폰번호")
                .postcode(12345)
                .roadNameAddress("테스트도로명주소")
                .recipientAddressDetails("테스트상세주소")
                .build();

        // when member
        List<MemberDestinationResponseDto> memberDestinationResponseDtoList = new ArrayList<>();
        memberDestinationResponseDtoList.add(memberDestinationResponseDto);

        given(memberDestinationService.findMemberDestinationResponseDtoByMemberNo(any()))
            .willReturn(memberDestinationResponseDtoList);
        given(memberService.findMemberByMemberNo(any())).willReturn(MemberDummy.getMember1());
        given(pointHistoryCommonService.findRecentlyPoint(any())).willReturn(4949L);

        mockMvc.perform(get("/api/order-sheet")
                .param("productNoList", productNoList)
                .param("productCntList", productCntList)
                .param("memberNo", String.valueOf(testMemberNo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.memberPoint",
                equalTo(4949)));
    }

    @Test
    @DisplayName("배송비는 기본 3,000원, 상품의 총 판매 가격이 20,000원 이상일 경우 0원")
    void orderSheetDeliveryFee() throws Exception {

        // given
        Long testProductNo = 9999L;
        String productNoList = String.valueOf(testProductNo);
        String productCntList = "2";

        productDetailsResponseDto.setSelledPrice(19999L);

        willDoNothing().given(productService).checkSellProductList(anyList(), anyList());
        given(productService.findProduct(any())).willReturn(productDetailsResponseDto);

        mockMvc.perform(get("/api/order-sheet")
                .param("productNoList", productNoList)
                .param("productCntList", productCntList)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.deliveryFee", equalTo(3000)));

        productDetailsResponseDto.setSelledPrice(20000L);

        mockMvc.perform(get("/api/order-sheet")
                .param("productNoList", productNoList)
                .param("productCntList", productCntList)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.deliveryFee", equalTo(0)));
    }
}