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
    void findCategoryListByEmployee() {
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


        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> page =
            categoryService.findCategoryListByEmployee(pageRequest);

        List<CategoryListResponseDto> categoryList = page.getContent();

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

    @Test
    void addCategory() {

    }

    @DisplayName("일반회원이 조회하는 요청에 대한 서비스 로직이 잘 실행되고 반환된다.")
    @Test
    void findCategoryListByNotEmployee() {
        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "도서");
        ReflectionTestUtils.setField(category1, "count", 0L);

        CategoryListResponseDto category2 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category2, "categoryNo", 2);
        ReflectionTestUtils.setField(category2, "categoryName", "잡화");
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
            .isEqualTo("도서");
        assertThat(categoryList.get(1).getCategoryNo())
            .isEqualTo(2);
        assertThat(categoryList.get(1).getCategoryName())
            .isEqualTo("잡화");
    }

    @DisplayName("대분류와 상품번호를 가져온뒤 대분류의 갯수를 세는 로직이 정상적으로 작동한다. 도서 2개, 잡화 0개(소분류들은 계산로직이 안돌아서 여기서는 0이 나오는게 맞다.)")
    @Test
    void findMainCategoryList() {

        // given
        CategoryListResponseDto category1 = new CategoryListResponseDto();
        ReflectionTestUtils.setField(category1, "categoryNo", 1);
        ReflectionTestUtils.setField(category1, "parentCategoryNo", 1);
        ReflectionTestUtils.setField(category1, "categoryName", "도서");
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
        ReflectionTestUtils.setField(category2, "categoryName", "잡화");
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
            .isEqualTo("도서");
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
            .isEqualTo("잡화");
        assertThat(actual.get(3).getCount())
            .isEqualTo(0);
    }

    @DisplayName("자식 카테고리들을 불러오는 서비스 로직이 잘 수행된다.")
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

    @DisplayName("카테고리가 존재할시에 상세조회 서비스가 잘 동작한다.")
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
            .isEqualTo("도서");
    }

    @DisplayName("카테고리 수정시에 부모가 널이아니고 부모 카테고리는 변경되지 않는경우에 서비스 로직이 잘 동작한다.")
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

    @DisplayName("변경하고자 하는 카테고리와 변경해야할 카테고리가 같은경우 서비스는 아무동작하지 않는다.")
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

    @DisplayName("자식카테고리 위치변경에 부모카테고리가 온경우 NotChildCategoryException 이 발생한다.")
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

    @DisplayName("카테고리 수정시에 부모가 다른경우 부모의 저장 까지 잘 이루어진다.")
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

    @DisplayName("메인카테고리 순서 변경 서비스가 잘 동작한다.")
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

    @DisplayName("같은 가테고리간에 변경을 하려고하면 아무 동작하지않는다.")
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

    @DisplayName("카테고리 히든속성변경이 잘 이루어진다. false -> true")
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