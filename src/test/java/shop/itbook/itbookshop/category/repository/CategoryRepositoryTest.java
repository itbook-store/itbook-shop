package shop.itbook.itbookshop.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
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
        List<CategoryResponseProjectionDto> categoryList =
            categoryRepository.findCategoryListFetch();

        // then
        assertThat(categoryList)
            .hasSize(2);
        CategoryResponseProjectionDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyBook.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyBook.isHidden());
        assertThat(actual.getParentCategory())
            .isNull();
    }

    @DisplayName("부모의 번호를 통해서 자식 들을 모두 잘 찾는다.")
    @Test
    void findCategoryThroughParentCategoryNo() {

        // when
        List<CategoryChildResponseProjectionDto> categoryList =
            categoryRepository.findCategoryListFetchThroughParentCategoryNo(
                categoryDummyBook.getCategoryNo());


        // then
        assertThat(categoryList)
            .hasSize(1);
        CategoryChildResponseProjectionDto actual = categoryList.get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyStuff.isHidden());
    }
}