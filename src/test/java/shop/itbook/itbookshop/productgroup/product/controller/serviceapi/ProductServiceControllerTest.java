package shop.itbook.itbookshop.productgroup.product.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.service.salesstatus.ProductSalesStatusService;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductServiceController.class)
class ProductServiceControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService mockProductService;

    @MockBean
    ProductTypeService mockProductTypeService;

    @Test
    @DisplayName("사용자 상품 조회 성공 테스트")
    void productList() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(mockProductService.findProductListForUser(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 번호로 해당되는 상품들에 대해 상세정보 조회 성공 테스트")
    void testProductList() throws Exception {
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(
            mockProductService.findProductListByProductNoListForUser(any(Pageable.class),
                anyList()))
            .willReturn(page);

        mockMvc.perform(get("/api/products/1,2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("모든 상품 유형 조회 성공 테스트")
    void productTypeList() throws Exception {
        ProductTypeResponseDto productType1 = new ProductTypeResponseDto(1, "유형1");
        ProductTypeResponseDto productType2 = new ProductTypeResponseDto(2, "유형2");
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(productType1, productType2), pageable, 10);

        given(
            mockProductTypeService.findProductTypeList(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/api/products/product-types"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }
}