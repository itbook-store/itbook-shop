package shop.itbook.itbookshop.productgroup.product.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.io.FileInputStream;
import java.io.IOException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.ProductTypeRegistrationService;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductServiceImpl.class)
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    BookService mockBookService;

    @MockBean
    MemberRoleService memberRoleService;

    @MockBean
    CategoryService mockCategoryService;

    @MockBean
    ProductTypeService mockProductTypeService;

    @MockBean
    ProductTypeRegistrationService mockProductTypeRegistrationService;

    @MockBean
    ProductCategoryService mockProductCategoryService;

    @MockBean
    FileService mockFileService;

    @MockBean
    ProductRepository mockProductRepository;

    ProductAddRequestDto productAddRequestDto;
    ProductModifyRequestDto productModifyRequestDto;

    MockMultipartFile mockImageFile;
    MockMultipartFile mockPdfFile;

    @BeforeEach
    void setUp() throws IOException {
        String imageContentType = "image/png";
        String pdfContentType = "application/pdf";
        String path = "src/test/resources/";

        mockImageFile = new MockMultipartFile("image", "test.png", imageContentType,
            new FileInputStream(path + "test.png"));
        mockPdfFile = new MockMultipartFile("pdf", "test.pdf", pdfContentType,
            new FileInputStream(path + "test.pdf"));

        productAddRequestDto = ProductDummy.getProductRequest();
        productModifyRequestDto = ProductDummy.getProductModifyRequest();
        ReflectionTestUtils.setField(productModifyRequestDto, "productName", "객체지향의 거짓과 오해");
    }

    @Test
    void addProductTest_success() {
        Product product = ProductTransfer.dtoToEntityAdd(productAddRequestDto);
        given(mockProductCategoryService.addProductCategory(any(Product.class), anyList()))
            .willReturn(CategoryDummy.getCategoryNoHiddenBook());
        given(mockProductRepository.save(any(Product.class)))
            .willReturn(product);

        Long actual = productService.addProduct(productAddRequestDto, mockImageFile);

        Assertions.assertThat(actual).isEqualTo(product.getProductNo());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void modifyProductTest_success() {
        Product product = mock(Product.class);
        given(mockProductRepository.findById(anyLong()))
            .willReturn(Optional.of(product));
        given(mockProductCategoryService.modifyProductCategory(any(Product.class), anyList()))
            .willReturn(CategoryDummy.getCategoryNoHiddenBook());
        given(mockProductRepository.save(any(Product.class))).willReturn(product);

        productService.modifyProduct(1L, productModifyRequestDto, mockImageFile);

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setName(anyString());
        then(product).should().setStock(anyInt());
        then(product).should().setRawPrice(anyLong());
    }

    @Test
    @DisplayName("상품 삭제여부 수정 테스트")
    void changeIsDeletedTest_success() {
        Product product = mock(Product.class);
        given(mockProductRepository.findById(anyLong()))
            .willReturn(Optional.of(product));
        given(mockProductRepository.save(any(Product.class))).willReturn(product);

        productService.changeBooleanField(1L, "delete");

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setIsDeleted(anyBoolean());
        then(mockProductRepository).should().save(product);
    }

    @Test
    @DisplayName("상품 강제품절여부 수정 테스트")
    void changeIsForceSoldOutTest_success() {
        Product product = mock(Product.class);
        given(mockProductRepository.findById(anyLong()))
            .willReturn(Optional.of(product));
        given(mockProductRepository.save(any(Product.class))).willReturn(product);

        productService.changeBooleanField(1L, "isForceSoldOut");

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setIsForceSoldOut(anyBoolean());
        then(mockProductRepository).should().save(product);

    }

    @Test
    @DisplayName("상품 판매여부 수정 테스트")
    void changeIsSelledTest_success() {
        Product product = mock(Product.class);
        given(mockProductRepository.findById(anyLong()))
            .willReturn(Optional.of(product));
        given(mockProductRepository.save(any(Product.class))).willReturn(product);

        productService.changeBooleanField(1L, "isSelled");

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setIsSelled(anyBoolean());
        then(mockProductRepository).should().save(product);
    }

    @Test
    @DisplayName("상품 조회수 수정 테스트")
    void changeDailyHitsTest_success() {
        Product product = mock(Product.class);
        given(mockProductRepository.findById(anyLong()))
            .willReturn(Optional.of(product));
        given(mockProductRepository.save(any(Product.class))).willReturn(product);

        productService.changeDailyHits(1L);

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setDailyHits(anyLong());
        then(mockProductRepository).should().save(product);
    }

    @Test
    @DisplayName("상품 단건 조회 시 실패 테스트 - 상품이 없을 시 예외 발생")
    void findProductTest_failure() {
        given(mockProductRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> productService.findProduct(1L))
            .isInstanceOf(ProductNotFoundException.class)
            .hasMessage(ProductNotFoundException.MESSAGE);
    }

    @Test
    @DisplayName("상품 엔티티 단건 조회 테스트")
    void findProductEntityTest_success() {
        Product product = ProductTransfer.dtoToEntityAdd(productAddRequestDto);

        given(mockProductRepository.findById(anyLong())).willReturn(Optional.of(product));

        Optional<Product> actualProduct = mockProductRepository.findById(1L);

        Assertions.assertThat(actualProduct).isPresent();
        Assertions.assertThat(actualProduct.get().getProductNo())
            .isEqualTo(product.getProductNo());
    }

    @Test
    @DisplayName("상품 상세정보 단건 조회 테스트")
    void findProductTest_success() {
        ProductDetailsResponseDto product = ProductDummy.getProductDetailsResponseDto();

        given(mockProductRepository.findProductDetails(anyLong())).willReturn(Optional.of(product));

        Optional<ProductDetailsResponseDto> actualProduct =
            mockProductRepository.findProductDetails(1L);

        Assertions.assertThat(actualProduct).isPresent();
        Assertions.assertThat(actualProduct.get().getProductNo())
            .isEqualTo(product.getProductNo());
    }

    @Test
    @DisplayName("<관리자> 상품 리스트 조회 테스트")
    void findProductListForAdmin_success() {
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        given(mockProductRepository.findProductListAdmin(any(Pageable.class))).willReturn(
            new PageImpl<>(
                List.of(product1, product2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<ProductDetailsResponseDto> page =
            productService.findProductListForAdmin(pageRequest);

        List<ProductDetailsResponseDto> productList = page.getContent();


        Assertions.assertThat(productList).hasSize(2);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(product1.getProductNo());
        Assertions.assertThat(productList.get(0).getProductName())
            .isEqualTo(product1.getProductName());
    }

    @Test
    @DisplayName("<사용자> 상품 리스트 조회 테스트")
    void findProductListForUser_success() {
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        given(mockProductRepository.findProductListUser(any(Pageable.class))).willReturn(
            new PageImpl<>(
                List.of(product1, product2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ProductDetailsResponseDto> page =
            productService.findProductListForUser(pageRequest);

        List<ProductDetailsResponseDto> productList = page.getContent();


        Assertions.assertThat(productList).hasSize(2);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(product1.getProductNo());
        Assertions.assertThat(productList.get(0).getProductName())
            .isEqualTo(product1.getProductName());
    }

    @Test
    @DisplayName("<사용자> 상품 번호 리스트로 상품 리스트 조회 테스트")
    void findProductListByProductNoListForUser_success() {
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        given(mockProductRepository.findProductListByProductNoListForUser(any(Pageable.class),
            any(List.class))).willReturn(new PageImpl<>(List.of(product1, product2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        List productNoList = new ArrayList();
        productNoList.addAll(Arrays.asList(1L, 2L));

        Page<ProductDetailsResponseDto> page =
            productService.findProductListByProductNoListForUser(pageRequest, productNoList);

        List<ProductDetailsResponseDto> productList = page.getContent();


        Assertions.assertThat(productList).hasSize(2);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(product1.getProductNo());
        Assertions.assertThat(productList.get(0).getProductName())
            .isEqualTo(product1.getProductName());
    }

    @Test
    @DisplayName("<관리자> 상품 번호 리스트로 상품 리스트 조회 테스트")
    void findProductListByProductNoListForAdmin_success() {
        ProductDetailsResponseDto product1 = ProductDummy.getProductDetailsResponseDto();
        ProductDetailsResponseDto product2 = ProductDummy.getProductDetailsResponseDto();

        given(mockProductRepository.findProductListByProductNoListForAdmin(any(Pageable.class),
            any(List.class))).willReturn(new PageImpl<>(List.of(product1, product2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        List productNoList = new ArrayList();
        productNoList.addAll(Arrays.asList(1L, 2L));

        Page<ProductDetailsResponseDto> page =
            productService.findProductListByProductNoListForAdmin(pageRequest, productNoList);

        List<ProductDetailsResponseDto> productList = page.getContent();


        Assertions.assertThat(productList).hasSize(2);
        Assertions.assertThat(productList.get(0).getProductNo())
            .isEqualTo(product1.getProductNo());
        Assertions.assertThat(productList.get(0).getProductName())
            .isEqualTo(product1.getProductName());
    }
}