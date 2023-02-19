package shop.itbook.itbookshop.coupongroup.categorycoupon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;

/**
 * 카테고리 쿠폰의 Querydsl을 사용하는 레포지토리입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCategoryCouponRepository {
    /**
     * 카테고리 쿠폰을 페이지 객체로 반환하는 메소드 입니다.
     *
     * @param pageable 페이저블 객체입니.
     * @return 카테고리 쿠폰리스트를 페이지 단위로 반환합니다.
     */
    Page<AdminCouponListResponseDto> findCategoryCouponList(Pageable pageable);
}
