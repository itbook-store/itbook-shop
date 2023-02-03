package shop.itbook.itbookshop.coupongroup.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Coupon pointDummyCoupon;

    Coupon amountDummyCoupon;

    Coupon percentDummyCoupon;

    CouponType couponType;

    @BeforeEach
    public void setup() {
        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        percentDummyCoupon = CouponDummy.getPercentCoupon();
        percentDummyCoupon.setCouponType(couponType);

        pointDummyCoupon = CouponDummy.getPointCoupon();
        pointDummyCoupon.setCouponType(couponType);


        couponRepository.save(amountDummyCoupon);
        couponRepository.save(percentDummyCoupon);
        couponRepository.save(pointDummyCoupon);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    @Order(1)
    void findCouponById() {

        //when
        Coupon coupon =
            couponRepository.findById(amountDummyCoupon.getCouponNo()).orElseThrow();

        //then
        assertThat(coupon.getCouponNo())
            .isEqualTo(amountDummyCoupon.getCouponNo());
    }

    @Test
    @Order(2)
    void deleteById() {

        couponRepository.deleteById(amountDummyCoupon.getCouponNo());
        couponRepository.deleteById(percentDummyCoupon.getCouponNo());
        couponRepository.deleteById(pointDummyCoupon.getCouponNo());
    }

    @Test
    @Order(3)
    void findCouponByCode() {

        //when
        Coupon coupon =
            couponRepository.findCouponByCode(amountDummyCoupon.getCode()).orElseThrow();

        //then
        assertThat(coupon.getCouponNo()).isEqualTo(amountDummyCoupon.getCouponNo());
    }

//    @AfterEach
//    void setDown(){
//        couponRepository.deleteById(amountDummyCoupon.getCouponNo());
//        couponRepository.deleteById(percentDummyCoupon.getCouponNo());
//        couponRepository.deleteById(pointDummyCoupon.getCouponNo());
//
//        couponTypeRepository.deleteAll();
//        testEntityManager.flush();
//        testEntityManager.clear();
//    }
}