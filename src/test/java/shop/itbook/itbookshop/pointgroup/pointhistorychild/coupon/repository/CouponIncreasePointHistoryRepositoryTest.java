package shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.repository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
class CouponIncreasePointHistoryRepositoryTest {

    @Autowired
    CouponIncreasePointHistoryRepository couponIncreasePointHistoryRepository;

    @Autowired
    CouponRepository couponRepository;

    @BeforeEach
    void setUp() {

    }
}