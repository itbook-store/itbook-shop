//package shop.itbook.itbookshop.productgroup.product.service.impl;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyBoolean;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//import java.util.Optional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
//import shop.itbook.itbookshop.productgroup.product.entity.Product;
//import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
//import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
//import shop.itbook.itbookshop.productgroup.product.service.ProductService;
//import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
//
///**
// * @author 이하늬
// * @since 1.0
// */
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ProductServiceImpl.class)
//class ProductServiceTest {
//
//    @Autowired
//    ProductService productService;
//
//    @MockBean
//    ProductRepository mockProductRepository;
//
//    ProductRequestDto productRequestDto;
//    ProductRequestDto modifyProductRequestDto;
//
//    @BeforeEach
//    void setUp() {
//        productRequestDto = new ProductRequestDto("객체지향의 사실과 오해",
//            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
//            "상세 설명", Boolean.TRUE, Boolean.FALSE, 1,
//            "testUrl", 20000L, 1, 10.0, 12000L);
//
//        modifyProductRequestDto = new ProductRequestDto("객체지향의 사실과 오해",
//            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
//            "상세 설명", 1, Boolean.TRUE, Boolean.FALSE,
//            "testUrl", 20000L, 1, 10.0, 12000L);
//    }
//
//    @Test
//    @DisplayName("상품 등록 테스트")
//    void addProductTest() {
//        Product product = ProductTransfer.dtoToEntityAdd(productRequestDto);
//        given(mockProductRepository.save(any(Product.class)))
//            .willReturn(product);
//
//        Long actual = productService.addProduct(productRequestDto);
//
//        Assertions.assertThat(actual).isEqualTo(product.getProductNo());
//    }
//
//    @Test
//    @DisplayName("상품 수정 테스트")
//    void modifyProductTest() {
//        Product product = mock(Product.class);
//        given(mockProductRepository.findById(anyLong())).willReturn(Optional.of(product));
//
//        productService.modifyProduct(1L, modifyProductRequestDto);
//
//        then(mockProductRepository).should().findById(anyLong());
//        then(product).should().setName(anyString());
//        then(product).should().setStock(anyInt());
//        then(product).should().setRawPrice(anyLong());
//        then(product).should().setIsDeleted(anyBoolean());
//    }
//
//    @Test
//    @DisplayName("상품 삭제 테스트")
//    void removeProductTests() {
//        productService.removeProduct(1L);
//
//        then(mockProductRepository).should().deleteById(1L);
//    }
//
//    @Test
//    @DisplayName("상품 단건 조회 테스트")
//    void findProductTest() {
//        Product product = ProductTransfer.dtoToEntityAdd(productRequestDto);
//
//        given(mockProductRepository.findById(anyLong())).willReturn(Optional.of(product));
//
//        Optional<Product> actualProduct = mockProductRepository.findById(1L);
//
//        Assertions.assertThat(actualProduct).isPresent();
//        Assertions.assertThat(actualProduct.get().getProductNo())
//            .isEqualTo(product.getProductNo());
//    }
//
//    @Test
//    @DisplayName("상품 단건 조회 시 실패 테스트 - 상품이 없을 시 예외 발생")
//    void findProductTest_failure() {
//        given(mockProductRepository.findById(anyLong())).willReturn(Optional.empty());
//        Assertions.assertThatThrownBy(() -> productService.findProduct(1L))
//            .isInstanceOf(ProductNotFoundException.class)
//            .hasMessage(ProductNotFoundException.MESSAGE);
//    }
//}