package shop.itbook.itbookshop.coupongroup.couponissue.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CouponIssueListByGroupResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByCoupon(Long memberNo, Long couponNo);
    List<CouponIssue> addCouponIssueByCoupons(Long memberNo, String couponType);
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberNo(Pageable pageable,
                                                                       Long memberNo);
    void usePointCouponAndCreatePointHistory(Long couponIssueNo);

    CouponIssueListByGroupResponseDto findMemberAvailableCouponIssuesList(Long memberNo);

    Page<AdminCouponIssueListResponseDto> findAllCouponIssue(Pageable pageable);

    CouponIssue findCouponIssueByCouponIssueNo(Long couponIssueNo);

    CouponIssue usingCouponIssue(Long couponIssueNo);

    CouponIssue cancelCouponIssue(Long couponIssueNo);
}
