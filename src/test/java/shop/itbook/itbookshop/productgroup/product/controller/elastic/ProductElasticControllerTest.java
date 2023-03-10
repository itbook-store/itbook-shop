package shop.itbook.itbookshop.productgroup.product.controller.elastic;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductSearchResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author ?????????
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

    private static String SEARCH_URL = "/api/products/search";
    @BeforeEach
    void setUp() {
        responseDto = new ProductSearchResponseDto(256L, "??????????????????",
            "?????????????????? ????????????? ??? ?????? ??? ????????? ?????? ?????? ?????? ?????? ???????????? ?????? ?????? ???????????? ?????? ?????????.",
            "?????? ??????", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1.0, 10.0, 12000L, 18000L);
    }

    @Test
    @DisplayName("Get ????????? ?????? ?????????")
    void productSearchNameTest_success() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(responseDto, responseDto), pageRequest, 10);

        given(productSearchService.searchProductByTitle(any(), anyString()))
            .willReturn(page);

        mockMvc.perform(get(SEARCH_URL+"?name=?????????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(256)))
            .andExpect(jsonPath("$.result.content[0].name", equalTo(responseDto.getName())))
            .andExpect(jsonPath("$.result.content[0].simpleDescription",
                equalTo(responseDto.getSimpleDescription())))
            .andExpect(jsonPath("$.result.content[0].detailsDescription",
                equalTo(responseDto.getDetailsDescription())))
            .andExpect(jsonPath("$.result.content[0].stock", equalTo(responseDto.getStock())))
            .andExpect(
                jsonPath("$.result.content[0].thumbnailUrl",
                    equalTo(responseDto.getThumbnailUrl())))
            .andExpect(jsonPath("$.result.content[0].fixedPrice",
                equalTo(responseDto.getFixedPrice().intValue())))
            .andExpect(jsonPath("$.result.content[0].increasePointPercent",
                equalTo(responseDto.getIncreasePointPercent())))
            .andExpect(
                jsonPath("$.result.content[0].discountPercent",
                    equalTo(responseDto.getDiscountPercent())))
            .andExpect(
                jsonPath("$.result.content[0].rawPrice",
                    equalTo(responseDto.getRawPrice().intValue())))
            .andExpect(jsonPath("$.result.content[0].forceSoldOut",
                equalTo(responseDto.isForceSoldOut())));

    }

    @Test
    @DisplayName("Get ????????? ?????? ?????????")
    void productSearchNameTest_fail() throws Exception {
        given(productSearchService.searchProductByTitle(any(), anyString())).willThrow(
            new SearchProductNotFoundException());
        mockMvc.perform(get(SEARCH_URL+"?name=?????????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(SearchProductNotFoundException.MESSAGE)));

    }

    @Test
    void getProductSearchByName() throws Exception {
        List<ProductSearchResponseDto> searchList = List.of(responseDto, responseDto);

        given(productSearchService.searchProductsByTitle(anyString()))
            .willReturn(searchList);

        mockMvc.perform(get(SEARCH_URL+"/list?name=?????????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.[0].productNo", equalTo(256)))
            .andExpect(jsonPath("$.result.[0].name", equalTo(responseDto.getName())));

    }

    @Test
    void productRemove() throws Exception {

        mockMvc.perform(delete(SEARCH_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(ProductResultMessageEnum.DELETE_SUCCESS.getMessage())));

    }


    @Test
    void productAdd() throws Exception {
        Long productNo = 1L;
        Product product = ProductDummy.getProductSuccess();
        given(productService.findProductEntity(anyLong())).willReturn(product);
        given(productSearchService.addSearchProduct(any())).willReturn(productNo);
        mockMvc.perform(post(SEARCH_URL+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo(ProductResultMessageEnum.ADD_SUCCESS.getMessage())))
            .andExpect(jsonPath("$.result.productNo", equalTo(productNo.intValue())));

    }
}