package shop.itbook.itbookshop.productapplicablecoupon.entity;

import java.io.Serializable;
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
 * The type Product applicable coupon.
 *
 * @author 정재원
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
    @JoinColumn(name = "order_product_no", nullable = false)
    private OrderProduct orderProduct;

    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no", nullable = false)
    private Coupon coupon;

    /**
     * ProductApplicableCoupon 엔티티를 식별하기 위한 Pk 클래스 입니다.
     *
     * @author 정재원
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        
        private Long orderProductNo;

        private Long couponNo;
    }
}
