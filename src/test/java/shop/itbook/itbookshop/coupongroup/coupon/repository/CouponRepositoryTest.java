package shop.itbook.itbookshop.coupongroup.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
    void findByAvailableCoupon(){
        CouponType welcomeCouponType = couponTypeRepository.save(
            new CouponType(0, CouponTypeEnum.WELCOME_COUPON));

        couponTypeRepository.save(welcomeCouponType);

        Coupon percentDummyCoupon = CouponDummy.getPercentCoupon();
        percentDummyCoupon.setCouponType(welcomeCouponType);
        percentDummyCoupon.setCouponExpiredAt(LocalDateTime.now().plusDays(1L));

        Coupon pointDummyCoupon = CouponDummy.getPointCoupon();
        pointDummyCoupon.setCouponType(welcomeCouponType);
        pointDummyCoupon.setCouponExpiredAt(LocalDateTime.now().plusDays(1L));

        Coupon pointDummyCoupon2 = CouponDummy.getPointCoupon();
        pointDummyCoupon2.setCouponType(welcomeCouponType);
        pointDummyCoupon2.setCouponExpiredAt(LocalDateTime.now().minusDays(1L));

        couponRepository.save(percentDummyCoupon);
        couponRepository.save(pointDummyCoupon);
        couponRepository.save(pointDummyCoupon2);

        List<Coupon> coupons = couponRepository.findByAvailableCouponByCouponType(CouponTypeEnum.WELCOME_COUPON);

        assertThat(coupons.size()).isEqualTo(2);
    }



}