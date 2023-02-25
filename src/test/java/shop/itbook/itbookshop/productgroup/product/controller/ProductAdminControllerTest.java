package shop.itbook.itbookshop.productgroup.product.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.type.ArrayType;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.service.salesstatus.ProductSalesStatusService;
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
    @DisplayName("도서 등록 성공 테스트")
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
    @DisplayName("도서 수정 성공 테스트")
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

    @Test
    @DisplayName("모든 상품 조회 성공 테스트")
    void findProductList_success() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(mockProductService.findProductListForAdmin(any(Pageable.class)))
            .willReturn(page);

        mockMvc.perform(get("/api/admin/products"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("카테고리 상품 조회 성공 테스트")
    void productListFilteredByCategoryNo_success() throws Exception {
        Product product1 = ProductDummy.getProductSuccess();
        Product product2 = ProductDummy.getProductSuccess();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(product1, product2), pageable, 10);

        given(mockProductCategoryService.findProductList(any(Pageable.class), anyInt()))
            .willReturn(page);

        mockMvc.perform(get("/api/admin/products?categoryNo=2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품별 카테고리 조회 성공 테스트")
    void productListFilteredByProductNo_success() throws Exception {
        Category category = CategoryDummy.getCategoryNoHiddenBook();
        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(category), pageable, 10);

        given(mockProductCategoryService.findCategoryList(any(Pageable.class), anyLong()))
            .willReturn(page);

        mockMvc.perform(get("/api/admin/products?productNo=2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 삭제여부 수정 성공 테스트")
    void changeDeleteField_success() throws Exception {
        Long productNo = 1L;

        mockProductService.changeBooleanField(productNo, "delete");

        mockMvc.perform(put("/api/admin/products/modify/1?fieldName=delete"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 강제품절여부 수정 성공 테스트")
    void changeIsForceSoldOutField_success() throws Exception {
        Long productNo = 1L;

        mockProductService.changeBooleanField(productNo, "isForceSoldOut");

        mockMvc.perform(put("/api/admin/products/modify/1?fieldName=isForceSoldOut"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 판매여부 수정 성공 테스트")
    void changeIsSelledField_success() throws Exception {
        Long productNo = 1L;

        mockProductService.changeBooleanField(productNo, "isSelled");

        mockMvc.perform(put("/api/admin/products/modify/1?fieldName=isSelled"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 조회수 업데이트 성공 테스트")
    void updateDailyHits_success() throws Exception {
        Long productNo = 1L;

        mockProductService.changeDailyHits(productNo);

        mockMvc.perform(put("/api/admin/products/modify-dailyhits/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.MODIFY_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 상세정보 조회 성공 테스트")
    void findProductDetails_success() throws Exception {
        Long productNo = 1L;
        ProductDetailsResponseDto productDetailsResponseDto =
            ProductDummy.getProductDetailsResponseDto();
        given(mockProductService.findProduct(productNo)).willReturn(productDetailsResponseDto);

        mockMvc.perform(get("/api/admin/products/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }

    @Test
    @DisplayName("상품 판매순위 집계 조회 성공 테스트")
    void findProductSalesRank_success() throws Exception {
        ProductSalesRankResponseDto productSalesRankResponseDto =
            new ProductSalesRankResponseDto(1L, "상품", 4, 15000L);

        Pageable pageable = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(productSalesRankResponseDto), pageable, 10);

        given(mockProductSalesStatusService.findSortingList(anyString(),
            any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/admin/products/sales-rank?sortingCriteria=취소건"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    equalTo(ProductResultMessageEnum.GET_SUCCESS.getMessage())));
    }
}