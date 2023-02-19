package shop.itbook.itbookshop.productgroup.productcategory.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductCategoryServiceImpl.class)
class ProductCategoryServiceImplTest {

    @Autowired
    ProductCategoryService productCategoryService;

    @MockBean
    ProductCategoryRepository mockProductCategoryRepository;

    @MockBean
    CategoryService mockCategoryService;

    @MockBean
    CategoryRepository mockCategoryRepository;

    Product product1;
    Product product2;
    Category category1;
    Category category2;
    Category category3;

    CategoryDetailsResponseDto categoryDetailsResponseDto1;
    CategoryDetailsResponseDto categoryDetailsResponseDto2;
    CategoryDetailsResponseDto categoryDetailsResponseDto3;

    ProductDetailsResponseDto productDetailsResponseDto1;
    ProductDetailsResponseDto productDetailsResponseDto2;

    List<Integer> categoryNoList;
    List<Category> categoryList;

    @BeforeEach
    void setUp() throws IOException {

        product1 = ProductDummy.getProductSuccess();
        product2 = ProductDummy.getProductSuccess();
        category1 = CategoryDummy.getCategoryNoHiddenBook();
        category2 = CategoryDummy.getCategoryNoHiddenBook();
        category3 = CategoryDummy.getCategoryNoHiddenBook();

        given(mockCategoryService.findCategoryEntity(category1.getCategoryNo())).willReturn(
            category1);
        given(mockCategoryService.findCategoryEntity(category2.getCategoryNo())).willReturn(
            category2);
        given(mockCategoryService.findCategoryEntity(category3.getCategoryNo())).willReturn(
            category3);

        categoryNoList = new ArrayList<>();
        categoryNoList.addAll(Arrays.asList(category1.getCategoryNo(), category2.getCategoryNo(),
            category3.getCategoryNo()));

        categoryList = new ArrayList<>();
        categoryList.addAll(Arrays.asList(category1, category2, category3));

        categoryDetailsResponseDto1 = CategoryDetailsResponseDto.builder()
            .categoryNo(category1.getCategoryNo())
            .parentCategoryNo(category1.getCategoryNo())
            .categoryName(category1.getCategoryName())
            .isHidden(category1.getIsHidden())
            .build();

        categoryDetailsResponseDto2 = CategoryDetailsResponseDto.builder()
            .categoryNo(category2.getCategoryNo())
            .parentCategoryNo(category2.getCategoryNo())
            .categoryName(category2.getCategoryName())
            .isHidden(category2.getIsHidden())
            .build();

        categoryDetailsResponseDto3 = CategoryDetailsResponseDto.builder()
            .categoryNo(category3.getCategoryNo())
            .parentCategoryNo(category3.getCategoryNo())
            .categoryName(category3.getCategoryName())
            .isHidden(category3.getIsHidden())
            .build();

        productDetailsResponseDto1 = ProductDummy.getProductDetailsResponseDto();
        ReflectionTestUtils.setField(productDetailsResponseDto1, "productNo",
            product1.getProductNo());
        productDetailsResponseDto2 = ProductDummy.getProductDetailsResponseDto();
        ReflectionTestUtils.setField(productDetailsResponseDto2, "productNo",
            product2.getProductNo());
    }

    @Test
    void addProductCategory() {

        given(mockCategoryRepository.findAllById(any(List.class))).willReturn(categoryList);

        for (Category category : categoryList) {
            given(mockCategoryService.findCategoryEntity(category.getCategoryNo())).willReturn(
                category);
            given(mockProductCategoryRepository.save(any())).willReturn(
                new ProductCategory(product1, category));
        }

        Category parentCategory =
            productCategoryService.addProductCategory(product1, categoryNoList);

        Assertions.assertThat(parentCategory).isEqualTo(categoryList.get(0).getParentCategory());
    }

    @Test
    void modifyProductCategory() {
        given(mockCategoryRepository.findAllById(any(List.class))).willReturn(categoryList);

        for (Category category : categoryList) {
            given(mockCategoryService.findCategoryEntity(category.getCategoryNo())).willReturn(
                category);
            given(mockProductCategoryRepository.save(any())).willReturn(
                new ProductCategory(product1, category));
        }

        productCategoryService.addProductCategory(product1, categoryNoList);

        Category parentCategory2 =
            productCategoryService.modifyProductCategory(product1, categoryNoList);
        then(mockProductCategoryRepository).should().deleteByPk_productNo(product1.getProductNo());
        Assertions.assertThat(parentCategory2).isEqualTo(categoryList.get(0).getParentCategory());
    }

    @Test
    void removeProductCategory() {
        productCategoryService.removeProductCategory(product1.getProductNo());
        then(mockProductCategoryRepository).should().deleteByPk_productNo(product1.getProductNo());
    }

//    @Test
//    void findCategoryList() {
//
//        given(mockProductCategoryRepository.getCategoryListWithProductNo(any(Pageable.class),
//            anyLong())).willReturn(
//            new PageImpl<>(List.of(categoryDetailsResponseDto1, categoryDetailsResponseDto2,
//                categoryDetailsResponseDto3)));
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        Page<CategoryDetailsResponseDto> page =
//            productCategoryService.findCategoryList(pageRequest, product1.getProductNo());
//
//        List<CategoryDetailsResponseDto> categoryList = page.getContent();
//
//        Assertions.assertThat(categoryList).hasSize(3);
//        Assertions.assertThat(categoryList.get(0).getCategoryNo())
//            .isEqualTo(category1.getCategoryNo());
//        Assertions.assertThat(categoryList.get(0).getCategoryName())
//            .isEqualTo(category1.getCategoryName());
//    }
//
//    @Test
//    void findProductList() {
//        given(mockProductCategoryRepository.getProductListWithCategoryNo(any(Pageable.class),
//            anyInt())).willReturn(
//            new PageImpl<>(List.of(productDetailsResponseDto1, productDetailsResponseDto2)));
//
//        PageRequest pageRequest = PageRequest.of(0, 10);
//
//        Page<ProductDetailsResponseDto> page =
//            productCategoryService.findProductList(pageRequest, category1.getCategoryNo());
//
//        List<ProductDetailsResponseDto> productList = page.getContent();
//
//
//        Assertions.assertThat(productList).hasSize(2);
//        Assertions.assertThat(productList.get(0).getProductNo())
//            .isEqualTo(product1.getProductNo());
//        Assertions.assertThat(productList.get(0).getProductName())
//            .isEqualTo(product1.getName());
//    }
}