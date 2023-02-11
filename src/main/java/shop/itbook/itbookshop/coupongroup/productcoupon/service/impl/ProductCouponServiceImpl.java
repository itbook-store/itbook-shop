package shop.itbook.itbookshop.coupongroup.productcoupon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductCouponServiceImpl implements ProductCouponService {

    private final ProductCouponRepository productCouponRepository;
    private final CouponService couponService;
    private final ProductService productService;

    @Override
    public Long addProductCoupon(ProductCouponRequestDto productCouponRequestDto) {
        Product product = productService.findProductEntity(productCouponRequestDto.getProductNo());
        Long couponNo = couponService.addCoupon(productCouponRequestDto.getCouponRequestDto());
        ProductCoupon productCoupon =
            new ProductCoupon(couponNo, product);
        return productCouponRepository.save(productCoupon).getCouponNo();
    }

    @Override
    public Page<AdminCouponListResponseDto> findProductCouponPageList(Pageable pageable) {
        return productCouponRepository.findProductCouponPageList(pageable);
    }
}
