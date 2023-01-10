package shop.itbook.itbookshop.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryWithoutParentFieldResponseDto;
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
        categoryDummyStuff = CategoryDummy.getCategoryNoHiddenStuff();

        categoryRepository.save(categoryDummyBook);
        categoryRepository.save(categoryDummyStuff);

//        categoryDummyBook.setParentCategory(categoryDummyStuff);
        categoryDummyStuff.setParentCategory(categoryDummyBook);

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

        categoryRepository.deleteById(categoryDummyBook.getCategoryNo());
        categoryRepository.deleteById(categoryDummyStuff.getCategoryNo());
    }

    @DisplayName("입력된 데이터만큼(2) 등록된 모든 카테고리를 잘 불러온다.")
    @Test
    void findCategoryList() {

        // when
        List<CategoryAllFieldResponseDto> categoryList =
            categoryRepository.findCategoryListFetch(null);

        // then
        assertThat(categoryList)
            .hasSize(2);
        CategoryAllFieldResponseDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyBook.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyBook.getIsHidden());
        assertThat(actual.getParentCategoryNo())
            .isNull();
    }

    @DisplayName("부모의 번호를 통해서 자식 들을 모두 잘 찾는다.")
    @Test
    void findCategoryThroughParentCategoryNo() {

        // when
        List<CategoryWithoutParentFieldResponseDto> categoryList =
            categoryRepository.findCategoryChildListThroughParentCategoryNo(
                categoryDummyBook.getCategoryNo(), null);

        // then
        assertThat(categoryList)
            .hasSize(1);
        CategoryWithoutParentFieldResponseDto actual = categoryList.get(0);

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
        Category category = categoryRepository.findCategoryFetch(2).get();

        //given
        assertThat(category.getParentCategory().getCategoryName())
            .isEqualTo("도서");
        assertThat(category.getParentCategory().getCategoryNo())
            .isEqualTo(1);
    }
}