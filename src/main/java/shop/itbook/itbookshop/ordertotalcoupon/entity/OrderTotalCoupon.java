package shop.itbook.itbookshop.ordertotalcoupon.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.itbook.itbookshop.coupon.entity.Coupon;

/**
 * 쿠폰이 장바구니 전체에 사용할 수 있는 쿠폰 인지를 구별하기 위한 테이블의 엔터티 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class OrderTotalCoupon {

    @EmbeddedId
    private Pk pk;

    @MapsId("couponNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {

        @Column(name = "coupon_no")
        private Integer couponNo;

    }
}
