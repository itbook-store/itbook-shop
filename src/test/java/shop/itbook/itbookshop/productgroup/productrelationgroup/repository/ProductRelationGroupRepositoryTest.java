package shop.itbook.itbookshop.productgroup.productrelationgroup.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;
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
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);

        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup2);

        ProductRelationGroup productRelationGroup3 =
            new ProductRelationGroup(null, dummyProduct2, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup3);

        Assertions.assertThat(productRelationGroupRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("기준 상품의 상품 번호로 연관상품 삭제 성공 테스트")
    void Delete_ProductCategory_ByBasedProduct_ProductNo() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);
        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup2);

        productRelationGroupRepository.deleteByBasedProduct_productNo(dummyProduct1.getProductNo());
        Assertions.assertThat(productRelationGroupRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("기준 상품과 해당 상품의 번호로 상품 번호로 연관상품 삭제 성공 테스트")
    void Delete_ProductCategory_ByProductNo() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);

        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup2);

        ProductRelationGroup productRelationGroup3 =
            new ProductRelationGroup(null, dummyProduct2, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup3);

        productRelationGroup2.setIsDeleted(Boolean.TRUE);
        productRelationGroupRepository.save(productRelationGroup2);

        productRelationGroup3.setIsDeleted(Boolean.TRUE);
        productRelationGroupRepository.save(productRelationGroup3);

        Assertions.assertThat(
            productRelationGroupRepository.getRelationProductNoListWithBasedProductNoUser(
                dummyProduct1.getProductNo())).hasSize(1);
        Assertions.assertThat(
            productRelationGroupRepository.getRelationProductNoListWithBasedProductNoUser(
                dummyProduct2.getProductNo())).isEmpty();
    }

    @Test
    @DisplayName("기준 상품으로 연관상품 리스트 조회 테스트")
    void Find_RelationProductList_ByProductNo() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);

        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup2);

        ProductRelationGroup productRelationGroup3 =
            new ProductRelationGroup(null, dummyProduct2, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup3);

        Assertions.assertThat(
            productRelationGroupRepository.getRelationProductNoListWithBasedProductNoAdmin(
                dummyProduct1.getProductNo())).hasSize(2);
        Assertions.assertThat(
            productRelationGroupRepository.getRelationProductNoListWithBasedProductNoAdmin(
                dummyProduct2.getProductNo())).hasSize(1);

    }

    @Test
    @DisplayName("기준 상품으로 연관상품 조회 테스트")
    void Find_RelationProduct_ByProductNo() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);

        Long productRelationNo =
            productRelationGroupRepository.findProductRelationGroupByBasedProduct_ProductNoAndProduct_ProductNo(
                    dummyProduct1.getProductNo(), dummyProduct2.getProductNo())
                .getProductRelationGroupNo();

        Assertions.assertThat(productRelationNo)
            .isEqualTo(productRelationGroup1.getProductRelationGroupNo());

    }

    @Test
    @DisplayName("기준 상품으로 자신을 제외한 모든 상품 조회 테스트")
    void Find_Product_Except_BasedProduct_ByProductNo() {

        List<Long> productNoList =
            productRelationGroupRepository.getProductNoListToAddRelationAdmin(
                dummyProduct1.getProductNo());

        Assertions.assertThat(productNoList)
            .hasSize(2);
        Assertions.assertThat(dummyProduct1.getProductNo()).isNotIn(productNoList);
        Assertions.assertThat(dummyProduct2.getProductNo()).isIn(productNoList);
        Assertions.assertThat(dummyProduct3.getProductNo()).isIn(productNoList);

    }

    @Test
    @DisplayName("모든 상위 연관 상품 번호 조회 테스트")
    void Find_Based_Product_All() {

        ProductRelationGroup productRelationGroup1 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct2, false);
        productRelationGroupRepository.save(productRelationGroup1);

        ProductRelationGroup productRelationGroup2 =
            new ProductRelationGroup(null, dummyProduct1, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup2);

        ProductRelationGroup productRelationGroup3 =
            new ProductRelationGroup(null, dummyProduct2, dummyProduct3, false);
        productRelationGroupRepository.save(productRelationGroup3);

        List<ProductRelationResponseDto> productNoList =
            productRelationGroupRepository.getAllBasedProductNoListAdmin(
                PageRequest.of(0, Integer.MAX_VALUE)).getContent();

        Assertions.assertThat(productNoList.get(0).getProductNo())
            .isEqualTo(dummyProduct1.getProductNo());
        Assertions.assertThat(productNoList.get(0).getProductRelationCount()).isEqualTo(2);

    }

}