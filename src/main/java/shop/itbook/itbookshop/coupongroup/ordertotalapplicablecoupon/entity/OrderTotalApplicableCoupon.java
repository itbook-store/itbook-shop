package shop.itbook.itbookshop.coupongroup.ordertotalapplicablecoupon.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.order.entity.Order;

/**
 * 주문총액쿠폰적용 테이블 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_total_applicable_coupon")
public class OrderTotalApplicableCoupon {

    @Id
    private Long couponIssueNo;

    @MapsId("couponIssueNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_issue_no")
    private CouponIssue couponIssue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", nullable = false)
    private Order order;
}
