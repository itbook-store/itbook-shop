package shop.itbook.itbookshop.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.category.dto.CategoryNoAndProductNoDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Category categoryDummyBook;

    Category categoryDummyStuff;

    Product dummyProduct1;
    Product dummyProduct2;
    Category dummySubCategory;
    Category dummyMainCategory;
    Product savedProduct1;
    ProductCategory dummyProductCategory1;


    @BeforeEach
    void setUp() {

        //given
        categoryDummyBook = CategoryDummy.getCategoryNoHiddenBook();
        categoryDummyStuff = CategoryDummy.getCategoryHiddenStuff();

        categoryRepository.save(categoryDummyBook);
        categoryRepository.save(categoryDummyStuff);

        categoryDummyStuff.setParentCategory(categoryDummyBook);

        dummyProduct1 = ProductDummy.getProductSuccess();
        dummyProduct2 = ProductDummy.getProductSuccess();

        savedProduct1 = productRepository.save(dummyProduct1);


        dummyProductCategory1 =
            new ProductCategory(savedProduct1, categoryDummyStuff);

        productCategoryRepository.save(dummyProductCategory1);

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

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> categoryList =
            categoryRepository.findCategoryListByEmployee(pageRequest);

        // then
        CategoryListResponseDto actual = categoryList.getContent().get(0);

        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
        assertThat(actual.getCategoryName())
            .isEqualTo(categoryDummyStuff.getCategoryName());
        assertThat(actual.getIsHidden())
            .isEqualTo(categoryDummyStuff.getIsHidden());
        assertThat(actual.getParentCategoryNo())
            .isNull();
        assertThat(categoryList.getTotalElements()).isEqualTo(2);

    }


    @DisplayName("부모의 번호를 통해서 자식 들을 모두 잘 찾는다.")
    @Test
    void findCategoryChildListThroughParentCategoryNo() {

        PageRequest pageable = PageRequest.of(0, 10);
        // when
        Page<CategoryListResponseDto> page =
            categoryRepository.findCategoryListAboutChild(
                categoryDummyBook.getCategoryNo(), pageable);

        List<CategoryListResponseDto> categoryList = page.getContent();
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

        PageRequest pageable = PageRequest.of(0, 10);
        // when
        Page<CategoryListResponseDto> page =
            categoryRepository.findCategoryListAboutChild(
                categoryDummyBook.getCategoryNo(), pageable);

        List<CategoryListResponseDto> categoryList = page.getContent();
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

    @DisplayName("메인카테고리인 도서하나를 잘 가져온다.")
    @Test
    void findMainCategoryList() {

        PageRequest pageable = PageRequest.of(0, 10);
        Page<CategoryListResponseDto> page = categoryRepository.findMainCategoryList(
            pageable);

        List<CategoryListResponseDto> mainCategoryList = page.getContent();

        assertThat(mainCategoryList.get(0).getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
        assertThat(mainCategoryList.get(0).getCategoryName())
            .isEqualTo(categoryDummyBook.getCategoryName());
        assertThat(mainCategoryList.get(0).getCount())
            .isNull();
    }

    @DisplayName("시퀀스번호가 2였던 잡화가 자신 시퀀스번호이상부터 1식 시퀀스를 추가하는 동작이 잘 수행된다 자기자신의 번호가 한번호 밀린다.( 3번이 된다.)")
    @Test
    void modifySequence_child() {

        // given
        Integer sequence = categoryDummyStuff.getSequence();
        Category stuff = categoryRepository.findById(categoryDummyStuff.getCategoryNo()).get();
        stuff.setLevel(1);
        testEntityManager.flush();
        testEntityManager.clear();

        // when
        categoryRepository.modifyChildCategorySequence(
            categoryDummyStuff.getParentCategory().getCategoryNo(),
            categoryDummyStuff.getSequence());

        // then
        testEntityManager.flush();
        testEntityManager.clear();
        Category category = categoryRepository.findById(categoryDummyStuff.getCategoryNo()).get();
        assertThat(category.getSequence())
            .isEqualTo(sequence + 1);
    }

    @DisplayName("시퀀스가 1인 책(메인카테고리)의 시퀀스번호 이상부터 1식 증가하여 자기자신 번호가 2가된다.")
    @Test
    void modifySequence_main() {
        // given
        Integer sequence = categoryDummyBook.getSequence();

        // when
        categoryRepository.modifyMainCategorySequence(
            categoryDummyBook.getSequence());

        // then
        testEntityManager.flush();
        testEntityManager.clear();
        Category category = categoryRepository.findById(categoryDummyBook.getCategoryNo()).get();
        assertThat(category.getSequence())
            .isEqualTo(sequence + 1);
    }

    @DisplayName("부모 도서가 하나 자식도서 하나있을때 부모도서가 숨김상태면 자식도서까지 아무것도 조회되지 않는다.")
    @Test
    void findCategoryListByNotEmployee() {
        // given
        PageRequest pageable = PageRequest.of(0, 10);

        // when
        Page<CategoryListResponseDto> page =
            categoryRepository.findCategoryListByNotEmployee(pageable);
        List<CategoryListResponseDto> content = page.getContent();

        // then
        assertThat(content).hasSize(0);
    }

    @DisplayName("해당 대분류안의 소분류로 상품이 등록되었을때 대분류와 상품의 번호가 잘반환된다.")
    @Test
    void getMainCategoryNoAndProductNoForSettingCount() {

        // given when
        List<CategoryNoAndProductNoDto> mainCategoryNoAndProductNoForSettingCount =
            categoryRepository.getMainCategoryNoAndProductNoForSettingCount(
                List.of(categoryDummyBook.getCategoryNo()));

        // then
        CategoryNoAndProductNoDto actual = mainCategoryNoAndProductNoForSettingCount.get(0);
        assertThat(mainCategoryNoAndProductNoForSettingCount)
            .hasSize(1);
        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
        assertThat(actual.getProductNo())
            .isEqualTo(savedProduct1.getProductNo());
    }

    @DisplayName("대분류 카테고리에 해당하는 상품이 존재하는 경우에 잘가져온다.")
    @Test
    void getMainCategoryNoAndProductNoDtoForContainsProducts() {

        // given when
        CategoryNoAndProductNoDto actual =
            categoryRepository.getMainCategoryNoAndProductNoDtoForContainsProducts(
                categoryDummyBook.getCategoryNo());

        // then
        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyBook.getCategoryNo());
    }

    @DisplayName("자식카테고리에 상품이 포함되어있는지 확인하여 포함된 자식카테고리 번호와 상품번호리스트를 잘가져온다.")
    @Test
    void getSubCategoryNoAndProductNoDtoForContainsProducts() {

        // given when
        List<CategoryNoAndProductNoDto> subCategoryNoAndProductNoDtoForContainsProducts =
            categoryRepository.getSubCategoryNoAndProductNoDtoForContainsProducts(
                categoryDummyStuff.getCategoryNo());

        // then
        CategoryNoAndProductNoDto actual = subCategoryNoAndProductNoDtoForContainsProducts.get(0);

        assertThat(subCategoryNoAndProductNoDtoForContainsProducts).hasSize(1);
        assertThat(actual.getProductNo())
            .isEqualTo(savedProduct1.getProductNo());
        assertThat(actual.getCategoryNo())
            .isEqualTo(categoryDummyStuff.getCategoryNo());
    }
}