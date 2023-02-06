package shop.itbook.itbookshop.coupongroup.productcoupon.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 쿠폰이 개별 상품에 사용할 수 있는 쿠폰 인지를 구별하기 위한 테이블의 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_coupon")
@Entity
public class ProductCoupon {

    @Id
    private Long couponNo;

    @MapsId("couponNo")//persist merge
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no", nullable = false)
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;
}
