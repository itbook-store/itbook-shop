package shop.itbook.itbookshop.productgroup.productcategory.controller.service;

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
import shop.itbook.itbookshop.category.controller.adminapi.CategoryAdminController;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductCategoryController.class)
class ProductCategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ProductCategoryController productCategoryController;

    @MockBean
    ProductCategoryService productCategoryService;

    @Test
    void productListTest() throws Exception {

        ProductDetailsResponseDto product1 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product1, "productNo", 1L);
        ReflectionTestUtils.setField(product1, "productName", "상품1");

        ProductDetailsResponseDto product2 = new ProductDetailsResponseDto();
        ReflectionTestUtils.setField(product2, "productNo", 2L);
        ReflectionTestUtils.setField(product2, "productName", "상품2");

        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);
        given(productCategoryService.findProductList(any(Pageable.class), anyInt()))
            .willReturn(page);


        mvc.perform(get("/api/products?categoryNo=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].productNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].productName", equalTo("상품1")))
            .andExpect(jsonPath("$.result.content[1].productNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[1].productName", equalTo("상품2")));
    }

    @Test
    void testProductListTest() throws Exception {
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "도서");

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "잡화");

        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(category1, category2), pageable, 10);
        given(productCategoryService.findCategoryList(any(Pageable.class), anyLong()))
            .willReturn(page);


        mvc.perform(get("/api/products/categories?productNo=1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.content[0].categoryNo", equalTo(1)))
            .andExpect(jsonPath("$.result.content[0].categoryName", equalTo("도서")))
            .andExpect(jsonPath("$.result.content[1].categoryNo", equalTo(2)))
            .andExpect(jsonPath("$.result.content[1].categoryName", equalTo("잡화")));
    }
}