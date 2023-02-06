package shop.itbook.itbookshop.coupongroup.productcoupon.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.categorycoupon.resultmessageenum.CategoryCouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.productcoupon.dto.request.ProductCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.resultmessageenum.ProductCouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.productcoupon.service.ProductCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/api/product-coupon")
@RequiredArgsConstructor
public class ProductCouponAdminController {

    private final ProductCouponService productCouponService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<CouponNoResponseDto>> couponAdd(
        @Valid @RequestBody ProductCouponRequestDto productCouponRequestDto) {

        CouponNoResponseDto couponNoResponseDto =
            new CouponNoResponseDto(
                productCouponService.addProductCoupon(productCouponRequestDto));

        CommonResponseBody<CouponNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CouponResultMessageEnum.COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            couponNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    @GetMapping
    public ResponseEntity<CommonResponseBody<Page<CouponListResponseDto>>> findProductCouponList(
        @PageableDefault Pageable pageable) {

        Page<CouponListResponseDto> couponList =
            productCouponService.findProductCouponList(pageable);

        CommonResponseBody<Page<CouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCouponResultMessageEnum.PRODUCT_COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                couponList);

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
