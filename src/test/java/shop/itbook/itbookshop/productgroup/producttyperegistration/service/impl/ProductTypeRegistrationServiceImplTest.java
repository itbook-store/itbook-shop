package shop.itbook.itbookshop.productgroup.producttyperegistration.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.producttyperegistration.exception.ProductTypeRegistrationNotFoundException;
import shop.itbook.itbookshop.productgroup.producttyperegistration.repository.ProductTypeRegistrationRepository;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.ProductTypeRegistrationService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductTypeRegistrationServiceImpl.class)
class ProductTypeRegistrationServiceImplTest {

    @Autowired
    ProductTypeRegistrationService productTypeRegistrationService;

    @MockBean
    ProductTypeRegistrationRepository mockProductTypeRegistrationRepository;

    Product product1;
    Product product2;
    Product product3;

    ProductDetailsResponseDto productDetailsResponseDto1;
    ProductDetailsResponseDto productDetailsResponseDto2;
    ProductDetailsResponseDto productDetailsResponseDto3;

    @BeforeEach
    void setUp() {
        product1 = ProductDummy.getProductSuccess();
        product2 = ProductDummy.getProductSuccess();
        product3 = ProductDummy.getProductSuccess();

        productDetailsResponseDto1 = ProductDummy.getProductDetailsResponseDto();
        ReflectionTestUtils.setField(productDetailsResponseDto1, "productNo",
            product1.getProductNo());
        productDetailsResponseDto2 = ProductDummy.getProductDetailsResponseDto();
        ReflectionTestUtils.setField(productDetailsResponseDto2, "productNo",
            product2.getProductNo());
        productDetailsResponseDto3 = ProductDummy.getProductDetailsResponseDto();
        ReflectionTestUtils.setField(productDetailsResponseDto2, "productNo",
            product3.getProductNo());
    }

    @Test
    void findProductTypeNameList() {

    }

    @Test
    void findProductList() {
        List<Long> productNoList = new ArrayList<>();
        productNoList.addAll(Arrays.asList(2L, 3L));
        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        given(mockProductTypeRegistrationRepository.findProductListAdminWithProductTypeNo(
            any(Pageable.class),
            anyInt())).willReturn(new PageImpl<>(productDetailsList));

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);


        Page<ProductDetailsResponseDto> productList =
            productTypeRegistrationService.findProductList(pageable, 1);

        List<ProductDetailsResponseDto> content = productList.getContent();
        Assertions.assertThat(productList).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    @DisplayName("상품유형으로 상품리스트 조회 시 실패 테스트 - 상품이 없을 시 예외 발생")
    void findProductList_failure() {
        given(mockProductTypeRegistrationRepository.findProductListAdminWithProductTypeNo(
            any(Pageable.class), anyInt())).willReturn(null);
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Assertions.assertThatThrownBy(
                () -> productTypeRegistrationService.findProductList(pageable, 1))
            .isInstanceOf(ProductTypeRegistrationNotFoundException.class)
            .hasMessage(ProductTypeRegistrationNotFoundException.MESSAGE);
    }

    @Test
    @DisplayName("상품번호로 상품유형리스트 조회 시 실패 테스트 - 상품유형이 없을 시 예외 발생")
    void findProductTypeNaeList_failure() {
        given(mockProductTypeRegistrationRepository.findProductTypeListWithProductNo(
            any(Pageable.class), anyLong())).willReturn(null);
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);

        Assertions.assertThatThrownBy(
                () -> productTypeRegistrationService.findProductTypeNameList(pageable, 1L))
            .isInstanceOf(ProductTypeRegistrationNotFoundException.class)
            .hasMessage(ProductTypeRegistrationNotFoundException.MESSAGE);
    }
}