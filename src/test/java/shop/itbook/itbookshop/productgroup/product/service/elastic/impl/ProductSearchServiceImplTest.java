package shop.itbook.itbookshop.productgroup.product.service.elastic.impl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static shop.itbook.itbookshop.productgroup.product.transfer.SearchProductTransfer.documentToDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository.ProductSearchRepository;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * @author 송다혜
 * @since 1.0
 */
@SpringBootTest
class ProductSearchServiceImplTest {

    @Autowired
    ProductSearchService productSearchService;

    @MockBean
    ProductSearchRepository mockProductSearchRepository;

    Product product;
    SearchProduct elasticProduct;


    @BeforeEach
    void setUp() {
        product = Product.builder().name("test 테스트북")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명").stock(1).isSelled(true).isDeleted(false)
            .thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10.0).rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now()).build();
        product.setProductNo(256L);
        elasticProduct = SearchProduct.builder()
            .productNo(256L).name("test 테스트북")
            .simpleDescription("객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.")
            .detailsDescription("상세 설명").stock(1).isSelled(true)
            .isDeleted(false).thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1).discountPercent(10.0)
            .rawPrice(12000L).dailyHits(0L)
            .productCreatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("상품 검색 등록 테스트")
    void addSearchProductTest() {
        given(mockProductSearchRepository.save(any(SearchProduct.class)))
            .willReturn(elasticProduct);

        assertThat(productSearchService.addSearchProduct(product)).isEqualTo(elasticProduct.getProductNo());
    }

    @Test
    @DisplayName("상품 검색 수정 테스트")
    void modifyProductTest() {
        Product product = mock(Product.class);
        given(mockProductSearchRepository.save(any())).willReturn(elasticProduct);

        productSearchService.modifySearchProduct(product);

        verify(mockProductSearchRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void removeProductTests() {
        productSearchService.removeSearchProduct(1L);

        then(mockProductSearchRepository).should().deleteById(1L);
    }

    @Test
    @DisplayName("상품 이름 검색 테스트")
    void findProductTest() {

        given(mockProductSearchRepository.findByName(anyString())).willReturn(List.of(elasticProduct,elasticProduct));

        List<SearchProduct> results = mockProductSearchRepository.findByName("test");

        assertThat(productSearchService.searchProductByTitle("test"))
            .usingRecursiveComparison().isEqualTo(List.of(documentToDto(elasticProduct),documentToDto(elasticProduct)));

    }

    @Test
    @DisplayName("상품 검색 시 실패 테스트 - 상품이 없을 시 예외 발생")
    void findProductTest_failure() {
        given(mockProductSearchRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> productSearchService.searchProductByTitle("test"))
            .isInstanceOf(SearchProductNotFoundException.class)
            .hasMessage(SearchProductNotFoundException.MESSAGE);
    }
}