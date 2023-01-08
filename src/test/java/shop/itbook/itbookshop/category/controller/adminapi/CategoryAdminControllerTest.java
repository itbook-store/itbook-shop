package shop.itbook.itbookshop.category.controller.adminapi;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;
import shop.itbook.itbookshop.category.service.serviceapi.CategoryService;

/**
 * @author 최겸준
 * @since 1.0
 */
@WebMvcTest(CategoryAdminController.class)
class CategoryAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CategoryAdminController categoryAdminController;

    @MockBean
    CategoryAdminService categoryAdminService;

    @Test
    void categorySave() {

    }

    @Test
    void categoryList() {
    }

    @Test
    void categoryChildList() {
    }

    @Test
    void categoryDetails() throws Exception {

        // given
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
            .categoryNo(1)
            .parentCategoryNo(1)
            .categoryName("도서")
            .isHidden(false)
            .build();

        given(categoryAdminService.findCategoryResponseDtoThroughCategoryNo(any()))
            .willReturn(categoryResponseDto);

        // when
        mvc.perform(get("/api/admin/categories/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }
}