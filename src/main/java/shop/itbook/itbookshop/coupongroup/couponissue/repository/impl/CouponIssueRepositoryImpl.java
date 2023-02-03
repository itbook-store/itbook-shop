package shop.itbook.itbookshop.coupongroup.couponissue.repository.impl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CustomCouponIssueRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponIssueRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCouponIssueRepository {
    public CouponIssueRepositoryImpl() {
        super(CouponIssue.class);
    }


}
