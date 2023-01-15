package shop.itbook.itbookshop.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Category categoryDummyBook;

    Category categoryDummyStuff;

    @BeforeEach
    void setUp() {

        //given
        categoryDummyBook = CategoryDummy.getCategoryNoHiddenBook();
        categoryDummyStuff = CategoryDummy.getCategoryHiddenStuff();

        categoryRepository.save(categoryDummyBook);
        categoryRepository.save(categoryDummyStuff);

        categoryDummyStuff.setParentCategory(categoryDummyBook);

//        categoryDummyBook.setParentCategory(categoryDummyStuff);
//        categoryDummyStuff.setParentCategory(categoryDummyBook);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @DisplayName("카테고리가 잘 찾아와지는지 확인한다.")
    void findById() {

        // when
        Category category =
            categoryRepository.findById(categoryDummyBook.getCategoryNo()).orElseThrow();

        // then
        assertThat(category.getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
    }

    @DisplayName("삭제가 잘 이루어진다.")
    @Test
    void deleteById() {

        categoryRepository.deleteById(categoryDummyStuff.getCategoryNo());
        categoryRepository.deleteById(categoryDummyBook.getCategoryNo());
    }

    @DisplayName("도서를 부모로 가진 잡화 카테고리가 잘 불러와진다.")
    @Test
    void findCategoryList() {

        // when
        List<CategoryListResponseDto> categoryList =
            categoryRepository.findCategoryListByEmployee();

        // then
        CategoryListResponseDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyStuff.getIsHidden());
        assertThat(actual.getParentCategoryNo())
            .isNull();
    }


    @DisplayName("부모의 번호를 통해서 자식 들을 모두 잘 찾는다.")
    @Test
    void findCategoryChildListThroughParentCategoryNo() {

        // when
        List<CategoryListResponseDto> categoryList =
            categoryRepository.findCategoryListAboutChild(
                categoryDummyBook.getCategoryNo());

        // then
        assertThat(categoryList)
            .hasSize(1);
        CategoryListResponseDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyStuff.getIsHidden());
    }

    @DisplayName("부모의 번호를 통해서 hidden 이 true 인 자식 들을 모두 잘 찾는다.")
    @Test
    void findCategoryChildListThroughParentCategory_hidden() {

        // when
        List<CategoryListResponseDto> categoryList =
            categoryRepository.findCategoryListAboutChild(
                categoryDummyBook.getCategoryNo());

        // then
        assertThat(categoryList)
            .hasSize(1);
        CategoryListResponseDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyStuff.getIsHidden());
    }

    @DisplayName("카테고리를 찾아올때 부모카테고리까지 함께 얻어온다.")
    @Test
    void findCategoryFetch() {

        // when
        Category category =
            categoryRepository.findCategoryFetch(categoryDummyStuff.getCategoryNo()).get();

        //given
        assertThat(category.getParentCategory().getCategoryName())
            .isEqualTo("도서");
        assertThat(category.getParentCategory().getCategoryNo())
            .isEqualTo(categoryDummyStuff.getParentCategory().getCategoryNo());
    }

    @DisplayName("카테고리를 찾아올때 자식카테고리까지 함께 얻어온다.")
    @Test
    void findCategoryFetch_child() {

        // when
        Category category =
            categoryRepository.findCategoryFetch(categoryDummyStuff.getCategoryNo()).get();

        //given
        assertThat(category.getParentCategory().getCategoryName())
            .isEqualTo("도서");
        assertThat(category.getParentCategory().getCategoryNo())
            .isEqualTo(categoryDummyStuff.getParentCategory().getCategoryNo());
    }

    @DisplayName("메인카테고리를 갯수까지 순서에맞게 잘 가져온다.")
    @Test
    void findMainCategoryList() {

        List<CategoryListResponseDto> mainCategoryList = categoryRepository.findMainCategoryList();

        assertThat(mainCategoryList)
            .hasSize(2);
        assertThat(mainCategoryList.get(0).getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
        assertThat(mainCategoryList.get(0).getCategoryName())
            .isEqualTo(categoryDummyBook.getCategoryName());
        assertThat(mainCategoryList.get(0).getCount())
            .isEqualTo(0);

        assertThat(mainCategoryList.get(1).getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(mainCategoryList.get(1).getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(mainCategoryList.get(1).getCount())
            .isEqualTo(0);
    }

}