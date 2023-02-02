package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TestEntityManager entityManager;

    Product dummyProductSuccess1;
    Product dummyProductSuccess2;

    @BeforeEach
    void setUp() {
        dummyProductSuccess1 = ProductDummy.getProductSuccess();
        dummyProductSuccess2 = ProductDummy.getProductSuccess();
        dummyProductSuccess2.setName("객체지향의 거짓과 오해");

        productRepository.save(dummyProductSuccess1);
        productRepository.save(dummyProductSuccess2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 번호로 상품 조회 성공 테스트")
    void Find_product_ByProductNo() {

        Optional<Product> product =
            productRepository.findById(dummyProductSuccess1.getProductNo());

        Assertions.assertThat(product).isPresent();
        Assertions.assertThat(product.get().getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void Modify_Product() {
        dummyProductSuccess1.setName("객체지향의 거짓과 오해");
        productRepository.save(dummyProductSuccess1);
        Assertions.assertThatNoException();
        Assertions.assertThat(dummyProductSuccess1.getName()).isEqualTo("객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void delete_Product_ByProductNo() {
        productRepository.deleteById(dummyProductSuccess1.getProductNo());
        Optional<Product> product =
            productRepository.findById(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(product).isNotPresent();
    }

    @Test
    @DisplayName("모든 상품 리스트 조회 성공 테스트")
    void find_ProductList() {

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListUser(pageable);
        Assertions.assertThat(productList).isNotEmpty();
        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);

        Assertions.assertThat(productList).hasSize(1);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProductSuccess1.getIsSelled());
    }

    @Test
    @DisplayName("상품 번호 리스트로 상품 상세 리스트 조회 성공 테스트")
    void find_ProductList_By_ProductNoList() {
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
        List<Long> productNoList = new ArrayList<>();
        productNoList.add(dummyProductSuccess1.getProductNo());
        productNoList.add(dummyProductSuccess2.getProductNo());

        Page<ProductDetailsResponseDto> productList =
            productRepository.findProductListByProductNoList(pageable, productNoList);
        Assertions.assertThat(productList).isNotEmpty();
        Assertions.assertThat(productList).hasSize(2);
        
        ProductDetailsResponseDto productDetailsResponseDtoActual = productList.getContent().get(0);
        Assertions.assertThat(productDetailsResponseDtoActual.getProductNo())
            .isEqualTo(dummyProductSuccess1.getProductNo());
        Assertions.assertThat(productDetailsResponseDtoActual.getIsSelled())
            .isEqualTo(dummyProductSuccess1.getIsSelled());
    }
}