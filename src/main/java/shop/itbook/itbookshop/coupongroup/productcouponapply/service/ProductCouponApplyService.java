package shop.itbook.itbookshop.coupongroup.productcouponapply.service;

import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductCouponApplyService {
    void saveProductCouponApplyAndChangeCouponIssue(Long couponIssueNo, OrderProduct orderProduct);

    void cancelProductCouponApplyAndChangeCouponIssue(Long couponIssueNo);
}
