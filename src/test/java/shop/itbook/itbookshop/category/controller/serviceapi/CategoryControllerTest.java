package shop.itbook.itbookshop.category.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.service.serviceapi.CategoryService;

/**
 * @author 최겸준
 * @since 1.0
 */

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CategoryService categoryService;

    Category categoryNoHiddenBook;
    Category categoryHiddenBook;
    Category categoryHiddenStuff;
    Category categoryNoHiddenStuff;

    CategoryAllFieldResponseDto categoryAllFieldResponseDtoTrue;
    CategoryAllFieldResponseDto categoryAllFieldResponseDtoFalse;

    @BeforeEach
    void setUp() {
        categoryHiddenBook = CategoryDummy.getCategoryHiddenBook();
        categoryNoHiddenBook = CategoryDummy.getCategoryNoHiddenBook();
        categoryHiddenStuff = CategoryDummy.getCategoryHiddenStuff();
        categoryNoHiddenStuff = CategoryDummy.getCategoryNoHiddenStuff();
        categoryAllFieldResponseDtoTrue = new CategoryAllFieldResponseDto();

        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "categoryNo", 3);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "categoryName", "IT서적");
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "isHidden", false);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "level", 1);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "parentCategoryName", "도서");
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "parentCategoryIsHidden",
            false);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoTrue, "parentCategoryLevel", 0);

        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "categoryNo", 3);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "categoryName", "IT서적");
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "isHidden", true);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "level", 1);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "parentCategoryName", "도서");
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "parentCategoryIsHidden",
            false);
        ReflectionTestUtils.setField(categoryAllFieldResponseDtoFalse, "parentCategoryLevel", 0);

    }

//    @Test
//    void categoryDetails() throws Exception {

//        mvc.perform(get("/1"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.result.categoryNo",
//                equalTo(categoryAllFieldResponseDtoFalse.getCategoryNo())))
//            .andExpect(jsonPath("$.result.categoryName",
//                equalTo(categoryAllFieldResponseDtoFalse.getCategoryName())))
//            .andExpect(jsonPath("$.result.level",
//                equalTo(categoryAllFieldResponseDtoFalse.getParentCategoryNo())))
//            .andExpect(jsonPath("$.result.parentCategoryName",
//                equalTo(categoryAllFieldResponseDtoFalse.getParentCategoryName())));

//    }
}