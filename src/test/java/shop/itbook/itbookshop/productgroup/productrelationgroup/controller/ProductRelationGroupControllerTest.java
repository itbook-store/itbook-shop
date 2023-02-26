package shop.itbook.itbookshop.productgroup.productrelationgroup.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productcategory.controller.service.ProductCategoryController;
import shop.itbook.itbookshop.productgroup.productrelationgroup.service.ProductRelationGroupService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductRelationGroupController.class)
class ProductRelationGroupControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductRelationGroupController productRelationGroupController;
    @MockBean
    ProductRelationGroupService productRelationGroupService;

    @Test
    void productListFilteredByBasedProductNoForUsers() throws Exception {
        ProductDetailsResponseDto product1 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product1, "productNo", 1L);
        ReflectionTestUtils.setField(product1, "productName", "상품1");

        ProductDetailsResponseDto product2 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product2, "productNo", 2L);
        ReflectionTestUtils.setField(product2, "productName", "상품2");

        ProductDetailsResponseDto product3 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product3, "productNo", 3L);
        ReflectionTestUtils.setField(product3, "productName", "상품3");

        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product2, product3), pageable, 10);
        given(
            productRelationGroupService.findProductRelationForUser(any(Pageable.class), anyLong()))
            .willReturn(page);


        mvc.perform(get("/api/products/relation/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[0].productName", equalTo("상품2")))
            .andExpect(jsonPath("$.result.content[1].productNo", equalTo(3)))
            .andExpect(jsonPath("$.result.content[1].productName", equalTo("상품3")));
    }

    @Test
    void productListFilteredByBasedProductNoForAdmin() throws Exception {
        ProductDetailsResponseDto product1 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product1, "productNo", 1L);
        ReflectionTestUtils.setField(product1, "productName", "상품1");

        ProductDetailsResponseDto product2 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product2, "productNo", 2L);
        ReflectionTestUtils.setField(product2, "productName", "상품2");

        ProductDetailsResponseDto product3 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product3, "productNo", 3L);
        ReflectionTestUtils.setField(product3, "productName", "상품3");

        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product2, product3), pageable, 10);
        given(
            productRelationGroupService.findProductRelationForAdmin(any(Pageable.class), anyLong()))
            .willReturn(page);


        mvc.perform(get("/api/admin/products/relation/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[0].productName", equalTo("상품2")))
            .andExpect(jsonPath("$.result.content[1].productNo", equalTo(3)))
            .andExpect(jsonPath("$.result.content[1].productName", equalTo("상품3")));
    }

    @Test
    void allRelationProductListForAdmin() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(
            productRelationGroupService.findProductRelationForAdmin(any(Pageable.class), anyLong()))
            .willReturn(page);

        mvc.perform(get("/api/admin/products/relation/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    void productListExceptBasedProduct() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(
            productRelationGroupService.findAllMainProductRelationForAdmin(any(Pageable.class)))
            .willReturn(page);

        mvc.perform(get("/api/admin/products/relation"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    void testProductListExceptBasedProduct() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(
            productRelationGroupService.findProductExceptBasedProductForAdmin(any(Pageable.class),
                anyLong()))
            .willReturn(page);

        mvc.perform(get("/api/admin/products/relation/add-candidates/2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }
}