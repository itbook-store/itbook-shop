package shop.itbook.itbookshop.productgroup.product.service.adminapi.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * @author 이하늬
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductAdminServiceImpl.class)
class ProductAdminServiceImplTest {

    @Autowired
    ProductAdminService productService;

    @MockBean
    ProductRepository productRepository;

    private AddProductRequestDto addProductRequestDto_success;
    private AddProductRequestDto addProductRequestDto_failure;

    @BeforeEach
    void setUp() {
        addProductRequestDto_success = new AddProductRequestDto("객체지향의 사실과 오해",
            "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
            "다 모르겠고 그냥 제발 됐으면 좋겠네요..", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1, 10, 12000L);

        addProductRequestDto_failure = new AddProductRequestDto("객체지향의 사실과 오해",
            null, "다 모르겠고 그냥 제발 됐으면 좋겠네요..", 1, Boolean.TRUE, Boolean.FALSE,
            "testUrl", 20000L, 1, 10, 12000L);
    }

    @Test
    @DisplayName("상품 등록 테스트")
    void addProductTest() {

        Product product_success = ProductTransfer.dtoToEntityAdd(addProductRequestDto_success);
        given(productRepository.save(any(Product.class)))
            .willReturn(product_success);

        Long actual = productService.addProduct(addProductRequestDto_success);

        Assertions.assertThat(actual).isEqualTo(product_success.getProductNo());
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void modifyProductTest() {

        // TODO 수정 테스트 verify 써서 해보기
//        Product product = ProductTransfer.dtoToEntityAdd(addProductRequestDto_success);
//        given(productRepository.save(any(Product.class))).willReturn(product);
//        given(productRepository.findById(anyLong())).willReturn(Optional.ofNullable(product));
//
//        Long productNo = productService.addProduct(addProductRequestDto_success);
//
//        ModifyProductRequestDto modifyProductRequestDto =
//            new ModifyProductRequestDto("객체지향의 사실과 오해",
//                "객체지향이란 무엇인가? 이 책은 이 질문에 대한 답을 찾기 위해 노력하고 있는 모든 개발자를 위한 책이다.",
//                "다 모르겠고 그냥 제발 됐으면 좋겠네요..", 1, Boolean.TRUE, Boolean.FALSE,
//                "testUrl", 20000L, 1, 10, 15000L);
//
//
//        productService.modifyProduct(productNo, modifyProductRequestDto);
//
//        Optional<Product> actual = productRepository.findById(productNo);
//        Assertions.assertThat(actual.get().getRawPrice()).isEqualTo(15000L);
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void removeProductTests() {
    }

    @Test
    @DisplayName("상품 단건 조회 테스트")
    void findProductTest() {
    }
}