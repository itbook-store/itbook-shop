package shop.itbook.itbookshop.coupongroup.coupontype.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponTypeRepository {
    Optional<CouponType> findByCouponTypeName(String couponTypeName);
}
