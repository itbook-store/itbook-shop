package shop.itbook.itbookshop.coupongroup.coupontype.service;

import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CouponTypeService {

    CouponType findCouponType(String couponTypeName);
}
