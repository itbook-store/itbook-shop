package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.book.BookDummy;
import shop.itbook.itbookshop.book.entity.Book;
import shop.itbook.itbookshop.book.repository.BookRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class ProductTypeRegistrationRepositoryTest {

    @Autowired
    ProductTypeRegistrationRepository productTypeRegistrationRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductTypeRepository productTypeRepository;
    Product product;
    ProductType productType;
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    private BookRepository bookRepository;
    Pageable pageable;

    @BeforeEach
    void setUp() {

        pageable = PageRequest.of(0, Integer.MAX_VALUE);
        product = ProductDummy.getProductSuccess();
        productType = new ProductType(null, ProductTypeEnum.BESTSELLER);

        productRepository.save(product);
        productTypeRepository.save(productType);

        Book book = BookDummy.getBookSuccess();
        book.setProductNo(product.getProductNo());
        bookRepository.save(book);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("상품유형 등록 성공")
    void addProductTypeRegistrationTest() {

        Product actualProduct = productRepository.findById(product.getProductNo()).get();
        ProductType actualProductType =
            productTypeRepository.findById(productType.getProductTypeNo()).get();
        ProductTypeRegistration productTypeRegistration =
            new ProductTypeRegistration(actualProduct, actualProductType);
        productTypeRegistrationRepository.save(productTypeRegistration);

        Optional<ProductTypeRegistration> optionalProductTypeRegistration =
            productTypeRegistrationRepository.findById(productTypeRegistration.getPk());

        Assertions.assertThat(optionalProductTypeRegistration).isPresent();
        Assertions.assertThat(optionalProductTypeRegistration.get().getProduct().getProductNo())
            .isEqualTo(actualProduct.getProductNo());
        Assertions.assertThat(
                optionalProductTypeRegistration.get().getProductType().getProductTypeNo())
            .isEqualTo(actualProductType.getProductTypeNo());
    }

    @Test
    @DisplayName("상품 번호로 상품유형 리스트 조회 성공")
    void findProductTypeListByProductNoTest() {

        Product actualProduct = productRepository.findById(product.getProductNo()).get();
        ProductType actualProductType =
            productTypeRepository.findById(productType.getProductTypeNo()).get();

        ProductTypeRegistration productTypeRegistration1 =
            new ProductTypeRegistration(actualProduct, actualProductType);
        productTypeRegistrationRepository.save(productTypeRegistration1);


        List<FindProductTypeResponseDto> productTypeListByProductNo =
            productTypeRegistrationRepository.findProductTypeListWithProductNo(
                PageRequest.of(0, Integer.MAX_VALUE),
                actualProduct.getProductNo()).getContent();

        Assertions.assertThat(productTypeListByProductNo).hasSize(1);
        Assertions.assertThat(
                productTypeListByProductNo.get(0).getProductTypeName().getProductType())
            .isEqualTo(actualProductType.getProductTypeEnum().getProductType());
    }

}