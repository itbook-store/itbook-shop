package shop.itbook.itbookshop.categoryapplicablecoupon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.categorycoupon.entity.CategoryCoupon;
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
@Table(name = "category_applicable_coupon")
public class CategoryApplicableCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId("orderProductNo")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private CategoryCoupon categoryCoupon;

    /**
     * CategoryApplicableCoupon의 Pk입니다.
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

        private Long couponNo;
    }
}
