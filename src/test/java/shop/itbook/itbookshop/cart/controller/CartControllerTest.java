package shop.itbook.itbookshop.cart.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.cart.dto.request.CartModifyRequestDto;
import shop.itbook.itbookshop.cart.dto.request.CartRequestDto;
import shop.itbook.itbookshop.cart.dto.response.CartProductDetailsResponseDto;
import shop.itbook.itbookshop.cart.service.CartService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;

/**
 * @author 강명관
 * @since 1.0
 */
@WebMvcTest(controllers = CartController.class)
class CartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CartService cartService;

    @Autowired
    ObjectMapper objectMapper;

    CartRequestDto cartRequestDto;

    @BeforeEach
    void setUp() {
        cartRequestDto = new CartRequestDto();
        ReflectionTestUtils.setField(cartRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(cartRequestDto, "productNo", 1L);

    }

    @DisplayName("장바구니에 상품을 추가하는 성공 테스트")
    @Test
    void productRegisterToCart() throws Exception {

        // given
        given(cartService.registerCart(any())).willReturn(Boolean.TRUE);

        // when, then
        mockMvc.perform(post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequestDto)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isSuccessful", equalTo(true)));
    }

    @DisplayName("장바구니에 상품을 추가 But 실패 Return false")
    @Test
    void productRegisterToCart_alreadyExistCartProduct_thenReturnFalse() throws Exception {

        // given
        given(cartService.registerCart(cartRequestDto)).willReturn(false);

        // when, then
        mockMvc.perform(post("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isSuccessful", equalTo(false)));
    }


    @DisplayName("장바구니 상품 리스트 반환 테스트")
    @Test
    void cartGetProductList() throws Exception {
        // given
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        CartProductDetailsResponseDto cart1 = new CartProductDetailsResponseDto(1, product1);
        CartProductDetailsResponseDto cart2 = new CartProductDetailsResponseDto(1, product2);


        List<CartProductDetailsResponseDto> productList = List.of(cart1, cart2);

        given(cartService.getProductList(any(Long.class))).willReturn(productList);

        Long memberNo = 1L;

        // when, then

        mockMvc.perform(get("/api/cart/{memberNo}", memberNo))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result[0].productCount", equalTo(1)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.productName",
                equalTo("객체지향의 사실과 오해")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.simpleDescription",
                equalTo("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.detailsDescription",
                equalTo("상세 설명")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.isSelled", equalTo(true)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.isForceSoldOut", equalTo(false)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.stock", equalTo(1)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.increasePointPercent", equalTo(1)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.rawPrice", equalTo(12000)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.fixedPrice", equalTo(20000)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.discountPercent", equalTo(10.0)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.fileThumbnailsUrl",
                equalTo("testUrl")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.isbn", equalTo("isbn")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.pageCount", equalTo(10)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.isEbook", equalTo(false)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.fileEbookUrl", equalTo(null)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.publisherName", equalTo("출판사")))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.authorName", equalTo("작가")))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.isPointApplyingBasedSellingPrice",
                    equalTo(false)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.isPointApplying", equalTo(true)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.isSubscription", equalTo(false)))
            .andExpect(jsonPath("$.result[0].productDetailsResponseDto.selledPrice", equalTo(null)))
            .andExpect(
                jsonPath("$.result[0].productDetailsResponseDto.thumbnailsName", equalTo(null)))
            .andExpect(jsonPath("$.result", hasSize(2)));
    }

    @DisplayName("장바구니에 상품을 삭제하는 테스트")
    @Test
    void cartDeleteProduct() throws Exception {

        // when, then
        mockMvc.perform(delete("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @DisplayName("장바구니의 모든 상품을 삭제하는 테스트")
    @Test
    void cartDeleteAllProduct() throws Exception {

        // given
        Long memberNo = 123L;

        // when, then
        mockMvc.perform(delete("/api/cart/{memberNo}", memberNo))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @DisplayName("장바구니의 상품 갯수를 수정하는 테스트")
    @Test
    void cartModifyProductCount() throws Exception {

        // given
        CartModifyRequestDto cartModifyRequestDto = new CartModifyRequestDto(
            cartRequestDto.getMemberNo(),
            cartRequestDto.getProductNo(),
            10
        );

        // when then
        mockMvc.perform(put("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartModifyRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }
}