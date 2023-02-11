package shop.itbook.itbookshop.coupongroup.coupontype.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface CouponTypeRepository extends JpaRepository<CouponType, Integer>,
    CustomCouponTypeRepository {
}
