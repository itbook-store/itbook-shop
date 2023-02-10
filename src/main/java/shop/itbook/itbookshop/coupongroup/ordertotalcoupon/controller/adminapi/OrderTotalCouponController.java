package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.dto.request.OrderTotalCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.service.OrderTotalCouponService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/order-total-coupons")
public class OrderTotalCouponController {

    private final OrderTotalCouponService orderTotalCouponService;
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<CouponNoResponseDto>> couponAdd(
        @Valid @RequestBody OrderTotalCouponRequestDto orderTotalCouponRequestDto) {

        CouponNoResponseDto couponNoResponseDto =
            new CouponNoResponseDto(orderTotalCouponService.addOrderTotalCoupon(orderTotalCouponRequestDto));

        CommonResponseBody<CouponNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CouponResultMessageEnum.COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            couponNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<CouponListResponseDto>>> couponList(
        Pageable pageable) {

        Page<CouponListResponseDto> page =
            orderTotalCouponService.findTotalCouponPageList(pageable);

        PageResponse<CouponListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<CouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
