package shop.itbook.itbookshop.coupongroup.couponissue.service;


import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponIssueService {
    Long addCouponIssueByNormalCoupon(String memberId, Long couponNo);
    List<CouponIssue> addCouponIssueByWelcomeCoupon(Member member);
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(Pageable pageable,
                                                                       String memberId);
}
