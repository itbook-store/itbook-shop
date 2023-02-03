package shop.itbook.itbookshop.coupongroup.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
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

    Coupon amountDummyCoupon;

    CouponType couponType;

    @BeforeEach
    public void setup() {
        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

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
    }

    @Test
    void findCouponByCode() {

        //when
        Coupon coupon =
            couponRepository.findCouponByCode(amountDummyCoupon.getCode()).orElseThrow();

        //then
        assertThat(coupon.getCouponNo()).isEqualTo(amountDummyCoupon.getCouponNo());
    }

    @Test
    void findByAvailableWelcomeCoupon(){
        CouponType welcomeCouponType = couponTypeRepository.save(
            new CouponType(0, CouponTypeEnum.WELCOME_COUPON));

        couponTypeRepository.save(welcomeCouponType);

        Coupon percentDummyCoupon = CouponDummy.getPercentCoupon();
        percentDummyCoupon.setCouponType(welcomeCouponType);

        Coupon pointDummyCoupon = CouponDummy.getPointCoupon();
        pointDummyCoupon.setCouponType(welcomeCouponType);

        couponRepository.save(percentDummyCoupon);
        couponRepository.save(pointDummyCoupon);

        List<Coupon> coupons = couponRepository.findByAvailableWelcomeCoupon();

        assertThat(coupons.size()).isEqualTo(2);
    }

    @Disabled
    @Test
    void testFindCouponByCode() {
    }


}