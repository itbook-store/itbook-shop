package shop.itbook.itbookshop.coupongroup.productcouponapply.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.ordertotalcouponapply.entity.OrderTotalCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.entity.ProductCouponApply;
import shop.itbook.itbookshop.coupongroup.productcouponapply.repository.ProductCouponApplyRepository;
import shop.itbook.itbookshop.coupongroup.productcouponapply.service.ProductCouponApplyService;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCouponApplyServiceImpl implements ProductCouponApplyService {

    private final ProductCouponApplyRepository productCouponApplyRepository;

    @Override
    @Transactional
    public void saveProductCouponApplyAndChangeCouponIssue(Long couponIssueNo,
                                                           OrderProduct orderProduct) {
        productCouponApplyRepository.save(
            new ProductCouponApply(couponIssueNo, orderProduct));
    }

    @Override
    @Transactional
    public void cancelProductCouponApplyAndChangeCouponIssue(Long couponIssueNo) {

        if (productCouponApplyRepository.existsById(couponIssueNo)) {
            productCouponApplyRepository.deleteById(couponIssueNo);
        }
    }

}