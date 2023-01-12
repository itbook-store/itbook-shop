package shop.itbook.itbookshop.productgroup.product.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductAdminController.class)
class ProductAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ProductAdminService productService;
    private AddProductRequestDto addProductRequestDto_success;
    private AddProductRequestDto addProductRequestDto_failure;


    @BeforeEach
    void setUp() {
        addProductRequestDto_success = new AddProductRequestDto("객체지향의 사실과 오해",
            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
            "상세 설명", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1, 10, 12000L);

        addProductRequestDto_failure = new AddProductRequestDto("객체지향의 사실과 오해",
            null, "상세 설명", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1, 10, 12000L);
    }

    @Test
    @DisplayName("POST 메서드 성공 테스트")
    void productAddTest_success() throws Exception {
        Long productNo_long = 1L;
        Integer productNo_integer = 1;

        given(productService.addProduct(any(AddProductRequestDto.class))).willReturn(
            productNo_long);

        mockMvc.perform(post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductRequestDto_success)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.productNo", equalTo(productNo_integer)));
    }

    @Test
    @DisplayName("POST 메서드 실패 테스트 - notnull 컬럼에 null 값 저장")
    void productAddTest_failure() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addProductRequestDto_failure)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT 메서드 성공 테스트")
    void productModifyTest_success() throws Exception {
        Long productNo_long = 1L;

        given(productService.findProduct(productNo_long))
            .willReturn(ProductTransfer.dtoToEntityAdd(addProductRequestDto_success));
        Product product = productService.findProduct(productNo_long);
        product.setRawPrice(20000L);

        mockMvc.perform(put("/api/admin/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", equalTo("상품 수정 성공!")));
    }

    @Test
    @DisplayName("PUT 메서드 실패 테스트 - notnull 컬럼에 null 값 저장")
    void productModifyTest_failure() throws Exception {
        Long productNo_long = 1L;

        given(productService.findProduct(productNo_long))
            .willReturn(ProductTransfer.dtoToEntityAdd(addProductRequestDto_success));
        Product product = productService.findProduct(productNo_long);
        product.setRawPrice(null);

        mockMvc.perform(put("/api/admin/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage", containsString("Validation failed")));
    }

    @Test
    @DisplayName("DELETE 메서드 성공 테스트")
    void productDeleteTest_success() throws Exception {
        Long productNo_long = 1L;

        given(productService.findProduct(productNo_long))
            .willReturn(ProductTransfer.dtoToEntityAdd(addProductRequestDto_success));

        mockMvc.perform(delete("/api/admin/products/1")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.header.resultMessage",
                equalTo(ProductResultMessageEnum.DELETE_SUCCESS.getMessage())));

    }

}