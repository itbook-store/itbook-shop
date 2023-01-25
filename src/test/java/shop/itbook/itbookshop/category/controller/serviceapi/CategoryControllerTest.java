package shop.itbook.itbookshop.category.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.config.WebMvcConfig;
import shop.itbook.itbookshop.productgroup.product.fileservice.FileService;
import shop.itbook.itbookshop.productgroup.product.fileservice.init.TokenManager;

/**
 * @author 최겸준
 * @since 1.0
 */

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryService categoryService;

    @DisplayName("카테고리 내 모든 리스트가 반환된다.")
    @Test
    void categoryList() throws Exception {

        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "도서");

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "잡화");

        given(categoryService.findCategoryListByNotEmployee())
            .willReturn(List.of(category1, category2));

        mvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result[0].categoryNo", equalTo(1)))
            .andExpect(jsonPath("$.result[0].categoryName", equalTo("도서")))
            .andExpect(jsonPath("$.result[1].categoryNo", equalTo(2)))
            .andExpect(jsonPath("$.result[1].categoryName", equalTo("잡화")));
    }
}