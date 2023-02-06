package shop.itbook.itbookshop.coupongroup.coupon.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * 쿼리 dsl 을 처리하기 위한 레포지토리 인터페이스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponRepository {

    /**
     * 쿠폰의 목록을 반환하기 위한 클레스입니다..
     *
     * @return 쿠폰목록입니다.
     */
    Page<CouponListResponseDto> findCouponList(Pageable pageable);

    List<Coupon> findByAvailableCouponByCouponType(CouponTypeEnum couponTypeEnum);

    List<CouponListResponseDto> findByAvailableCouponDtoByCouponType(CouponTypeEnum couponTypeEnum);

    Page<CouponListResponseDto> findByCouponAtCouponTypeList(Pageable pageable,
                                                             CouponTypeEnum couponTypeEnum);
}
