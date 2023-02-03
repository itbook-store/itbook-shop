package shop.itbook.itbookshop.coupongroup.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;

/**
 * @author 송다혜
 * @since 1.0
 */
@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CouponRepositoryTest {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Coupon pointDummyCoupon;

    Coupon amountDummyCoupon;

    Coupon percentDummyCoupon;

    @BeforeEach
    public void setup() {
        amountDummyCoupon = CouponDummy.getAmountCoupon();
        percentDummyCoupon = CouponDummy.getPercentCoupon();
        pointDummyCoupon = CouponDummy.getPointCoupon();

        couponRepository.save(amountDummyCoupon);
        couponRepository.save(percentDummyCoupon);
        couponRepository.save(pointDummyCoupon);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findCouponById() {

        //when
        Coupon coupon =
            couponRepository.findById(amountDummyCoupon.getCouponNo()).orElseThrow();

        //then
        assertThat(coupon.getCouponNo())
            .isEqualTo(amountDummyCoupon.getCouponNo());
    }

    @Test
    void deleteById() {

        couponRepository.deleteById(amountDummyCoupon.getCouponNo());
        couponRepository.deleteById(percentDummyCoupon.getCouponNo());
        couponRepository.deleteById(pointDummyCoupon.getCouponNo());
    }

    @Test
    void findCouponByCode() {

        //when
        Coupon coupon =
            couponRepository.findCouponByCode(amountDummyCoupon.getCode()).orElseThrow();

        //then
        assertThat(coupon.getCouponNo()).isEqualTo(amountDummyCoupon.getCouponNo());
    }
}