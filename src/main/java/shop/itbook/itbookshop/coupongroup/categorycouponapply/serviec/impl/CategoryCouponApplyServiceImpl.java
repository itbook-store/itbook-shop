package shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.entity.CategoryCouponApply;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.repository.CategoryCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.categorycouponapply.serviec.CategoryCouponApplyService;
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

    @Override
    @Transactional
    public void saveCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo,
                                                             OrderProduct orderProduct) {
        categoryCouponApplyRepository.save(new CategoryCouponApply(couponIssueNo, orderProduct));
    }

    @Override
    @Transactional
    public void cancelCategoryCouponApplyAndChangeCouponIssues(Long couponIssueNo) {

        if (categoryCouponApplyRepository.existsById(couponIssueNo)) {
            categoryCouponApplyRepository.deleteById(couponIssueNo);
        }
    }
}
