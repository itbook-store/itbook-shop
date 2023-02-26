package shop.itbook.itbookshop.coupongroup.coupontype.repository.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CouponTypeRepositoryImplTest {

    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    TestEntityManager testEntityManager;

    CouponType couponType;

    @BeforeEach
    void setUp(){
        couponType = CouponTypeDummy.getWelcomeCouponType();
        couponType = couponTypeRepository.save(couponType);
    }

    @Test
    void findByCouponTypeName() {

        CouponType result =
            couponTypeRepository.findByCouponTypeName(CouponTypeEnum.WELCOME_COUPON.getCouponType()).orElseThrow();

        assertThat(result.getCouponTypeEnum()).isEqualTo(CouponTypeEnum.WELCOME_COUPON);
    }
}