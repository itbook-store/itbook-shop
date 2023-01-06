package shop.itbook.itbookshop.ordertotalapplicablecoupon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupon.entity.Coupon;
import shop.itbook.itbookshop.orderproduct.entity.OrderProduct;

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
@Table(name = "category_applicable_coupon")
public class OrderTotalApplicableCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId("orderProductNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    //todo:copon이 아니라 OrderTotalCoupon을 받도록 해야함
    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    /**
     * OrderTotalApplicableCoupon의 Pk 클레스 입니다.
     *
     * @author 송다혜
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "order_product_no")
        private Long orderProductNo;

        @Column(name = "coupon_no")
        private Integer couponNo;
    }
}
