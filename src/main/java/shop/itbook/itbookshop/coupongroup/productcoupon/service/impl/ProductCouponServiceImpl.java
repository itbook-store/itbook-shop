package shop.itbook.itbookshop.coupongroup.productcoupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductCouponServiceImpl {

    private final ProductCouponRepository productCouponRepository;
    private final CouponService couponService;
    private final ProductService productService;

//    @Override
//    public Long addProductCoupon(){
//
//    }

}
