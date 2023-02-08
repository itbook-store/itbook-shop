package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository;

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
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class OrderTotalCouponRepositoryTest {
    @Autowired
    OrderTotalCouponRepository orderTotalCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponTypeRepository couponTypeRepository;


    @Autowired
    TestEntityManager testEntityManager;

    Coupon amountDummyCoupon;

    CouponType couponType;

    OrderTotalCoupon orderTotalCoupon;


    @BeforeEach
    void setUp() {

        //given

        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

        orderTotalCoupon = new OrderTotalCoupon(amountDummyCoupon.getCouponNo());
        orderTotalCouponRepository.save(orderTotalCoupon);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById(){
        OrderTotalCoupon orderTotalCoupon1 =
            orderTotalCouponRepository.findById(orderTotalCoupon.getCouponNo()).orElseThrow();

        assertThat(orderTotalCoupon1.getCoupon().getCode()).isEqualTo(amountDummyCoupon.getCode());
    }
}