package shop.itbook.itbookshop.productgroup.productcategory.repository;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;

/**
 * @author 이하늬
 * @since 1.0
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductCategoryRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    TestEntityManager entityManager;
    Product dummyProduct;
    Category dummySubCategory;
    Category dummyMainCategory;
    ProductCategory dummyProductCategory;


    @BeforeEach
    void setUp() {

        dummyProduct = ProductDummy.getProductSuccess();
        dummySubCategory = CategoryDummy.getCategoryHiddenStuff();
        dummyMainCategory = CategoryDummy.getCategoryNoHiddenBook();
        dummyMainCategory.setCategoryName("main");

        Category savedMainCategory = categoryRepository.save(dummyMainCategory);

        dummySubCategory.setCategoryName("sub");
        dummySubCategory.setParentCategory(savedMainCategory);

        Product savedProduct = productRepository.save(dummyProduct);
        Category savedSubCategory = categoryRepository.save(dummySubCategory);

        dummyProductCategory = new ProductCategory(savedProduct, savedSubCategory);
        productCategoryRepository.save(dummyProductCategory);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 번호로 상품 카테고리 삭제 성공 테스트")
    void Delete_ProductCategory_ByProductNo() {
        productCategoryRepository.deleteByPk_productNo(dummyProduct.getProductNo());
        Optional<ProductCategory> productCategory =
            productCategoryRepository.findById(new ProductCategory.Pk(dummyProduct.getProductNo(),
                dummySubCategory.getCategoryNo()));
        Assertions.assertThat(productCategory).isNotPresent();
    }

    @Test
    @DisplayName("카테고리 번호에 해당하는 모든 상품 리스트 조회 성공 테스트")
    void Find_ProductList_ByCategoryNo() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<ProductDetailsResponseDto> productList
            = productCategoryRepository.getProductListWithCategoryNo(pageable,
            dummySubCategory.getCategoryNo()).getContent();
        ProductDetailsResponseDto actual = productList.get(0);

        Assertions.assertThat(productList).hasSize(1);
        Assertions.assertThat(actual.getProductNo()).isEqualTo(dummyProduct.getProductNo());
    }

    @Test
    @DisplayName("상품 번호에 해당하는 모든 카테고리 리스트 조회 성공 테스트")
    void Find_CategoryList_ByProductNo() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Long productNo = dummyProduct.getProductNo();
        List<CategoryDetailsResponseDto> categoryList
            = productCategoryRepository.getCategoryListWithProductNo(pageable, productNo)
            .getContent();
        CategoryDetailsResponseDto actual = categoryList.get(0);

        Assertions.assertThat(categoryList).hasSize(1);
        Assertions.assertThat(actual.getCategoryNo()).isEqualTo(dummySubCategory.getCategoryNo());
    }

}