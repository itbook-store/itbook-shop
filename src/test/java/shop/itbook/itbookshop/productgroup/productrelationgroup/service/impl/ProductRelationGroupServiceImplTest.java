package shop.itbook.itbookshop.productgroup.productrelationgroup.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.impl.ProductServiceImpl;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;
import shop.itbook.itbookshop.productgroup.productrelationgroup.repository.ProductRelationGroupRepository;
import shop.itbook.itbookshop.productgroup.productrelationgroup.service.ProductRelationGroupService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductRelationGroupServiceImpl.class)
class ProductRelationGroupServiceImplTest {
    @Autowired
    ProductRelationGroupService productRelationGroupService;

    @MockBean
    ProductRelationGroupRepository mockProductRelationRepository;
    @MockBean
    ProductRepository mockProductRepository;
    @MockBean
    ProductService mockProductService;
    Product product1;
    Product product2;
    Product product3;

    ProductDetailsResponseDto productDetailsResponseDto1;
    ProductDetailsResponseDto productDetailsResponseDto2;
    ProductDetailsResponseDto productDetailsResponseDto3;
    Pageable pageable;

    @BeforeEach
    void setUp() {
        product1 = ProductDummy.getProductSuccess();
        product1.setProductNo(1L);
        product2 = ProductDummy.getProductSuccess();
        product2.setProductNo(2L);
        product3 = ProductDummy.getProductSuccess();
        product3.setProductNo(3L);
        productDetailsResponseDto1 = ProductDummy.getProductDetailsResponseDto();
        productDetailsResponseDto2 = ProductDummy.getProductDetailsResponseDto();
        productDetailsResponseDto3 = ProductDummy.getProductDetailsResponseDto();
        pageable = PageRequest.of(0, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("연관상품 등록 테스트")
    void addProductRelation() {
        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<Product> productList = new ArrayList<>();
        productList.addAll(Arrays.asList(product2, product3));

        given(mockProductService.findProductEntity(anyLong())).willReturn(product1);
        given(mockProductRepository.findAllById(anyList())).willReturn(productList);

        productRelationGroupService.addProductRelation(product1.getProductNo(), productNoList);

        then(mockProductRelationRepository.save(new ProductRelationGroup(product1, product2)));
        then(mockProductRelationRepository.save(new ProductRelationGroup(product1, product3)));
    }

    @Test
    void findProductExceptBasedProductForAdmin() {

        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        given(mockProductRelationRepository.getProductNoListToAddRelationAdmin(anyLong()))
            .willReturn(productNoList);
        given(mockProductService.findProductListByProductNoListForAdmin(any(Pageable.class),
            anyList()))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> productExceptBasedProductForAdmin =
            productRelationGroupService.findProductExceptBasedProductForAdmin(pageable,
                product1.getProductNo());

        List<ProductDetailsResponseDto> content = productExceptBasedProductForAdmin.getContent();
        Assertions.assertThat(productExceptBasedProductForAdmin).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());

    }

    @Test
    void modifyProductRelation() {
        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<Product> productList = new ArrayList<>();
        productList.addAll(Arrays.asList(product2, product3));

        ProductRelationRequestDto productRelationRequestDto =
            new ProductRelationRequestDto(productNoList);

        given(mockProductService.findProductEntity(anyLong())).willReturn(product1);
        given(mockProductRepository.findAllById(anyList())).willReturn(productList);

        productRelationGroupService.modifyProductRelation(product1.getProductNo(),
            productRelationRequestDto);

        verify(mockProductRelationRepository).deleteByBasedProduct_productNo(
            product1.getProductNo());
        then(mockProductRelationRepository.save(new ProductRelationGroup(product1, product2)));
        then(mockProductRelationRepository.save(new ProductRelationGroup(product1, product3)));
    }

    @Test
    void removeProductRelation() {

        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<Product> productList = new ArrayList<>();
        productList.addAll(Arrays.asList(product2, product3));


        ProductRelationGroup productRelationGroup = mock(ProductRelationGroup.class);

        given(mockProductService.findProductEntity(anyLong())).willReturn(product1);
        given(mockProductRepository.findAllById(anyList())).willReturn(productList);
        given(
            mockProductRelationRepository.findProductRelationGroupByBasedProduct_ProductNoAndProduct_ProductNo(
                anyLong(), anyLong()))
            .willReturn(productRelationGroup);

        productRelationGroupService.removeProductRelation(product1.getProductNo(),
            product2.getProductNo());

        verify(
            mockProductRelationRepository).findProductRelationGroupByBasedProduct_ProductNoAndProduct_ProductNo(
            product1.getProductNo(), product2.getProductNo());
        then(productRelationGroup).should().setIsDeleted(Boolean.TRUE);
        then(mockProductRelationRepository.save(productRelationGroup));
    }

    @Test
    void findProductRelationForAdmin() {
        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        given(mockProductRelationRepository.getRelationProductNoListWithBasedProductNoAdmin(
            anyLong()))
            .willReturn(productNoList);
        given(mockProductService.findProductListByProductNoListForAdmin(any(Pageable.class),
            anyList()))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> productExceptBasedProductForAdmin =
            productRelationGroupService.findProductRelationForAdmin(pageable,
                product1.getProductNo());

        List<ProductDetailsResponseDto> content = productExceptBasedProductForAdmin.getContent();
        Assertions.assertThat(productExceptBasedProductForAdmin).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findAllMainProductRelationForAdmin() {
    }

    @Test
    void findProductRelationForUser() {
        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        given(mockProductRelationRepository.getRelationProductNoListWithBasedProductNoUser(
            anyLong()))
            .willReturn(productNoList);
        given(mockProductService.findProductListByProductNoListForUser(any(Pageable.class),
            anyList()))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> productExceptBasedProductForUser =
            productRelationGroupService.findProductRelationForUser(pageable,
                product1.getProductNo());

        List<ProductDetailsResponseDto> content = productExceptBasedProductForUser.getContent();
        Assertions.assertThat(productExceptBasedProductForUser).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }
}