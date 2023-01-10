package shop.itbook.itbookshop.productgroup.product.service.adminapi.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;

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
        
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void modifyProductTest() {
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