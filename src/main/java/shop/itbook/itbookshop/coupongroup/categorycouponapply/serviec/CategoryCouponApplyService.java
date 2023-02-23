package shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec;

import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CategoryCouponApplyService {

    void saveCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo,
                                                    OrderProduct orderProduct);

    void cancelCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo);
}
