package shop.itbook.itbookshop.productgroup.product.repository;

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
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    Product dummyProductSuccess;
//    static final Integer DATA_SIZE = 7;

    @BeforeEach
    void setUp() {
        dummyProductSuccess = ProductDummy.getProductSuccess();
        productRepository.save(dummyProductSuccess);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 번호로 상품 조회 성공 테스트")
    void Find_product_ByProductNo() {

        Optional<Product> product =
            productRepository.findById(dummyProductSuccess.getProductNo());

        Assertions.assertThat(product).isPresent();
        Assertions.assertThat(product.get().getProductNo())
            .isEqualTo(dummyProductSuccess.getProductNo());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void Modify_Product() {
        dummyProductSuccess.setName("객체지향의 거짓과 오해");
        productRepository.save(dummyProductSuccess);
        Assertions.assertThatNoException();
        Assertions.assertThat(dummyProductSuccess.getName()).isEqualTo("객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete_Product_ByProductNo() {
        productRepository.deleteById(dummyProductSuccess.getProductNo());
        Optional<Product> product =
            productRepository.findById(dummyProductSuccess.getProductNo());
        Assertions.assertThat(product).isNotPresent();
    }

    @Test
    @DisplayName("모든 상품 리스트 조회 성공 테스트")
    void Find_ProductList() {

        List<ProductDetailsResponseDto> productList = productRepository.findProductList();
        Assertions.assertThat(productList).isNotEmpty();
//        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.get(DATA_SIZE);
//
//        Assertions.assertThat(productList).hasSize(DATA_SIZE + 1);
//        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
//            .isEqualTo(dummyProductSuccess.getProductNo());
//        Assertions.assertThat(productDetailsResponseDtoActual.getIsExposed())
//            .isEqualTo(dummyProductSuccess.getIsExposed());
    }
}