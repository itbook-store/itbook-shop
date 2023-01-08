package shop.itbook.itbookshop.productgroup.product.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductRestController;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductService;

/**
 * @author 이하늬
 * @since 1.0
 */
@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private ProductService productService;
    private AddProductRequestDto addProductRequestDto_success;
    private AddProductRequestDto addProductRequestDto_failure;

    @BeforeEach
    void setUp() {
        addProductRequestDto_success = new AddProductRequestDto("객체지향의 사실과 오해",
            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
            "다 모르겠고 그냥 제발 됐으면 좋겠네요..", 1, true, false,
            false, "testUrl", 20000L, 1, 10, 12000L);

        addProductRequestDto_failure = new AddProductRequestDto("객체지향의 사실과 오해",
            null, "다 모르겠고 그냥 제발 됐으면 좋겠네요..", 1, true, false,
            false, "testUrl", 20000L, 1, 10, 12000L);

        given(productService.findProduct(1L))
            .willReturn(
                Product.builder().name("객체지향의 사실과 오해")
                    .simpleDescription(
                        "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
                    .detailsDescription("다 모르겠고 그냥 제발 됐으면 좋겠네요..").stock(1).isSelled(true)
                    .isDeleted(false)
                    .isSubscription(false).thumbnailUrl("testUrl").fixedPrice(20000L)
                    .increasePointPercent(1).discountPercent(10).rawPrice(12000L).dailyHits(0L)
                    .productCreatedAt(LocalDateTime.now()).build()
            );
    }

    @Test
    @DisplayName("POST 메서드 성공 테스트")
    void Test1() throws Exception {
        mockMvc.perform(post("/api/admin/products")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addProductRequestDto_success)))
            .andExpect(status().isCreated());
    }

//    @Test
//    @DisplayName("POST 메서드 실패 테스트")
//    void Test2() throws Exception {
//        mockMvc.perform(post("/api/products")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(addProductRequestDto_failure)))
//            .andExpect(status().isBadRequest());
//    }

    @Test
    @DisplayName("PUT 메서드 실패 테스트")
    void Test3() {
        Product product = productService.findProduct(1L);

    }
}