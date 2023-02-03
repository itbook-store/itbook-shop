package shop.itbook.itbookshop.coupongroup.coupon.transfer;

import java.time.LocalDateTime;
import java.util.Objects;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponTransfer {

    private CouponTransfer() {
    }

    public static Coupon dtoToEntity(CouponRequestDto dto) {
        LocalDateTime createAt = LocalDateTime.parse(dto.getCouponCreatedAt());
        LocalDateTime expiredAt = LocalDateTime.parse(dto.getCouponExpiredAt());
        LocalDateTime modifiedAt = null;
        if (!Objects.isNull(dto.getCouponModifiedAt())) {
            modifiedAt = LocalDateTime.parse(dto.getCouponModifiedAt());
        }

        return Coupon.builder()
            .name(dto.getName())
            .amount(dto.getAmount())
            .percent(dto.getPercent())
            .point(dto.getPoint())
            .standardAmount(dto.getStandardAmount())
            .maxDiscountAmount(dto.getMaxDiscountAmount())
            .couponCreatedAt(createAt)
            .couponExpiredAt(expiredAt)
            .couponModifiedAt(modifiedAt)
            .image(dto.getImage())
            .code(dto.getCode())
            .isDuplicateUse(dto.isDuplicateUse())
            .totalQuantity(dto.getTotalQuantity())
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
            .isDuplicateUse(coupon.getIsDuplicateUse())
            .totalQuantity(coupon.getTotalQuantity())
            .couponTypeEnum(coupon.getCouponType().getCouponTypeEnum())
            .issuedQuantity(coupon.getIssuedQuantity())
            .build();
    }
}
