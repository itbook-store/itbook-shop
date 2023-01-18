package shop.itbook.itbookshop.category.advisor;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.category.controller.adminapi.CategoryAdminController;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;

@WebMvcTest(CategoryAdminController.class)
class CategoryRestControllerAdvisorTest {

    @Autowired
    CategoryAdminController categoryAdminController;

    @MockBean
    CategoryService categoryService;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("MemberForbiddenException 이 발생할경우 잘 잡는다.")
    @Test
    void categoryAdd_fail_403_MemberForbiddenException() throws Exception {

        // given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryRequestDto, "sequence", 1);

        given(categoryService.addCategory(any(CategoryRequestDto.class)))
            .willThrow(new MemberForbiddenException());

        // when then
        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequestDto)))
            .andExpect(status().isForbidden())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result", equalTo(null)))
            .andExpect(
                jsonPath("$.header.resultMessage", equalTo(MemberForbiddenException.MESSAGE)));
    }

    @DisplayName("CategoryNotFoundException 이 발생할경우 잘 잡는다.")
    @Test
    void categoryAdd_fail_400_CategoryNotFoundException() throws Exception {

        // given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryRequestDto, "sequence", 1);

        given(categoryService.addCategory(any(CategoryRequestDto.class)))
            .willThrow(new CategoryNotFoundException());

        // when then
        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result", equalTo(null)))
            .andExpect(
                jsonPath("$.header.resultMessage", equalTo(CategoryNotFoundException.MESSAGE)));
    }

    @DisplayName("MethodArgumentNotValidException 이 발생할경우 잘 잡는다.")
    @Test
    void categoryAdd_fail_400_MethodArgumentNotValidException() throws Exception {

        // given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);

        // when then
        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequestDto)))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result", equalTo(null)))
            .andExpect(
                jsonPath("$.header.resultMessage",
                    containsString("카테고리 명은 null 일수 없으며 빈문자열이나 공백하나만 들어올 수 없습니다.")));
    }

    @DisplayName("RuntimeException 이 발생할경우 잘 잡는다.")
    @Test
    void categoryAdd_fail_500_RuntimeException() throws Exception {

        // given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryRequestDto, "sequence", 1);

        given(categoryService.addCategory(any(CategoryRequestDto.class)))
            .willThrow(new RuntimeException("test message"));

        // when then
        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequestDto)))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result", equalTo(null)))
            .andExpect(
                jsonPath("$.header.resultMessage", equalTo("test message")));
    }
}