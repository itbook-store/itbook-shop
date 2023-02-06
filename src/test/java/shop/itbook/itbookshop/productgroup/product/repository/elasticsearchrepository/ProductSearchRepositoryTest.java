package shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
@SpringBootTest
class ProductSearchRepositoryTest {
    @Autowired
    ProductSearchRepository productSearchRepository;

    @MockBean
    BookService bookService;


    private SearchProduct elasticProduct;

    @BeforeEach
    public void setup() {
        elasticProduct = SearchProduct
            .builder()
            .productNo(256L)
            .name("test 테스트북")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명")
            .stock(1)
            .isSelled(true)
            .isDeleted(false)
            .thumbnailUrl("testUrl")
            .fixedPrice(20000L)
            .increasePointPercent(1)
            .discountPercent(10.0)
            .rawPrice(12000L)
            .dailyHits(0L)
            .productCreatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    void productSaveTest() {
        productSearchRepository.save(elasticProduct);
        Optional<SearchProduct> result =
            productSearchRepository.findById(elasticProduct.getProductNo());
        assertThat(result).isPresent();
        assertThat(result.get()).usingRecursiveComparison().isEqualTo(elasticProduct);

    }

    @Test
    void productSearchTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<SearchProduct> searchProducts =
            productSearchRepository.findByName(pageable, "테스트").getContent();
        assertThat(searchProducts).hasSize(1);
    }


    @Test
    void deleteByIdTest() {

        productSearchRepository.deleteById(elasticProduct.getProductNo());

        assertThat(productSearchRepository.findById(elasticProduct.getProductNo())).isEmpty();
    }
}