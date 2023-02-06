package shop.itbook.itbookshop.coupongroup.couponissue.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCouponIssueRepository {
    Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(Pageable pageable,
                                                                  String memberId);

    CouponIssue findByIdFetchJoin(Long couponIssueNo);
}
