package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.OrderTotalCouponIssueResponseListDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.ProductCouponIssueListResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponIssueRepository {
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberNo(Pageable pageable,
                                                                  Long memberNo);

    CouponIssue findByIdFetchJoin(Long couponIssueNo);

    List<OrderTotalCouponIssueResponseListDto> findAvailableOrderTotalCouponIssueByMemberNo(
        Long memberNo);

    List<CategoryCouponIssueListResponseDto> findAvailableCategoryCouponIssueByMemberNo(
        Long memberNo);

    List<ProductCouponIssueListResponseDto> findAvailableProductCouponIssueByMemberNo(
        Long memberNo);
}
