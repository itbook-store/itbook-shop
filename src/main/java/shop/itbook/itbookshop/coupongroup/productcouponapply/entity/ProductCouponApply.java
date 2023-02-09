package shop.itbook.itbookshop.coupongroup.productcouponapply.entity;

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
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * 상품쿠폰적용 관련 엔티티입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_coupon_apply")
@Entity
public class ProductCouponApply {

    @Id
    private Long couponIssueNo;

    @MapsId("couponIssueNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_issue_no", nullable = false)
    private CouponIssue couponIssue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", nullable = false, unique = true)
    private OrderProduct orderProduct;

    public ProductCouponApply(Long couponIssueNo, OrderProduct orderProduct) {
        this.couponIssueNo = couponIssueNo;
        this.orderProduct = orderProduct;
    }
}
