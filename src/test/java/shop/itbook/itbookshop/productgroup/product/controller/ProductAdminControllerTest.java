package shop.itbook.itbookshop.productgroup.product.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductAdminController.class)
class ProductAdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService mockProductService;

    @MockBean
    BookService bookService;

    @MockBean
    CategoryService mockCategoryService;

    @MockBean
    ProductCategoryService mockProductCategoryService;

    @MockBean
    FileService mockFileService;


    @MockBean
    ProductSearchService productSearchService;
    ProductBookRequestDto productRequestDto;
    ProductBookRequestDto modifyProductRequestDto;

    MockMultipartFile thumbnailsPart;
    MockMultipartFile ebookPart;
    MockMultipartFile requestDtoPart;

    @BeforeEach
    void setUp() throws IOException {
        String FILE_PATH = "src/test/resources/";

        productRequestDto = ProductBookRequestDummy.getProductBookRequest();
        modifyProductRequestDto = ProductBookRequestDummy.getProductBookRequest();
        ReflectionTestUtils.setField(modifyProductRequestDto, "productName", "객체지향의 거짓과 오해");

        String jsonRequestDto = objectMapper.writeValueAsString(productRequestDto);

        thumbnailsPart = new MockMultipartFile("thumbnails", "test.png",
            MediaType.IMAGE_PNG_VALUE, new FileInputStream(FILE_PATH + "test.png"));
        ebookPart = new MockMultipartFile("ebook", "test.pdf",
            MediaType.APPLICATION_PDF_VALUE, new FileInputStream(FILE_PATH + "test.pdf"));
        requestDtoPart = new MockMultipartFile("requestDto", "",
            MediaType.APPLICATION_JSON_VALUE, jsonRequestDto.getBytes());
    }

    @Test
    @DisplayName("POST 메서드 성공 테스트")
    void addProductTest_success() throws Exception {
        Long testProductNo = 1L;

        given(mockProductService.addProduct(any(ProductBookRequestDto.class),
            any(MultipartFile.class), any(MultipartFile.class)))
            .willReturn(testProductNo);

        mockMvc.perform(multipart("/api/admin/products")
                .file(thumbnailsPart)
                .file(requestDtoPart)
                .characterEncoding("UTF-8"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//            .andExpect(jsonPath("$.result.productNo", equalTo(testProductNo)));
    }

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

}