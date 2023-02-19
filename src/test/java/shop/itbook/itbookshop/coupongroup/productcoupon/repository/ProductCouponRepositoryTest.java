package shop.itbook.itbookshop.coupongroup.productcoupon.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class ProductCouponRepositoryTest {
    @Autowired
    ProductCouponRepository productCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    ProductRepository productRepository;


    @Autowired
    TestEntityManager testEntityManager;

    Coupon amountDummyCoupon1;

    Coupon amountDummyCoupon2;

    CouponType couponType;

    Product product1;

    ProductCoupon productCoupon1;

    ProductCoupon productCoupon2;

    @BeforeEach
    void setUp() {

        //given
        product1 = ProductDummy.getProductSuccess();

        productRepository.save(product1);

        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon1 = CouponDummy.getAmountCoupon();
        amountDummyCoupon1.setCouponType(couponType);

        amountDummyCoupon2 = CouponDummy.getAmountCoupon();
        amountDummyCoupon2.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon1);
        couponRepository.save(amountDummyCoupon2);

        productCoupon1 = new ProductCoupon(amountDummyCoupon1.getCouponNo(), product1);
        productCoupon2 = new ProductCoupon(amountDummyCoupon2.getCouponNo(), product1);

        productCouponRepository.save(productCoupon1);
        productCouponRepository.save(productCoupon2);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById(){
        ProductCoupon productCoupon =
            productCouponRepository.findById(productCoupon1.getCouponNo()).orElseThrow();

        assertThat(productCoupon.getProduct().getName()).isEqualTo(product1.getName());
    }

    @Test
    void findByCouponNo_success(){
        ProductCoupon productCoupon =
            productCouponRepository.findByProductCouponByCouponNo(productCoupon1.getCouponNo());

        assertThat(productCoupon1.getProduct().getName()).isEqualTo(product1.getName());
        assertThat(productCoupon1.getProduct().getProductNo()).isEqualTo(product1.getProductNo());
        assertThat(productCoupon1.getCouponNo()).isEqualTo(amountDummyCoupon1.getCouponNo());
    }

    @Test
    void findByCouponNo_fail(){
        ProductCoupon productCoupon1 =
            productCouponRepository.findByProductCouponByCouponNo(123L);

        assertThat(productCoupon1).isNull();
    }

    @Test
    @DisplayName("상품 쿠폰 리스트를 제대로 반환하는지 테스트입니다.")
    void findCategoryCouponList_success(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminCouponListResponseDto> page = productCouponRepository.findProductCouponPageList(pageRequest);

        //when
        List<AdminCouponListResponseDto> productCouponList = page.getContent();

        //then
        Assertions.assertThat(productCouponList.size()).isEqualTo(2);
        Assertions.assertThat(productCouponList.get(1).getCouponType()).isEqualTo(couponType.getCouponTypeEnum().getCouponType());
        Assertions.assertThat(productCouponList.get(1).getIsDuplicateUse()).isEqualTo(amountDummyCoupon1.getIsDuplicateUse());
        Assertions.assertThat(productCouponList.get(1).getProductName()).isEqualTo(product1.getName());
    }

    @Test
    @DisplayName("이 쿠폰이 상품 쿠폰인지 확인하는 테스트")
    void findByProductCouponByCouponNo_success(){
        ProductCoupon productCoupon = productCouponRepository.findByProductCouponByCouponNo(amountDummyCoupon1.getCouponNo());

        assertThat(productCoupon.getProduct().getName()).isEqualTo(product1.getName());
        assertThat(productCoupon.getProduct().getProductNo()).isEqualTo(product1.getProductNo());
        assertThat(productCoupon.getCouponNo()).isEqualTo(amountDummyCoupon1.getCouponNo());
    }

    @Test
    @DisplayName("이 쿠폰이 상품 쿠폰인지 확인하고 아무것도 없을때 테스트")
    void findByProductCouponByCouponNo_fail(){
        ProductCoupon productCoupon = productCouponRepository.findByProductCouponByCouponNo(123L);

        assertThat(productCoupon).isNull();
    }
}