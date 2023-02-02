package shop.itbook.itbookshop.productgroup.product.controller.elastic;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.fileservice.init.TokenInterceptor;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author 송다혜
 * @since 1.0
 */
@WebMvcTest(ProductElasticController.class)
class ProductElasticControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductSearchService productSearchService;
    @MockBean
    private ProductService productService;
    private ProductSearchResponseDto responseDto;


    @BeforeEach
    void setUp() {
        responseDto = new ProductSearchResponseDto(256L, "테테스트북구",
            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
            "상세 설명", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1, 10.0, 12000L, 18000L);
    }

    @Test
    @DisplayName("Get 메서드 성공 테스트")
    void productSearchNameTest_success() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(responseDto, responseDto), pageRequest, 10);

        given(productSearchService.searchProductByTitle(any(), anyString()))
            .willReturn(page);

        mockMvc.perform(get("/api/products/search?name=테스트")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(256)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo(responseDto.getName())))
            .andExpect(jsonPath("$.result.content[0].simpleDescription",
                equalTo(responseDto.getSimpleDescription())))
            .andExpect(jsonPath("$.result.[0].detailsDescription",
                equalTo(responseDto.getDetailsDescription())))
            .andExpect(jsonPath("$.result.[0].stock", equalTo(responseDto.getStock())))
            .andExpect(
                jsonPath("$.result.[0].thumbnailUrl", equalTo(responseDto.getThumbnailUrl())))
            .andExpect(jsonPath("$.result.[0].fixedPrice",
                equalTo(responseDto.getFixedPrice().intValue())))
            .andExpect(jsonPath("$.result.[0].increasePointPercent",
                equalTo(responseDto.getIncreasePointPercent())))
            .andExpect(
                jsonPath("$.result.[0].discountPercent", equalTo(responseDto.getDiscountPercent())))
            .andExpect(
                jsonPath("$.result.[0].rawPrice", equalTo(responseDto.getRawPrice().intValue())))
            .andExpect(jsonPath("$.result.[0].deleted", equalTo(responseDto.isDeleted())));

    }

    @Test
    @DisplayName("Get 메서드 실패 테스트")
    void productSearchNameTest_fail() throws Exception {
        given(productSearchService.searchProductByTitle(anyString())).willThrow(
            new SearchProductNotFoundException());
        mockMvc.perform(get("/api/products/search?name=테스트")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(SearchProductNotFoundException.MESSAGE)));

    }
}