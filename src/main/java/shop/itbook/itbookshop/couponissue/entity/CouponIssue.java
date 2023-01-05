package shop.itbook.itbookshop.couponissue.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 쿠폰_발급 이력을 관리하는 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class CouponIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_issue_no ", nullable = false)
    private Integer couponIssueNo;

    
//    member_no
//    coupon_no
//    usage_status_no
//    coupon_issue_created_at
//    coupon_usage_created_at
}
