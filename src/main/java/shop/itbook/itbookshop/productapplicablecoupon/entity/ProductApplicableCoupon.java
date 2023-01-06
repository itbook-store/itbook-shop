package shop.itbook.itbookshop.productapplicablecoupon.entity;

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
import shop.itbook.itbookshop.productcoupon.entity.ProductCoupon;

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
@Table(name = "product_applicable_coupon")
@Entity
public class ProductApplicableCoupon {
    @EmbeddedId
    private Pk pk;

    @MapsId("orderProductNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private ProductCoupon productCoupon;

    /**
     * ProductApplicableCoupon Pk 클레스 입니다.
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
