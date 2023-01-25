package shop.itbook.itbookshop.coupongroup.coupon.transfer;

import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponTransfer {

    private CouponTransfer() {
    }

    public static Coupon dtoToEntity(CouponRequestDto dto) {

        return Coupon.builder()
            .name(dto.getName())
            .amount(dto.getAmount())
            .percent(dto.getPercent())
            .standardAmount(dto.getStandardAmount())
            .maxDiscountAmount(dto.getMaxDiscountAmount())
            .couponCreatedAt(dto.getCouponCreatedAt())
            .couponExpiredAt(dto.getCouponExpiredAt())
            .couponModifiedAt(dto.getCouponModifiedAt())
            .image(dto.getImage())
            .code(dto.getCode())
            .isReserved(dto.isReserved())
            .build();
    }

    public static CouponResponseDto entityToDto(Coupon coupon) {

        return CouponResponseDto.builder()
            .name(coupon.getName())
            .amount(coupon.getAmount())
            .percent(coupon.getPercent())
            .standardAmount(coupon.getStandardAmount())
            .maxDiscountAmount(coupon.getMaxDiscountAmount())
            .couponCreatedAt(coupon.getCouponCreatedAt())
            .couponExpiredAt(coupon.getCouponExpiredAt())
            .couponModifiedAt(coupon.getCouponModifiedAt())
            .image(coupon.getImage())
            .code(coupon.getCode())
            .isReserved(coupon.getIsReserved())
            .build();
    }
}
