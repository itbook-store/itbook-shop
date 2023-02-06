package shop.itbook.itbookshop.coupongroup.couponissue.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.request.CouponIssueNoRequest;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByCoupon(Long memberNo, Long couponNo);
    List<CouponIssue> addCouponIssueByCoupons(Long memberNo, String couponType);
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(Pageable pageable,
                                                                       String memberNo);
    void usePointCouponAndCreatePointHistory(Long couponIssueNo);
}
