package shop.itbook.itbookshop.category.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.category.service.impl.CategoryServiceImpl;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CategoryServiceImpl.class)
class CategoryServiceImplTest {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    CategoryRequestDto categoryRequestDto;

    @BeforeEach
    void setUp() {
        categoryRequestDto = new CategoryRequestDto();
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "도서");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);
    }

    @DisplayName("상위카테고리가 없는경우(0인경우) repository 에 저장한 값의 번호를 정상적으로 반환한다.")
    @Test
    void addCategory_noParentCategory() {
        // given
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 0);

        Category dummyCategory = Category.builder()
            .categoryName("도서")
            .isHidden(false)
            .parentCategory(null)
            .level(0)
            .sequence(1)
            .build();
        dummyCategory.setCategoryNo(1);

        given(categoryRepository.save(any(Category.class)))
            .willReturn(dummyCategory);

        // when
        Integer actual = categoryService.addCategory(categoryRequestDto);

        // then
        assertThat(actual)
            .isEqualTo(dummyCategory.getCategoryNo());
    }

    @DisplayName("상위카테고리가 있는 경우(1이상인경우) repository 에 저장한 값의 번호를 정상적으로 반환한다.")
    @Test
    void addCategory_hasParentCategory() {
        // given
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);

        Category dummyCategory = Category.builder()
            .categoryName("IT서적")
            .isHidden(false)
            .parentCategory(null)
            .level(0)
            .sequence(1)
            .build();
        dummyCategory.setCategoryNo(3);

        given(categoryRepository.save(any(Category.class)))
            .willReturn(dummyCategory);

        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Integer actual = categoryService.addCategory(categoryRequestDto);

        // then
        assertThat(actual)
            .isEqualTo(dummyCategory.getCategoryNo());
    }

    @DisplayName("카테고리를 조회한 후 그값을 잘 반환한다.")
    @Test
    void findCategoryList() {
        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "도서");
        ReflectionTestUtils.setField(category1, "count", 0L);

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "잡화");
        ReflectionTestUtils.setField(category2, "count", 0L);

        given(categoryRepository.findCategoryListByEmployee(any()))
            .willReturn(new PageImpl<>(List.of(category1, category2)));

        // when
        List<CategoryListResponseDto> categoryList = categoryService.findCategoryListByEmployee(
            null);

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
        CategoryListResponseDto response1 =
            new CategoryListResponseDto();
        ReflectionTestUtils.setField(response1, "categoryNo", 3);
        ReflectionTestUtils.setField(response1, "categoryName", "객체지향의사실과오해");

        CategoryListResponseDto response2 =
            new CategoryListResponseDto();
        ReflectionTestUtils.setField(response2, "categoryNo", 4);
        ReflectionTestUtils.setField(response2, "categoryName", "자바로배우는자료구조");

        // given
        given(categoryRepository.findCategoryListAboutChild(anyInt()))
            .willReturn(List.of(response1, response2));

        // when
        List<CategoryListResponseDto> categoryList =
            categoryService.findCategoryListAboutChild(1);

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
        Category dummyCategory = Category.builder()
            .categoryName("IT서적")
            .isHidden(false)
            .parentCategory(null)
            .level(0)
            .sequence(1)
            .build();
        dummyCategory.setCategoryNo(3);

        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Category category = categoryService.findCategoryEntity(1);

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
        assertThatThrownBy(() -> categoryService.findCategoryEntity(1))
            .isInstanceOf(CategoryNotFoundException.class)
            .hasMessage(CategoryNotFoundException.MESSAGE);
    }

    @DisplayName("엔티티를 조회할때 상위카테고리까지 잘 받아온다.")
    @Test
    void findCategoryEntityFetch() {
        // given
        Category dummyCategoryBook = CategoryDummy.getCategoryNoHiddenBook();
        Category dummyCategory = Category.builder()
            .categoryName("IT서적")
            .isHidden(false)
            .parentCategory(dummyCategoryBook)
            .level(0)
            .sequence(1)
            .build();
        dummyCategory.setCategoryNo(3);
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(dummyCategory));

        // when
        Category category = categoryService.findCategoryEntity(1);

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
        assertThatThrownBy(() -> categoryService.findCategoryEntityFetch(1))
            .isInstanceOf(CategoryNotFoundException.class)
            .hasMessage(CategoryNotFoundException.MESSAGE);
    }

    @DisplayName("엔티티를 CategoryResponseDto 형태로 잘 가져온다.")
    @Test
    void findCategoryResponseDtoThroughCategoryNo() {

        //given
        Category bookCategory = CategoryDummy.getCategoryNoHiddenBook();
        bookCategory.setParentCategory(bookCategory);
        given(categoryRepository.findCategoryFetch(anyInt()))
            .willReturn(Optional.of(bookCategory));

        //when
        CategoryDetailsResponseDto actual =
            categoryService.findCategoryDetailsResponseDto(1);

        //then
        assertThat(actual.getCategoryName())
            .isEqualTo(bookCategory.getCategoryName());
    }

    @DisplayName("부모카테고리가 없는경우 카테고리 수정 메서드가 잘 동작한다.")
    @Test
    void modifyCategory() {
        CategoryModifyRequestDto categoryModifyRequestDto = new CategoryModifyRequestDto();
        ReflectionTestUtils.setField(categoryModifyRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryModifyRequestDto, "categoryName", "홍길동");

        Category category = mock(Category.class);
        given(categoryRepository.findCategoryFetch(anyInt())).willReturn(
            Optional.of(category));
        categoryService.modifyCategory(1, categoryModifyRequestDto);

        verify(category).setCategoryName(anyString());
        verify(category).setIsHidden(anyBoolean());
    }

    @DisplayName("부모카테고리가 있는경우 카테고리 수정 메서드가 잘 동작한다.")
    @Test
    void modifyCategory_hasParent() {

        CategoryModifyRequestDto categoryModifyRequestDto = new CategoryModifyRequestDto();
        ReflectionTestUtils.setField(categoryModifyRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryModifyRequestDto, "categoryName", "홍길동");

        Category category = mock(Category.class);
        given(categoryRepository.findCategoryFetch(anyInt())).willReturn(
            Optional.of(category));
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(category));
        categoryService.modifyCategory(1, categoryModifyRequestDto);

        verify(category).setCategoryName(anyString());
        verify(category).setIsHidden(anyBoolean());
    }

//    @DisplayName("삭제행위가 잘 이루어진다.")
//    @Test
//    void removeCategory() {
//        categoryService.removeCategory(1);
//        verify(categoryRepository).deleteById(1);
//    }
}