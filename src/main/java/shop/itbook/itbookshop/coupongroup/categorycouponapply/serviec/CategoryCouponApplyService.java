package shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface CategoryCouponApplyService {

    void saveCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo,
                                                    List<OrderProduct> orderProducts);

    void cancelCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo);
}
