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
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.fileservice.FileService;
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

    ProductRequestDto productRequestDto;
    ProductRequestDto modifyProductRequestDto;

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

        productRequestDto = ProductBookRequestDummy.getProductRequest();
        modifyProductRequestDto = ProductBookRequestDummy.getProductRequest();
        ReflectionTestUtils.setField(modifyProductRequestDto, "productName", "객체지향의 거짓과 오해");
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void addProductTest_success() {
        Product product = ProductTransfer.dtoToEntityAdd(productRequestDto);
        given(mockProductCategoryService.addProductCategory(any(Product.class), anyList()))
            .willReturn(CategoryDummy.getCategoryNoHiddenBook());
        given(mockProductRepository.save(any(Product.class)))
            .willReturn(product);

        Long actual = productService.addProduct(productRequestDto, mockImageFile);

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
        given(mockProductRepository.save(any(Product.class)))
            .willReturn(ProductTransfer.dtoToEntityAdd(modifyProductRequestDto));

        productService.modifyProduct(1L, modifyProductRequestDto, mockImageFile);

        then(mockProductRepository).should().findById(anyLong());
        then(product).should().setName(anyString());
        then(product).should().setStock(anyInt());
        then(product).should().setRawPrice(anyLong());
        then(product).should().setIsForceSoldOut(anyBoolean());
    }

//    @Test
//    @DisplayName("상품 삭제 테스트")
//    void deleteProductTest_success() {
//        productService.removeProduct(1L);
//
//        then(mockProductRepository).should().save(1L);
//        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE);
//        Assertions.assertThat(mockProductCategoryService.findCategoryList(pageable, 1L)).isNull();
//    }

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
        Product product = ProductTransfer.dtoToEntityAdd(productRequestDto);

        given(mockProductRepository.findById(anyLong())).willReturn(Optional.of(product));

        Optional<Product> actualProduct = mockProductRepository.findById(1L);

        Assertions.assertThat(actualProduct).isPresent();
        Assertions.assertThat(actualProduct.get().getProductNo())
            .isEqualTo(product.getProductNo());
    }


}