package shop.itbook.itbookshop.productgroup.productrelationgroup.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductRelationGroupRepositoryTest {

    @Autowired
    ProductRelationGroupRepository productRelationGroupRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    Product dummyProduct1;
    Product dummyProduct2;
    Product dummyProduct3;


    @BeforeEach
    void setUp() {
        dummyProduct1 = ProductDummy.getProductSuccess();
        dummyProduct2 = ProductDummy.getProductSuccess();
        dummyProduct3 = ProductDummy.getProductSuccess();

        productRepository.save(dummyProduct1);
        productRepository.save(dummyProduct2);
        productRepository.save(dummyProduct3);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("연관상품 등록 성공 테스트")
    void save_ProductRelation() {
        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2);
        productRelationGroupRepository.save(productRelationGroup1);

        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3);
        productRelationGroupRepository.save(productRelationGroup2);

        ProductRelationGroup productRelationGroup3 =
            new ProductRelationGroup(null, dummyProduct2, dummyProduct3);
        productRelationGroupRepository.save(productRelationGroup3);

        Assertions.assertThat(productRelationGroupRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("연관상품 삭제 성공 테스트")
    void Delete_ProductCategory_ByProductNo() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2);
        productRelationGroupRepository.save(productRelationGroup1);

        productRelationGroupRepository.deleteByBasedProduct_productNo(dummyProduct1.getProductNo());
        Assertions.assertThat(productRelationGroupRepository.findAll()).isEmpty();
    }

}