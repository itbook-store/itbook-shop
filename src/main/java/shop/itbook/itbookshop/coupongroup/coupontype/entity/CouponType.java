package shop.itbook.itbookshop.coupongroup.coupontype.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.itbook.itbookshop.coupongroup.coupontype.converter.CouponTypeEnumConverter;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.productgroup.producttype.converter.impl.ProductTypeEnumConverter;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon_type")
@Entity
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_type_no", nullable = false)
    private Integer couponTypeNo;

    @Convert(converter = CouponTypeEnumConverter.class)
    @Column(name = "coupon_type_name", nullable = false)
    private CouponTypeEnum couponTypeEnum;
}
