package shop.itbook.itbookshop.productgroup.productcategory.repository;

import static org.junit.jupiter.api.Assertions.*;

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
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
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
    Category dummyCategory;
    ProductCategory dummyProductCategory;

    @BeforeEach
    void setUp() {

        dummyProduct = ProductDummy.getProductSuccess();
        dummyCategory = CategoryDummy.getCategoryNoHiddenBook();
        dummyProductCategory = new ProductCategory(dummyProduct, dummyCategory);
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
                dummyCategory.getCategoryNo()));
        Assertions.assertThat(productCategory).isNotPresent();

    }

}