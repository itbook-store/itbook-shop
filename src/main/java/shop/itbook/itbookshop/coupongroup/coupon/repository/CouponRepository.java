package shop.itbook.itbookshop.coupongroup.coupon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, CustomCouponRepository {

    Optional<Coupon> findCouponByCode(String code);
}
