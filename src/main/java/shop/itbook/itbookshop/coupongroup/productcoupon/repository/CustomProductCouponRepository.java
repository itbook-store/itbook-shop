package shop.itbook.itbookshop.coupongroup.productcoupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomProductCouponRepository {
    Page<OrderCouponListResponseDto> findProductCouponPageList(Pageable pageable);
}
