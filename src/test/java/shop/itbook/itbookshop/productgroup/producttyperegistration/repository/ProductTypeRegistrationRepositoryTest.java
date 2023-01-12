package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductTypeRegistrationRepositoryTest {

    @Autowired
    ProductTypeRegistrationRepository productTypeRegistrationRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    @Autowired
    TestEntityManager entityManager;

    Product product;
    ProductType productType;

    @BeforeEach
    void setUp() {
        product = Product.builder().name("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명").stock(1).isSelled(true).isDeleted(false)
            .thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10).rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now()).build();
        productType = new ProductType(null, ProductTypeEnum.BESTSELLER);
    }

    @Test
    @DisplayName("상품유형 등록 성공")
    void addProductTypeRegistrationTest() {

        productRepository.save(product);
        productTypeRepository.save(productType);

        Product actualProduct = productRepository.findById(product.getProductNo()).get();
        ProductType actualProductType =
            productTypeRepository.findById(productType.getProductTypeNo()).get();

        ProductTypeRegistration productTypeRegistration =
            new ProductTypeRegistration(actualProduct, actualProductType);
        productTypeRegistrationRepository.save(productTypeRegistration);

        Optional<ProductTypeRegistration> optionalProductTypeRegistration =
            productTypeRegistrationRepository.findById(productTypeRegistration.getPk());

        Assertions.assertThat(optionalProductTypeRegistration).isPresent();
        Assertions.assertThat(optionalProductTypeRegistration.get().getProduct().getProductNo())
            .isEqualTo(actualProduct.getProductNo());
        Assertions.assertThat(
                optionalProductTypeRegistration.get().getProductType().getProductTypeNo())
            .isEqualTo(actualProductType.getProductTypeNo());
    }

    @Test
    @DisplayName("모든 상품유형 조회 성공")
    void productTypeFindTest() {

    }
}