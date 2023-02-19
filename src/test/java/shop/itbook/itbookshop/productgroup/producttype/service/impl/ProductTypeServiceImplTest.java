package shop.itbook.itbookshop.productgroup.producttype.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.exception.ProductTypeNotFoundException;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;
import shop.itbook.itbookshop.productgroup.producttyperegistration.repository.ProductTypeRegistrationRepository;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.ProductTypeRegistrationService;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.impl.ProductTypeRegistrationServiceImpl;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductTypeServiceImpl.class)
class ProductTypeServiceImplTest {

    @Autowired
    ProductTypeService productTypeService;

    @MockBean
    ProductTypeRepository mockProductTypeRepository;
    @MockBean
    ProductService mockProductService;
    @MockBean
    ProductTypeRegistrationService mockProductTypeRegistrationService;

    ProductTypeResponseDto productTypeResponseDto1;
    ProductTypeResponseDto productTypeResponseDto2;
    ProductTypeResponseDto productTypeResponseDto3;
    ProductTypeResponseDto productTypeResponseDto4;
    ProductTypeResponseDto productTypeResponseDto5;
    List<ProductTypeResponseDto> productTypeList;
    Pageable pageable;
    Product product1;
    Product product2;
    Product product3;

    ProductDetailsResponseDto productDetailsResponseDto1;
    ProductDetailsResponseDto productDetailsResponseDto2;
    ProductDetailsResponseDto productDetailsResponseDto3;

    @BeforeEach
    void setUp() {
        productTypeResponseDto1 = new ProductTypeResponseDto(1, "신간");
        productTypeResponseDto2 = new ProductTypeResponseDto(2, "할인");
        productTypeResponseDto3 = new ProductTypeResponseDto(3, "베스트셀러");
        productTypeResponseDto4 = new ProductTypeResponseDto(4, "추천");
        productTypeResponseDto5 = new ProductTypeResponseDto(5, "인기");

        pageable = PageRequest.of(0, Integer.MAX_VALUE);

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

        productTypeList = new ArrayList<>();
        productTypeList.addAll(
            Arrays.asList(productTypeResponseDto1, productTypeResponseDto2, productTypeResponseDto3,
                productTypeResponseDto4, productTypeResponseDto5));

    }

    @Test
    void findProductTypeList() {

        given(mockProductTypeRepository.findProductTypeList(
            any(Pageable.class))).willReturn(new PageImpl<>(productTypeList));

        Page<ProductTypeResponseDto> productList =
            productTypeService.findProductTypeList(pageable);

        List<ProductTypeResponseDto> content = productList.getContent();
        Assertions.assertThat(productList).isNotEmpty().hasSize(5);
        Assertions.assertThat(content.get(0).getProductTypeNo())
            .isEqualTo(productTypeResponseDto1.getProductTypeNo());
    }

    @Test
    void findProductType_success() {
        ProductType productType = new ProductType(1, ProductTypeEnum.DISCOUNT);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));

        ProductType actualProductType = productTypeService.findProductType(1);

        Assertions.assertThat(actualProductType).isNotNull();
        Assertions.assertThat(actualProductType.getProductTypeNo())
            .isEqualTo(productType.getProductTypeNo());
        Assertions.assertThat(actualProductType.getProductTypeEnum())
            .isEqualTo(productType.getProductTypeEnum());
    }

    @Test
    void findProductType_failure() {
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.empty());

        assertThatThrownBy(() -> productTypeService.findProductType(1))
            .isInstanceOf(ProductTypeNotFoundException.class)
            .hasMessage(ProductTypeNotFoundException.MESSAGE);
    }

    @Test
    void findProductListByProductTypeNoForUser_NewIssue() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(1, ProductTypeEnum.NEW_ISSUE);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findNewBookListUser(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> newBooks =
            productTypeService.findProductListByProductTypeNoForUser(pageable, 1, memberNo);

        List<ProductDetailsResponseDto> content = newBooks.getContent();
        Assertions.assertThat(newBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForAdmin_NewIssue() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(1, ProductTypeEnum.NEW_ISSUE);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findNewBookListAdmin(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> newBooks =
            productTypeService.findProductListByProductTypeNoForAdmin(pageable, 1, memberNo);

        List<ProductDetailsResponseDto> content = newBooks.getContent();
        Assertions.assertThat(newBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForUser_Discount() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(2, ProductTypeEnum.DISCOUNT);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findDiscountBookListUser(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> discountBooks =
            productTypeService.findProductListByProductTypeNoForUser(pageable, 2, memberNo);

        List<ProductDetailsResponseDto> content = discountBooks.getContent();
        Assertions.assertThat(discountBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForAdmin_Discount() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(2, ProductTypeEnum.DISCOUNT);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findDiscountBookListAdmin(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> discountBooks =
            productTypeService.findProductListByProductTypeNoForAdmin(pageable, 2, memberNo);

        List<ProductDetailsResponseDto> content = discountBooks.getContent();
        Assertions.assertThat(discountBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForUser_BestSeller() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(3, ProductTypeEnum.BESTSELLER);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findBestSellerBookListUser(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> bestSellerBooks =
            productTypeService.findProductListByProductTypeNoForUser(pageable, 3, memberNo);

        List<ProductDetailsResponseDto> content = bestSellerBooks.getContent();
        Assertions.assertThat(bestSellerBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForAdmin_BestSeller() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(3, ProductTypeEnum.BESTSELLER);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findBestSellerBookListAdmin(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> bestSellerBooks =
            productTypeService.findProductListByProductTypeNoForAdmin(pageable, 3, memberNo);

        List<ProductDetailsResponseDto> content = bestSellerBooks.getContent();
        Assertions.assertThat(bestSellerBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForUser_Popularity() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(5, ProductTypeEnum.POPULARITY);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findPopularityBookListUser(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> popularityBooks =
            productTypeService.findProductListByProductTypeNoForUser(pageable, 5, memberNo);

        List<ProductDetailsResponseDto> content = popularityBooks.getContent();
        Assertions.assertThat(popularityBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForAdmin_Popularity() {
        Long memberNo = 1L;

        List<ProductDetailsResponseDto> productDetailsList = new ArrayList<>();
        productDetailsList.addAll(
            Arrays.asList(productDetailsResponseDto2, productDetailsResponseDto3));

        ProductType productType = new ProductType(5, ProductTypeEnum.POPULARITY);
        given(mockProductTypeRepository.findById(anyInt())).willReturn(Optional.of(productType));
        given(mockProductTypeRepository.findPopularityBookListAdmin(any(Pageable.class)))
            .willReturn(new PageImpl<>(productDetailsList));

        Page<ProductDetailsResponseDto> popularityBooks =
            productTypeService.findProductListByProductTypeNoForAdmin(pageable, 5, memberNo);

        List<ProductDetailsResponseDto> content = popularityBooks.getContent();
        Assertions.assertThat(popularityBooks).isNotEmpty().hasSize(2);
        Assertions.assertThat(content.get(0).getProductNo())
            .isEqualTo(productDetailsResponseDto2.getProductNo());
    }

    @Test
    void findProductListByProductTypeNoForAdmin() {
    }
}