package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomOrderTotalCouponRepository {
    Page<OrderCouponListResponseDto> findTotalCouponPageList(Pageable pageable);
}
