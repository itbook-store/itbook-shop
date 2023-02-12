package shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.CategoryCouponApplyService;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryCouponApplyServiceImpl implements CategoryCouponApplyService {

    private final CategoryCouponApplyRepository categoryCouponApplyRepository;

    private final CouponIssueService couponIssueService;

    @Override
    @Transactional
    public void saveCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo,
                                                             List<OrderProduct> orderProducts) {
        CouponIssue usedCouponIssue = couponIssueService.usingCouponIssue(couponIssueNo);
        List<CategoryCouponApply> categoryCouponApplyList = new ArrayList<>();
        for (OrderProduct orderProduct : orderProducts) {
            categoryCouponApplyList.add(new CategoryCouponApply(usedCouponIssue, orderProduct));
        }
        categoryCouponApplyRepository.saveAll(categoryCouponApplyList);
    }

    @Override
    @Transactional
    public void cancelCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo) {
        CouponIssue canceledCouponIssue = couponIssueService.cancelCouponIssue(couponIssueNo);
        categoryCouponApplyRepository.deleteAllByCouponIssue_CouponIssueNo(
            canceledCouponIssue.getCouponIssueNo());
    }
}
