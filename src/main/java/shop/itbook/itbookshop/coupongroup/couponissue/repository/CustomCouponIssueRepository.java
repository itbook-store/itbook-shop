package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
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

    Page<UserCouponIssueListResponseDto> findAvailableCouponIssueListByMemberNo(Pageable pageable,
                                                                                Long memberNo);

    Page<UserCouponIssueListResponseDto> findNotAvailableCouponIssueListByMemberNo(
        Pageable pageable,
        Long memberNo);

    List<CouponIssue> changePeriodExpiredByMemberNo(Long memberNo);

    CouponIssue findByIdFetchJoin(Long couponIssueNo);

    List<OrderTotalCouponIssueResponseListDto> findAvailableOrderTotalCouponIssueByMemberNo(
        Long memberNo);

    List<CategoryCouponIssueListResponseDto> findAvailableCategoryCouponIssueByMemberNo(
        Long memberNo);

    List<ProductCouponIssueListResponseDto> findAvailableProductCouponIssueByMemberNo(
        Long memberNo);

    List<OrderCouponSimpleListResponseDto> findAvailableProductCouponByMemberNoAndProductNo(
        Long memberNo, Long productNo);

    List<OrderCouponSimpleListResponseDto> findAvailableCategoryCouponByMemberNoAndProductNo(
        Long memberNo, Long productNo);

    List<OrderCouponSimpleListResponseDto> findAvailableTotalCouponByMemberNo(
        Long memberNo);

    Page<AdminCouponIssueListResponseDto> findAllCouponIssue(Pageable pageable);

    Page<AdminCouponIssueListResponseDto> findCouponIssueSearchMemberId(Pageable pageable,
                                                                        String memberId);

    Page<AdminCouponIssueListResponseDto> findCouponIssueSearchCouponName(Pageable pageable,
                                                                          String couponName);

    Page<AdminCouponIssueListResponseDto> findCouponIssueSearchCouponCode(Pageable pageable,
                                                                          String couponCode);
}
