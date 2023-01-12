package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import java.time.LocalDateTime;
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
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
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
    ProductType productType1;
    ProductType productType2;
    Product actualProduct;
    ProductType actualProductType1;
    ProductType actualProductType2;

    @BeforeEach
    void setUp() {
        product = Product.builder().name("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명").stock(1).isSelled(true).isDeleted(false)
            .thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10.0).rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now()).build();
        productType1 = new ProductType(null, ProductTypeEnum.BESTSELLER);
        productType2 = new ProductType(null, ProductTypeEnum.NEW_ISSUE);
        productRepository.save(product);
        productTypeRepository.save(productType1);
        productTypeRepository.save(productType2);
        actualProduct = productRepository.findById(product.getProductNo()).get();
        actualProductType1 =
            productTypeRepository.findById(productType1.getProductTypeNo()).get();
        actualProductType2 =
            productTypeRepository.findById(productType2.getProductTypeNo()).get();

    }

    @Test
    @DisplayName("상품유형 등록 성공")
    void addProductTypeRegistrationTest() {

        ProductTypeRegistration productTypeRegistration =
            new ProductTypeRegistration(actualProduct, actualProductType1);
        productTypeRegistrationRepository.save(productTypeRegistration);

        Optional<ProductTypeRegistration> optionalProductTypeRegistration =
            productTypeRegistrationRepository.findById(productTypeRegistration.getPk());

        Assertions.assertThat(optionalProductTypeRegistration).isPresent();
        Assertions.assertThat(optionalProductTypeRegistration.get().getProduct().getProductNo())
            .isEqualTo(actualProduct.getProductNo());
        Assertions.assertThat(
                optionalProductTypeRegistration.get().getProductType().getProductTypeNo())
            .isEqualTo(actualProductType1.getProductTypeNo());
    }

    @Test
    @DisplayName("상품 번호로 상품유형 리스트 조회 성공")
    void findProductTypeListByProductNoTest() {

        ProductTypeRegistration productTypeRegistration1 =
            new ProductTypeRegistration(actualProduct, actualProductType1);
        productTypeRegistrationRepository.save(productTypeRegistration1);
        ProductTypeRegistration productTypeRegistration2 =
            new ProductTypeRegistration(actualProduct, actualProductType2);
        productTypeRegistrationRepository.save(productTypeRegistration2);


        List<FindProductTypeResponseDto> productTypeListByProductNo =
            productTypeRegistrationRepository.findProductTypeListWithProductNo(
                actualProduct.getProductNo());

        Assertions.assertThat(productTypeListByProductNo.size()).isEqualTo(2);
        Assertions.assertThat(productTypeListByProductNo.get(0).getProductTypeName())
            .isEqualTo(actualProductType1.getProductTypeEnum());
    }

    @Test
    @DisplayName("상품 유형 번호로 상품 조회 성공")
    void findProductListByProductTypeNoTest() {

        ProductTypeRegistration productTypeRegistration =
            new ProductTypeRegistration(actualProduct, actualProductType1);
        productTypeRegistrationRepository.save(productTypeRegistration);

        List<FindProductResponseDto> productListByProductTypeNo =
            productTypeRegistrationRepository.findProductListWithProductTypeNo(
                actualProductType1.getProductTypeNo());

        Assertions.assertThat(productListByProductTypeNo.get(0).getProduct().getProductNo())
            .isEqualTo(actualProduct.getProductNo());
    }
}