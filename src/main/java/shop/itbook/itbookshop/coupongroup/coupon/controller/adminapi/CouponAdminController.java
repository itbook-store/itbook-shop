package shop.itbook.itbookshop.coupongroup.coupon.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;


/**
 * 관리자의 쿠폰 서비스 컨트롤러 클레스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupons")
public class CouponAdminController {

    private final CouponService couponService;

    /**
     * 쿠폰 탬플릿을 발급하는 메소드입니다.
     *
     * @param couponRequestDto 쿠폰의 정보를 담고있는 dto 객체입니다.
     * @return 저장한 쿠폰 번호를 ResponseEntity 에 담아 반환합니다.
     */
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<CouponNoResponseDto>> couponAdd(
        @Valid @RequestBody CouponRequestDto couponRequestDto) {

        CouponNoResponseDto couponNoResponseDto =
            new CouponNoResponseDto(couponService.addCoupon(couponRequestDto));

        CommonResponseBody<CouponNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                CouponResultMessageEnum.COUPON_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            couponNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 쿠폰의 리스트를 반환하는 메소드 입니다.
     *
     * @param pageable the pageable
     * @return 쿠폰 정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<AdminCouponListResponseDto>>> couponList(
        Pageable pageable) {

        Page<AdminCouponListResponseDto> page =
            couponService.findByCouponList(pageable);

        PageResponse<AdminCouponListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<AdminCouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 쿠폰의 종류별로 분류해서 보여주는 클레스 입니다.
     *
     * @param pageable   the pageable
     * @param couponType the coupon type
     * @return the response entity
     */
    @GetMapping("/list/{couponType}")
    public ResponseEntity<CommonResponseBody<PageResponse<AdminCouponListResponseDto>>> couponTypeList(
        Pageable pageable, @PathVariable String couponType) {

        Page<AdminCouponListResponseDto> page =
            couponService.findByCouponAtCouponTypeList(pageable, couponType);

        PageResponse<AdminCouponListResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<AdminCouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/list/all/{couponType}")
    public ResponseEntity<CommonResponseBody<List<AdminCouponListResponseDto>>> couponTypeListAll(
        @PathVariable String couponType) {

        CommonResponseBody<List<AdminCouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                couponService.findByAvailableCouponDtoByCouponType(couponType));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
