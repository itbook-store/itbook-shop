package shop.itbook.itbookshop.category.controller.adminapi;


import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;
import shop.itbook.itbookshop.category.service.serviceapi.CategoryService;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;

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


    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("저장에 관련한 요청이 잘들어오고 PK 값이(1) 잘 반환된다.")
    @Test
    void categorySave() throws Exception {

        // given
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);

        Integer testNo = 1;

        given(categoryAdminService.addCategory(any(CategoryRequestDto.class)))
            .willReturn(testNo);

        // when then
        mvc.perform(post("/api/admin/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.categoryNo", equalTo(testNo)));
    }

    @DisplayName("카테고리 내 모든 리스트가 반환된다.")
    @Test
    void categoryList() throws Exception {
        given(categoryAdminService.findCategoryList())
            .willReturn(List.of(new CategoryResponseProjectionDto() {
                @Override
                public Integer getCategoryNo() {
                    return 1;
                }

                @Override
                public CategoryResponseProjectionDto getParentCategory() {
                    return null;
                }

                @Override
                public String getCategoryName() {
                    return "도서";
                }

                @Override
                public boolean getIsHidden() {
                    return false;
                }
            }, new CategoryResponseProjectionDto() {
                @Override
                public Integer getCategoryNo() {
                    return 2;
                }

                @Override
                public CategoryResponseProjectionDto getParentCategory() {
                    return null;
                }

                @Override
                public String getCategoryName() {
                    return "잡화";
                }

                @Override
                public boolean getIsHidden() {
                    return false;
                }
            }));

        mvc.perform(get("/api/admin/categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result[0].categoryNo", equalTo(1)))
            .andExpect(jsonPath("$.result[0].categoryName", equalTo("도서")))
            .andExpect(jsonPath("$.result[1].categoryNo", equalTo(2)))
            .andExpect(jsonPath("$.result[1].categoryName", equalTo("잡화")));
    }

    @DisplayName("특정 카테고리의 자식카테고리들이 모두 반환된다.")
    @Test
    void categoryChildList() throws Exception {
        given(categoryAdminService.findCategoryChildList(anyInt()))
            .willReturn(List.of(new CategoryChildResponseProjectionDto() {
                @Override
                public Integer getCategoryNo() {
                    return 3;
                }

                @Override
                public String getCategoryName() {
                    return "객체지향의사실과오해";
                }

                @Override
                public boolean getIsHidden() {
                    return false;
                }
            }, new CategoryChildResponseProjectionDto() {
                @Override
                public Integer getCategoryNo() {
                    return 4;
                }

                @Override
                public String getCategoryName() {
                    return "자바로배우는자료구조";
                }

                @Override
                public boolean getIsHidden() {
                    return false;
                }
            }));

        mvc.perform(get("/api/admin/categories/1/child-categories"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result[0].categoryNo", equalTo(3)))
            .andExpect(jsonPath("$.result[0].categoryName", equalTo("객체지향의사실과오해")))
            .andExpect(jsonPath("$.result[1].categoryNo", equalTo(4)))
            .andExpect(jsonPath("$.result[1].categoryName", equalTo("자바로배우는자료구조")));
    }

    @DisplayName("카테거리 단건조회 요청이 잘 받아지고 원하는 형태로 잘 반환된다.")
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
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                jsonPath("$.result.categoryNo", equalTo(categoryResponseDto.getCategoryNo())))
            .andExpect(
                jsonPath("$.result.categoryName",
                    equalTo(categoryResponseDto.getCategoryName())))
            .andExpect(jsonPath("$.result.parentCategoryNo",
                equalTo(categoryResponseDto.getParentCategoryNo())))
            .andExpect(
                jsonPath("$.result.isHidden", equalTo(categoryResponseDto.getIsHidden())));
    }
}