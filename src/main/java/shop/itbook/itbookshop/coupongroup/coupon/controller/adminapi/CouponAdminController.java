package shop.itbook.itbookshop.coupongroup.coupon.controller.adminapi;

import java.util.List;
import java.util.Objects;
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
import shop.itbook.itbookshop.coupongroup.categorycoupon.dto.request.CategoryCouponRequestDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.service.CategoryCouponService;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponNoResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.resultmessageenum.CouponResultMessageEnum;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.service.CouponIssueService;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * 관리자의 쿠폰 서비스 컨트롤러 클레스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/coupon")
public class CouponAdminController {

    private final CouponService couponService;

    /**
     * 쿠폰 탬플릿을 발급하는 메소드입니다.
     *
     * @param couponRequestDto 쿠폰의 정보를 담고있는 dto 객체입니다.
     * @return 저장한 쿠폰 번호를 ResponseEntity 에 담아 반환합니다.
     */
    @PostMapping
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
     * @return 쿠폰 정보의 리스트를 ResponseEntity 에 담아 반환합니다.
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<Page<CouponListResponseDto>>> couponList(Pageable pageable) {

        CommonResponseBody<Page<CouponListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    CouponResultMessageEnum.COUPON_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                couponService.findByCouponList(pageable));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
