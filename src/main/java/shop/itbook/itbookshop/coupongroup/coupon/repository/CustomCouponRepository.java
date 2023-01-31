package shop.itbook.itbookshop.coupongroup.coupon.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponRepository {
    List<CouponListResponseDto> findCouponList();
}
