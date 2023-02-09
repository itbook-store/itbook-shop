package shop.itbook.itbookshop.coupongroup.productcoupon.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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

    Coupon amountDummyCoupon;

    CouponType couponType;

    Product product1;

    ProductCoupon productCoupon;

    @BeforeEach
    void setUp() {

        //given
        product1 = ProductDummy.getProductSuccess();

        productRepository.save(product1);

        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

        productCoupon = new ProductCoupon(amountDummyCoupon.getCouponNo(), product1);
        productCouponRepository.save(productCoupon);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById(){
        ProductCoupon productCoupon1 =
            productCouponRepository.findById(productCoupon.getCouponNo()).orElseThrow();

        assertThat(productCoupon1.getProduct().getName()).isEqualTo(product1.getName());
    }
}