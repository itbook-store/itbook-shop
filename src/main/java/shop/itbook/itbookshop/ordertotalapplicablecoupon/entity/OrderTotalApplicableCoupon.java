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
import shop.itbook.itbookshop.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordertotalcoupon.entity.OrderTotalCoupon;

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

    @EmbeddedId
    private Pk pk;

    @MapsId("orderProductNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private OrderTotalCoupon orderTotalCoupon;

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
        private Long orderProductNo;
        private Integer couponNo;
    }
}
