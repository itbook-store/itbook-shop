package shop.itbook.itbookshop.coupongroup.coupontype.repository;

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

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CouponTypeRepositoryTest {

    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    TestEntityManager testEntityManager;

    CouponType couponType;

    @BeforeEach
    void setup(){
        couponType = CouponTypeDummy.getCouponType();
        couponTypeRepository.save(couponType);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void find_by_couponTypeName(){
        CouponType couponType1 = couponTypeRepository.findByCouponTypeName("일반쿠폰").orElseThrow();

        assertThat(couponType1.getCouponTypeEnum()).isEqualTo(CouponTypeEnum.NORMAL_COUPON);
    }

}