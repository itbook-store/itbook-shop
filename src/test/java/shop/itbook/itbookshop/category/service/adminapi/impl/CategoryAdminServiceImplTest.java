package shop.itbook.itbookshop.category.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CategoryAdminServiceImpl.class)
class CategoryAdminServiceImplTest {

    @Autowired
    CategoryAdminService categoryAdminService;

    @MockBean
    CategoryRepository categoryRepository;

    @DisplayName("상위카테고리가 없는경우(0인경우) repository 에 저장한 값의 번호를 정상적으로 반환한다.")
    @Test
    void addCategory_noParentCategory() {
        // given
        CategoryRequestDto categoryResponseDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryResponseDto, "parentCategoryNo", 0);
        ReflectionTestUtils.setField(categoryResponseDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryResponseDto, "isHidden", false);

        Category dummyCategory = new Category(1, null, "도서", false);
        given(categoryRepository.save(any(Category.class)))
            .willReturn(dummyCategory);

        // when
        Integer actual = categoryAdminService.addCategory(categoryResponseDto);

        // then
        assertThat(actual)
            .isEqualTo(dummyCategory.getCategoryNo());
    }

    @DisplayName("상위카테고리가 있는 경우(1이상인경우) repository 에 저장한 값의 번호를 정상적으로 반환한다.")
    @Test
    void addCategory_hasParentCategory() {

        // given
        CategoryRequestDto categoryResponseDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryResponseDto, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(categoryResponseDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryResponseDto, "isHidden", false);

        Category dummyCategory = new Category(3, null, "IT서적", false);
        given(categoryRepository.save(any(Category.class)))
            .willReturn(dummyCategory);

        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Integer actual = categoryAdminService.addCategory(categoryResponseDto);

        // then
        assertThat(actual)
            .isEqualTo(dummyCategory.getCategoryNo());
    }

    @DisplayName("카테고리를 조회한 후 그값을 잘 반환한다.")
    @Test
    void findCategoryList() {
        // given
        given(categoryRepository.findCategoryListFetch())
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

        // when
        List<CategoryResponseProjectionDto> categoryList = categoryAdminService.findCategoryList();

        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(1);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("도서");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(2);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("잡화");
    }

    @DisplayName("자식 카테고리들을 가져오는경우 반환받은 값을 잘 가져온다.")
    @Test
    void findCategoryChildList() {

        // given
        given(categoryRepository.findCategoryChildListThroughParentCategoryNo(anyInt()))
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

        // when
        List<CategoryChildResponseProjectionDto> categoryList =
            categoryAdminService.findCategoryChildList(1);

        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(3);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("객체지향의사실과오해");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(4);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("자바로배우는자료구조");
    }

    @DisplayName("조회한 카테고리 엔티티를 잘 반환한다.")
    @Test
    void findCategoryEntity() {

        // given
        Category dummyCategory = new Category(3, null, "IT서적", false);
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Category category = categoryAdminService.findCategoryEntity(1);

        // then
        assertThat(category.getCategoryNo())
            .isEqualTo(dummyCategory.getCategoryNo());
    }

    @DisplayName("카테고리가 존재하지 않을경우 CategoryNotFoundException을 발생시킨다.")
    @Test
    void findCategoryEntity_fail() {

        // given
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> categoryAdminService.findCategoryEntity(1))
            .isInstanceOf(CategoryNotFoundException.class)
            .hasMessage(CategoryNotFoundException.MESSAGE);
    }

    @DisplayName("엔티티를 조회할때 상위카테고리까지 잘 받아온다.")
    @Test
    void findCategoryEntityFetch() {

        // given
        Category dummyCategoryBook = CategoryDummy.getCategoryNoHiddenBook();
        Category dummyCategory = new Category(3, dummyCategoryBook, "IT서적", false);
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Category category = categoryAdminService.findCategoryEntity(1);

        // then
        assertThat(category.getCategoryNo())
            .isEqualTo(dummyCategory.getCategoryNo());
        assertThat(category.getParentCategory().getCategoryName())
            .isEqualTo(dummyCategoryBook.getCategoryName());
    }

    @DisplayName("엔티티를 조회할때 엔티티가 없다면 CategoryNotFoundException을 발생시킨다.")
    @Test
    void findCategoryEntityFetch_fail() {

        // given
        given(categoryRepository.findCategoryFetch(anyInt()))
            .willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> categoryAdminService.findCategoryEntityFetch(1))
            .isInstanceOf(CategoryNotFoundException.class)
            .hasMessage(CategoryNotFoundException.MESSAGE);
    }

    @Test
    void findCategoryResponseDtoThroughCategoryNo() {
        CategoryResponseDto categoryResponseDto =
            CategoryResponseDto.builder()
                .categoryNo(1)
                .categoryName("도서")
                .build();
    }
}