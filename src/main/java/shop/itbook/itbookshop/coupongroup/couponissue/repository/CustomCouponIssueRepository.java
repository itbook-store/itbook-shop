package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response.CategoryCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.response.OrderTotalCouponResponseListDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.response.ProductCouponListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponIssueRepository {
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(Pageable pageable,
                                                                  String memberId);

    CouponIssue findByIdFetchJoin(Long couponIssueNo);

    List<OrderTotalCouponResponseListDto> findAvailableOrderTotalCouponIssueByMemberNo(
        Long memberNo);

    List<CategoryCouponListResponseDto> findAvailableCategoryCouponIssueByMemberNo(
        Long memberNo);

    List<ProductCouponListResponseDto> findAvailableProductCouponIssueByMemberNo(
        Long memberNo);
}
