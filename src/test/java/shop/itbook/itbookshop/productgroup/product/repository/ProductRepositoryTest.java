package shop.itbook.itbookshop.productgroup.product.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품 레포지토리 crud 테스트입니다.
 *
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

    Product product_success;
    Product product_failure;

    @BeforeEach
    void setUp() {
        product_success = Product.builder().name("객체지향의 사실과 오해")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("다 모르겠고 그냥 제발 됐으면 좋겠네요..").stock(1).isSelled(true).isDeleted(false)
            .isSubscription(false).thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10).rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now()).build();
        product_failure = Product.builder().name("객체지향의 사실과 오해").simpleDescription(null)
            .detailsDescription("좀..").stock(1).isSelled(Boolean.TRUE).isDeleted(Boolean.FALSE)
            .isSubscription(Boolean.FALSE).thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10).rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now()).build();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품 저장 성공 테스트")
    void test1() {
        entityManager.persist(product_success);
        Assertions.assertThatNoException();
    }

    @Test
    @DisplayName("상품 저장 실패 테스트 - notnull 컬럼에 null 값 저장")
    void test2() {
        Assertions.assertThatThrownBy(() -> entityManager.persist(product_failure))
            .isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("상품 번호로 상품 조회 테스트")
    void test3() {
        entityManager.persist(product_success);

        Optional<Product> product =
            productRepository.findById(product_success.getProductNo());

        Assertions.assertThat(product).isPresent();
        Assertions.assertThat(product.get()).isEqualTo(product_success);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void test4() {
        product_success.setName("객체지향의 거짓과 오해");
        entityManager.persist(product_success);
        Assertions.assertThatNoException();
        Assertions.assertThat(product_success.getName()).isEqualTo("객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void test5() {
        entityManager.persist(product_success);

        productRepository.deleteById(product_success.getProductNo());

        Optional<Product> product =
            productRepository.findById(product_success.getProductNo());
        Assertions.assertThat(product).isNotPresent();
    }
}