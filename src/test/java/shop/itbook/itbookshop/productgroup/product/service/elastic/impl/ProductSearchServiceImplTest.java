package shop.itbook.itbookshop.productgroup.product.service.elastic.impl;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository.ProductSearchRepository;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * @author ?????????
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductSearchServiceImpl.class)
class ProductSearchServiceImplTest {

    @Autowired
    ProductSearchService productSearchService;

    @MockBean
    ProductSearchRepository mockProductSearchRepository;

    Product product;
    SearchProduct elasticProduct;


    @BeforeEach
    void setUp() {
        product = Product.builder().name("test ????????????")
            .simpleDescription("?????????????????? ????????????? ??? ?????? ??? ????????? ?????? ?????? ?????? ?????? ???????????? ?????? ?????? ???????????? ?????? ?????????.")
            .detailsDescription("?????? ??????").stock(1).isSelled(true).isForceSoldOut(false)
            .thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1.0).discountPercent(10.0).rawPrice(12000L)
            .productCreatedAt(LocalDateTime.now()).build();
        product.setProductNo(256L);
        elasticProduct = SearchProduct.builder()
            .productNo(256L).name("test ????????????")
            .simpleDescription("?????????????????? ????????????? ??? ?????? ??? ????????? ?????? ?????? ?????? ?????? ???????????? ?????? ?????? ???????????? ?????? ?????????.")
            .detailsDescription("?????? ??????").stock(1).isSelled(true)
            .isForceSoldOut(false).thumbnailUrl("testUrl").fixedPrice(20000L)
            .increasePointPercent(1.0).discountPercent(10.0)
            .rawPrice(12000L)
            .build();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void addSearchProductTest() {
        given(mockProductSearchRepository.save(any(SearchProduct.class)))
            .willReturn(elasticProduct);

        assertThat(productSearchService.addSearchProduct(product)).isEqualTo(
            elasticProduct.getProductNo());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void modifyProductTest() {
        Product product = mock(Product.class);
        given(mockProductSearchRepository.save(any())).willReturn(elasticProduct);

        productSearchService.modifySearchProduct(product);

        verify(mockProductSearchRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void removeProductTests() {
        productSearchService.removeSearchProduct(1L);

        then(mockProductSearchRepository).should().deleteById(1L);
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void findProductTest() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(List.of(elasticProduct, elasticProduct), pageRequest, 10);

        given(mockProductSearchRepository.findByName(any(), anyString())).willReturn(page);

        Page<SearchProduct> results =
            mockProductSearchRepository.findByName(Pageable.unpaged(), "test");

        assertThat(
            productSearchService.searchProductByTitle(Pageable.unpaged(), "test").getContent())
            .usingRecursiveComparison()
            .isEqualTo(List.of(documentToDto(elasticProduct), documentToDto(elasticProduct)));

    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void findProductsListTest() {

        List<SearchProduct> productList = List.of(elasticProduct, elasticProduct);

        given(mockProductSearchRepository.findByName(anyString())).willReturn(productList);

        List<ProductSearchResponseDto> results =
            productSearchService.searchProductsByTitle("test");

        assertThat(results.get(0).getProductNo())
            .isEqualTo(documentToDto(elasticProduct).getProductNo());

    }

}