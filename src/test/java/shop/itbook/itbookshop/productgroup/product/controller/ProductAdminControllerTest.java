//package shop.itbook.itbookshop.productgroup.product.controller;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
//import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
//import shop.itbook.itbookshop.productgroup.product.entity.Product;
//import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
//import shop.itbook.itbookshop.productgroup.product.service.ProductService;
//import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
//
///**
// * @author 이하늬
// * @since 1.0
// */
//@WebMvcTest(ProductAdminController.class)
//class ProductAdminControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    ProductService productService;
//    ProductRequestDto productRequestDto_success;
//    ProductRequestDto productRequestDto_failure;
//
//    MockMultipartFile mockImageFile;
//    MockMultipartFile mockPdfFile;
//
//    @BeforeEach
//    void setUp() throws IOException {
//        String fileName = "file";
//        String imageContentType = "image/jpg";
//        String pdfContentType = "application/pdf";
//
//        String path = "src/test/resources/testImage/" + fileName + ".";
//        List<Integer> categoryList = new ArrayList<>();
//        productRequestDto_success = ProductTransfer.dtoToEntityAdd()
//
//        mockImageFile = new MockMultipartFile("image", "test.png", "image/jpg",
//            new FileInputStream(path + imageContentType));
//    }
//
//    @Test
//    @DisplayName("POST 메서드 성공 테스트")
//    void productAddTest_success() throws Exception {
//        Long productNo_long = 1L;
//        Integer productNo_integer = 1;
//
//        given(productService.addProduct(any(ProductRequestDto.class))).willReturn(
//            productNo_long);
//
//        mockMvc.perform(post("/api/admin/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productRequestDto_success)))
//            .andExpect(status().isCreated())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.result.productNo", equalTo(productNo_integer)));
//    }
//
//    @Test
//    @DisplayName("POST 메서드 실패 테스트 - notnull 컬럼에 null 값 저장")
//    void productAddTest_failure() throws Exception {
//        mockMvc.perform(post("/api/products")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(productRequestDto_failure)))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("PUT 메서드 성공 테스트")
//    void productModifyTest_success() throws Exception {
//        Long productNo_long = 1L;
//
//        given(productService.findProductEntity(productNo_long))
//            .willReturn(ProductTransfer.dtoToEntityAdd(productRequestDto_success));
//        Product product = productService.findProductEntity(productNo_long);
//        product.setRawPrice(20000L);
//
//        mockMvc.perform(put("/api/admin/products/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(product)))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.header.resultMessage", equalTo("상품 수정 성공!")));
//    }
//
//    @Test
//    @DisplayName("PUT 메서드 실패 테스트 - notnull 컬럼에 null 값 저장")
//    void productModifyTest_failure() throws Exception {
//        Long productNo_long = 1L;
//
//        given(productService.findProductEntity(productNo_long))
//            .willReturn(ProductTransfer.dtoToEntityAdd(productRequestDto_success));
//        Product product = productService.findProductEntity(productNo_long);
//        product.setRawPrice(null);
//
//        mockMvc.perform(put("/api/admin/products/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(product)))
//            .andExpect(status().isBadRequest())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.header.resultMessage", containsString("Validation failed")));
//    }
//
//    @Test
//    @DisplayName("DELETE 메서드 성공 테스트")
//    void productDeleteTest_success() throws Exception {
//        Long productNo_long = 1L;
//
//        given(productService.findProductEntity(productNo_long))
//            .willReturn(ProductTransfer.dtoToEntityAdd(productRequestDto_success));
//
//        mockMvc.perform(delete("/api/admin/products/1")
//                .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.header.resultMessage",
//                equalTo(ProductResultMessageEnum.DELETE_SUCCESS.getMessage())));
//
//    }
//
//}