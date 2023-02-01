package shop.itbook.itbookshop.coupongroup.categorycoupon.controller.adminapi;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.response.CategoryCouponListDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon/category-coupon")
public class CategoryCouponController {

    private final CategoryCouponService categoryCouponService;

    @GetMapping
    public ResponseEntity<CommonResponseBody<List<CouponListResponseDto>>> findCategoryCouponList(
        @PageableDefault Pageable pageable) {

        List<CouponListResponseDto> couponList =
            categoryCouponService.findCategoryCouponList(pageable).getContent()
                .stream().map(CategoryCouponListDto::getCoupon).collect(Collectors.toList());

        CommonResponseBody<List<CouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                couponList);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<CouponNoResponseDto>> couponAdd(
        @Valid @RequestBody CategoryCouponRequestDto categoryCouponRequestDto) {

        CouponNoResponseDto couponNoResponseDto =
            new CouponNoResponseDto(
                categoryCouponService.addCategoryCoupon(categoryCouponRequestDto));

        CommonResponseBody<CouponNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CouponResultMessageEnum.COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            couponNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
