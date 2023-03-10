package shop.itbook.itbookshop.category.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.elasticsearch.common.recycler.Recycler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.category.dto.CategoryNoAndProductNoDto;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.exception.NotChildCategoryException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.CategoryService;

/**
 * @author ?????????
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
        ReflectionTestUtils.setField(categoryRequestDto, "categoryName", "??????");
        ReflectionTestUtils.setField(categoryRequestDto, "isHidden", false);
    }

    @DisplayName("????????????????????? ????????????(0?????????) repository ??? ????????? ?????? ????????? ??????????????? ????????????.")
    @Test
    void addCategory_noParentCategory() {
        // given
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 0);

        Category dummyCategory = Category.builder()
            .categoryName("??????")
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

    @DisplayName("????????????????????? ?????? ??????(1???????????????) repository ??? ????????? ?????? ????????? ??????????????? ????????????.")
    @Test
    void addCategory_hasParentCategory() {
        // given
        ReflectionTestUtils.setField(categoryRequestDto, "parentCategoryNo", 1);

        Category dummyCategory = Category.builder()
            .categoryName("IT??????")
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

    @DisplayName("??????????????? ????????? ??? ????????? ??? ????????????.")
    @Test
    void findCategoryListByEmployee() {
        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "??????");
        ReflectionTestUtils.setField(category1, "count", 0L);

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "??????");
        ReflectionTestUtils.setField(category2, "count", 0L);

        given(categoryRepository.findCategoryListByEmployee(any()))
            .willReturn(new PageImpl<>(List.of(category1, category2)));


        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> page =
            categoryService.findCategoryListByEmployee(pageRequest);

        List<CategoryListResponseDto> categoryList = page.getContent();

        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(1);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("??????");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(2);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("??????");
    }

    @DisplayName("?????? ?????????????????? ?????????????????? ???????????? ?????? ??? ????????????.")
    @Test
    void findCategoryChildList() {
        CategoryListResponseDto response1 =
            new CategoryListResponseDto();
        ReflectionTestUtils.setField(response1, "categoryNo", 3);
        ReflectionTestUtils.setField(response1, "categoryName", "??????????????????????????????");

        CategoryListResponseDto response2 =
            new CategoryListResponseDto();
        ReflectionTestUtils.setField(response2, "categoryNo", 4);
        ReflectionTestUtils.setField(response2, "categoryName", "??????????????????????????????");

        PageRequest pageable = PageRequest.of(0, 10);

        // given
        given(categoryRepository.findCategoryListAboutChild(anyInt(), any()))
            .willReturn(new PageImpl(List.of(response1, response2), pageable, 0));

        // when
        Page<CategoryListResponseDto> page =
            categoryService.findCategoryListAboutChild(1, pageable);

        List<CategoryListResponseDto> categoryList = page.getContent();
        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(3);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("??????????????????????????????");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(4);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("??????????????????????????????");
    }

    @DisplayName("????????? ???????????? ???????????? ??? ????????????.")
    @Test
    void findCategoryEntity() {
        // given
        Category dummyCategory = Category.builder()
            .categoryName("IT??????")
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

    @DisplayName("??????????????? ???????????? ???????????? CategoryNotFoundException??? ???????????????.")
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

    @DisplayName("???????????? ???????????? ???????????????????????? ??? ????????????.")
    @Test
    void findCategoryEntityFetch() {
        // given
        Category dummyCategoryBook = CategoryDummy.getCategoryNoHiddenBook();
        Category dummyCategory = Category.builder()
            .categoryName("IT??????")
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

    @DisplayName("???????????? ???????????? ???????????? ????????? CategoryNotFoundException??? ???????????????.")
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

    @DisplayName("???????????? CategoryResponseDto ????????? ??? ????????????.")
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

    @DisplayName("????????????????????? ???????????? ???????????? ?????? ???????????? ??? ????????????.")
    @Test
    void modifyCategory() {
        CategoryModifyRequestDto categoryModifyRequestDto = new CategoryModifyRequestDto();
        ReflectionTestUtils.setField(categoryModifyRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryModifyRequestDto, "categoryName", "?????????");

        Category category = mock(Category.class);
        given(categoryRepository.findCategoryFetch(anyInt())).willReturn(
            Optional.of(category));
        categoryService.modifyCategory(1, categoryModifyRequestDto);

        verify(category).setCategoryName(anyString());
        verify(category).setIsHidden(anyBoolean());
    }

    @DisplayName("????????????????????? ???????????? ???????????? ?????? ???????????? ??? ????????????.")
    @Test
    void modifyCategory_hasParent() {

        CategoryModifyRequestDto categoryModifyRequestDto = new CategoryModifyRequestDto();
        ReflectionTestUtils.setField(categoryModifyRequestDto, "isHidden", false);
        ReflectionTestUtils.setField(categoryModifyRequestDto, "categoryName", "?????????");

        Category category = mock(Category.class);
        given(categoryRepository.findCategoryFetch(anyInt())).willReturn(
            Optional.of(category));
        given(categoryRepository.findById(anyInt()))
            .willReturn(Optional.of(category));
        categoryService.modifyCategory(1, categoryModifyRequestDto);

        verify(category).setCategoryName(anyString());
        verify(category).setIsHidden(anyBoolean());
    }

//    @DisplayName("??????????????? ??? ???????????????.")
//    @Test
//    void removeCategory() {
//        categoryService.removeCategory(1);
//        verify(categoryRepository).deleteById(1);
//    }

    @Test
    void addCategory() {

    }

    @DisplayName("??????????????? ???????????? ????????? ?????? ????????? ????????? ??? ???????????? ????????????.")
    @Test
    void findCategoryListByNotEmployee() {
        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "??????");
        ReflectionTestUtils.setField(category1, "count", 0L);

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "??????");
        ReflectionTestUtils.setField(category2, "count", 0L);

        given(categoryRepository.findCategoryListByNotEmployee(any()))
            .willReturn(new PageImpl<>(List.of(category1, category2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> page =
            categoryService.findCategoryListByNotEmployee(pageRequest);

        List<CategoryListResponseDto> categoryList = page.getContent();

        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(1);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("??????");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(2);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("??????");
    }

    @DisplayName("???????????? ??????????????? ???????????? ???????????? ????????? ?????? ????????? ??????????????? ????????????. ?????? 2???, ?????? 0???(??????????????? ??????????????? ???????????? ???????????? 0??? ???????????? ??????.)")
    @Test
    void findMainCategoryList() {

        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "??????");
        ReflectionTestUtils.setField(category1, "count", 0L);
        ReflectionTestUtils.setField(category1, "level", 0);

        CategoryListResponseDto it = new CategoryListResponseDto();
        ReflectionTestUtils.setField(it, "categoryNo", 3);
        ReflectionTestUtils.setField(it, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(it, "categoryName", "it");
        ReflectionTestUtils.setField(it, "count", 0L);
        ReflectionTestUtils.setField(it, "level", 1);

        CategoryListResponseDto food = new CategoryListResponseDto();
        ReflectionTestUtils.setField(food, "categoryNo", 4);
        ReflectionTestUtils.setField(food, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(food, "categoryName", "food");
        ReflectionTestUtils.setField(food, "count", 0L);
        ReflectionTestUtils.setField(food, "level", 1);

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category1, "parentCategoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "??????");
        ReflectionTestUtils.setField(category2, "count", 0L);
        ReflectionTestUtils.setField(category2, "level", 0);

        PageImpl<CategoryListResponseDto> mainCategoryListPage =
            new PageImpl<>(List.of(category1, it, food, category2));
        List<CategoryListResponseDto> mainCategoryList = mainCategoryListPage.getContent();

        given(categoryRepository.findMainCategoryList(any()))
            .willReturn(mainCategoryListPage);

        CategoryNoAndProductNoDto no1 = new CategoryNoAndProductNoDto();
        ReflectionTestUtils.setField(no1, "categoryNo", 1);
        ReflectionTestUtils.setField(no1, "productNo", 1L);

        CategoryNoAndProductNoDto no2 = new CategoryNoAndProductNoDto();
        ReflectionTestUtils.setField(no2, "categoryNo", 1);
        ReflectionTestUtils.setField(no2, "productNo", 2L);

        given(categoryRepository.getMainCategoryNoAndProductNoForSettingCount(anyList()))
            .willReturn(List.of(no1, no2));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> actualPage =
            categoryService.findMainCategoryList(pageRequest);

        List<CategoryListResponseDto> actual = mainCategoryListPage.getContent();

        // then
        assertThat(actual.get(0).getCategoryNo())
            .isEqualTo(1);
        assertThat(actual.get(0).getCategoryName())
            .isEqualTo("??????");
        assertThat(actual.get(0).getCount())
            .isEqualTo(2);

        assertThat(actual.get(1).getCategoryNo())
            .isEqualTo(3);
        assertThat(actual.get(1).getCategoryName())
            .isEqualTo("it");
        assertThat(actual.get(1).getCount())
            .isEqualTo(0);

        assertThat(actual.get(2).getCategoryNo())
            .isEqualTo(4);
        assertThat(actual.get(2).getCategoryName())
            .isEqualTo("food");
        assertThat(actual.get(2).getCount())
            .isEqualTo(0);

        assertThat(actual.get(3).getCategoryNo())
            .isEqualTo(2);
        assertThat(actual.get(3).getCategoryName())
            .isEqualTo("??????");
        assertThat(actual.get(3).getCount())
            .isEqualTo(0);
    }

    @DisplayName("?????? ?????????????????? ???????????? ????????? ????????? ??? ????????????.")
    @Test
    void findCategoryListAboutChild() {

        // given
        CategoryListResponseDto it = new CategoryListResponseDto();
        ReflectionTestUtils.setField(it, "categoryNo", 3);
        ReflectionTestUtils.setField(it, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(it, "categoryName", "it");
        ReflectionTestUtils.setField(it, "count", 0L);
        ReflectionTestUtils.setField(it, "level", 1);

        CategoryListResponseDto food = new CategoryListResponseDto();
        ReflectionTestUtils.setField(food, "categoryNo", 4);
        ReflectionTestUtils.setField(food, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(food, "categoryName", "food");
        ReflectionTestUtils.setField(food, "count", 0L);
        ReflectionTestUtils.setField(food, "level", 1);

        given(categoryRepository.findCategoryListAboutChild(anyInt(), any()))
            .willReturn(new PageImpl<>(List.of(it, food)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // whenR
        Page<CategoryListResponseDto> categoryListAboutChild =
            categoryService.findCategoryListAboutChild(1, pageRequest);
        List<CategoryListResponseDto> categoryList = categoryListAboutChild.getContent();

        // then
        assertThat(categoryList.get(0).getCategoryNo())
            .isEqualTo(3);
        assertThat(categoryList.get(0).getCategoryName())
            .isEqualTo("it");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(4);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("food");
    }

    @DisplayName("??????????????? ??????????????? ???????????? ???????????? ??? ????????????.")
    @Test
    void findCategoryDetailsResponseDto() {

        // given
        Category category = CategoryDummy.getCategoryNoHiddenBook();
        category.setCategoryNo(1);
        category.setParentCategory(category);

        given(categoryRepository.findCategoryFetch(anyInt()))
            .willReturn(Optional.of(category));

        // when
        CategoryDetailsResponseDto categoryDetailsResponseDto =
            categoryService.findCategoryDetailsResponseDto(1);

        // then
        assertThat(categoryDetailsResponseDto.getCategoryNo())
            .isEqualTo(1);
        assertThat(categoryDetailsResponseDto.getCategoryName())
            .isEqualTo("??????");
    }

    @DisplayName("???????????? ???????????? ????????? ??????????????? ?????? ??????????????? ???????????? ??????????????? ????????? ????????? ??? ????????????.")
    @Test
    void modifyChildSequence_success_nonnull() {

        // given
        Category bookCategory = CategoryDummy.getCategoryNoHiddenBook();
        bookCategory.setCategoryNo(1);
        bookCategory.setParentCategory(bookCategory);

        Category it = Category.builder()
            .categoryName("it")
            .isHidden(false)
            .level(1)
            .sequence(1)
            .parentCategory(bookCategory)
            .build();

        it.setCategoryNo(2);

        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);
        when(food.getParentCategory()).thenReturn(bookCategory);


        given(categoryRepository.findCategoryFetch(food.getCategoryNo()))
            .willReturn(Optional.of(food));
        given(categoryRepository.findCategoryFetch(it.getCategoryNo()))
            .willReturn(Optional.of(it));

        // when
        categoryService.modifyChildSequence(food.getCategoryNo(), it.getCategoryNo());

        // then
        verify(food, never()).setParentCategory(any());
    }

    @DisplayName("??????????????? ?????? ??????????????? ??????????????? ??????????????? ???????????? ???????????? ?????????????????? ?????????.")
    @Test
    void modifyChildSequence_success_null() {
        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);


        given(categoryRepository.findCategoryFetch(food.getCategoryNo()))
            .willReturn(Optional.of(food));

        categoryService.modifyChildSequence(1, 1);
        verify(food, never()).getParentCategory();
    }

    @DisplayName("?????????????????? ??????????????? ????????????????????? ????????? NotChildCategoryException ??? ????????????.")
    @Test
    void modifyChildSequence_fail_NotChildCategoryException() {
        Category bookCategory = CategoryDummy.getCategoryNoHiddenBook();
        bookCategory.setCategoryNo(1);
        bookCategory.setParentCategory(bookCategory);

        given(categoryRepository.findCategoryFetch(bookCategory.getCategoryNo()))
            .willReturn(Optional.of(bookCategory));

        assertThatThrownBy(() -> categoryService.modifyChildSequence(1, 3))
            .isInstanceOf(NotChildCategoryException.class)
            .hasMessage(NotChildCategoryException.MESSAGE);
    }

    @DisplayName("???????????? ???????????? ????????? ???????????? ????????? ?????? ?????? ??? ???????????????.")
    @Test
    void modifyChildSequence_success_parent_change() {

        // given
        Category bookCategory = CategoryDummy.getCategoryNoHiddenBook();
        bookCategory.setCategoryNo(1);
        bookCategory.setParentCategory(bookCategory);

        Category stuffCategory = CategoryDummy.getCategoryHiddenStuff();
        bookCategory.setCategoryNo(2);
        bookCategory.setParentCategory(stuffCategory);

        Category it = Category.builder()
            .categoryName("it")
            .isHidden(false)
            .level(1)
            .sequence(1)
            .parentCategory(bookCategory)
            .build();
        it.setCategoryNo(2);

        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);
        when(food.getParentCategory()).thenReturn(stuffCategory);


        given(categoryRepository.findCategoryFetch(food.getCategoryNo()))
            .willReturn(Optional.of(food));
        given(categoryRepository.findCategoryFetch(it.getCategoryNo()))
            .willReturn(Optional.of(it));

        // when
        categoryService.modifyChildSequence(food.getCategoryNo(), it.getCategoryNo());

        // then
        verify(food).setParentCategory(bookCategory);
    }

    @DisplayName("?????????????????? ?????? ?????? ???????????? ??? ????????????.")
    @Test
    void modifyMainSequence() {
        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);


        given(categoryRepository.findById(food.getCategoryNo()))
            .willReturn(Optional.of(food));

        categoryService.modifyMainSequence(food.getCategoryNo(), 5);

        verify(food).setSequence(5);
    }

    @DisplayName("?????? ?????????????????? ????????? ??????????????? ?????? ?????????????????????.")
    @Test
    void modifyMainSequence_null() {
        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);


        given(categoryRepository.findById(food.getCategoryNo()))
            .willReturn(Optional.of(food));

        categoryService.modifyMainSequence(food.getCategoryNo(), food.getCategoryNo());

        verify(food, never()).setSequence(5);
    }

    @DisplayName("???????????? ????????????????????? ??? ???????????????. false -> true")
    @Test
    void modifyCategoryHidden() {
        Category food = mock(Category.class);
        when(food.getCategoryNo()).thenReturn(3);
        when(food.getCategoryName()).thenReturn("food");
        when(food.getIsHidden()).thenReturn(false);
        when(food.getLevel()).thenReturn(1);
        when(food.getSequence()).thenReturn(2);


        given(categoryRepository.findCategoryFetch(food.getCategoryNo()))
            .willReturn(Optional.of(food));

        categoryService.modifyCategoryHidden(food.getCategoryNo());

        verify(food).getIsHidden();
        verify(food).setIsHidden(true);
    }

    @Test
    void removeCategory() {
    }
}