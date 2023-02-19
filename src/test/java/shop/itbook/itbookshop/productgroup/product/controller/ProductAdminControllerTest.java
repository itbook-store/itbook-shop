package shop.itbook.itbookshop.productgroup.product.controller;

import static org.hamcrest.Matchers.equalTo;
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
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.book.transfer.BookTransfer;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.BookResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.service.salesstatus.ProductSalesStatusService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
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
    ProductCategoryService mockProductCategoryService;

    @MockBean
    ProductSalesStatusService mockProductSalesStatusService;

    @MockBean
    ProductSearchService productSearchService;
    ProductAddRequestDto productRequestDto;
    ProductModifyRequestDto productModifyRequestDto;

    MockMultipartFile thumbnailsPart;
    MockMultipartFile ebookPart;
    MockMultipartFile requestDtoPart;

    @BeforeEach
    void setUp() throws IOException {
        String FILE_PATH = "src/test/resources/";

        productRequestDto = ProductDummy.getProductRequest();
        productModifyRequestDto = ProductDummy.getProductModifyRequest();

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

        given(mockProductService.addProduct(productRequestDto, thumbnailsPart))
            .willReturn(testProductNo);

        mockMvc.perform(multipart("/api/admin/products")
                .file(thumbnailsPart)
                .file(requestDtoPart)
                .characterEncoding("UTF-8"))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.ADD_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("PUT 메서드 성공 테스트")
    void modifyBookTest_success() throws Exception {
        Long productNo = 1L;

        given(mockProductService.findProductEntity(productNo))
            .willReturn(ProductDummy.getProductSuccess());

        String json = objectMapper.writeValueAsString(productModifyRequestDto);
        requestDtoPart = new MockMultipartFile("requestDto", "",
            MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        MockMultipartHttpServletRequestBuilder builder =
            multipart("/api/admin/products/1");
        builder.with(request -> {
            request.setMethod("PUT");
            return request;
        });

        mockMvc.perform(builder
                .file(thumbnailsPart)
                .file(requestDtoPart))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }


}