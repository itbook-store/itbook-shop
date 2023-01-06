package shop.itbook.itbookshop.coupongroup.categoryapplicablecoupon.entity;

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
import shop.itbook.itbookshop.orderproduct.entity.OrderProduct;

/**
 * 카테고리쿠폰적용 관련 쿠폰입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category_coupon_apply")
public class CategoryCouponApply {

    @Id
    private Long couponIssueNo;

    @MapsId("couponIssueNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_issue_no", nullable = false)
    private CouponIssue couponIssue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", nullable = false, unique = true)
    private OrderProduct orderProduct;

}
