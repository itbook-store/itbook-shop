package shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.repository.OrderTotalCouponApplyRepositoy;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.service.OrderTotalCouponApplyService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderTotalCouponApplyServiceImpl implements OrderTotalCouponApplyService {

    private final OrderTotalCouponApplyRepositoy orderTotalCouponApplyRepositoy;
    private final CouponIssueService couponIssueService;


    @Override
    @Transactional
    public void saveOrderTotalCouponApplyAndChangeCouponIssue(Long couponIssueNo, Order order) {
        CouponIssue usedCouponIssue = couponIssueService.usingCouponIssue(couponIssueNo);
        orderTotalCouponApplyRepositoy.save(
            new OrderTotalCouponApply(usedCouponIssue.getCouponIssueNo(), order));
    }

    @Override
    @Transactional
    public void cancelOrderTotalCouponApplyAndChangeCouponIssue(Long couponIssueNo) {
        CouponIssue canceledCouponIssue = couponIssueService.cancelCouponIssue(couponIssueNo);
        if (Objects.isNull(canceledCouponIssue)) {
            return;
        }
        orderTotalCouponApplyRepositoy.deleteById(canceledCouponIssue.getCouponIssueNo());
    }

}
