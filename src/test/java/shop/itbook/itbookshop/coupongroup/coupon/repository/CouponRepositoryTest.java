package shop.itbook.itbookshop.coupongroup.coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

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
    Coupon amountDummyCoupon1;

    CouponType couponType;
    CouponType couponType1;

    @BeforeEach
    public void setup() {
        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);
        couponType1 = CouponTypeDummy.getWelcomeCouponType();
        couponType1 = couponTypeRepository.save(couponType1);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

        amountDummyCoupon1 = CouponDummy.getAmountCoupon();
        amountDummyCoupon1.setCouponType(couponType1);

        couponRepository.save(amountDummyCoupon1);

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
    void findByAvailableCouponByCouponType() {
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

        List<Coupon> coupons =
            couponRepository.findByAvailableCouponByCouponType(CouponTypeEnum.WELCOME_COUPON);

        assertThat(coupons).hasSize(3);
    }

    @Test
    void findCouponList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminCouponListResponseDto> page =
            couponRepository.findCouponList(pageRequest);

        //when
        List<AdminCouponListResponseDto> couponList = page.getContent();

        //then
        Assertions.assertThat(couponList).hasSize(3);
        Assertions.assertThat(couponList.get(0).getCouponType())
            .isEqualTo(couponType1.getCouponTypeEnum().getCouponType());
    }

    @Test
    void findByAvailableCouponDtoByCouponType() {
        List<AdminCouponListResponseDto> couponList =
            couponRepository.findByAvailableCouponDtoByCouponType(CouponTypeEnum.NORMAL_COUPON);

        //then
        Assertions.assertThat(couponList).hasSize(2);
        for (AdminCouponListResponseDto coupon : couponList) {
            Assertions.assertThat(coupon.getCouponType())
                .isEqualTo(CouponTypeEnum.NORMAL_COUPON.getCouponType());
        }

    }

    @Test
    void findByCouponAtCouponTypeList() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminCouponListResponseDto> page =
            couponRepository.findByCouponAtCouponTypeList(pageRequest,
                CouponTypeEnum.WELCOME_COUPON);

        //when
        List<AdminCouponListResponseDto> couponList = page.getContent();

        //
        Assertions.assertThat(couponList).hasSize(1);
        for (AdminCouponListResponseDto coupon : couponList) {
            Assertions.assertThat(coupon.getCouponType())
                .isEqualTo(CouponTypeEnum.WELCOME_COUPON.getCouponType());
        }
    }


}