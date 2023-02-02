package shop.itbook.itbookshop.coupongroup.coupon.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 쿠폰의 정보를 담을 request dto 객체 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Getter
public class CouponRequestDto {

    @NotNull(message = "쿠폰 타입은 공란 일 수 없습니다.")
    private String couponType;

    private String userId;

    private Integer categoryNo;

    private Long searchResult;

    @NotNull(message = "이름은 공란 일 수 없습니다.")
    @Length(min = 1, max = 20, message = "이름의 길이는 20자를 넘을 수 없습니다.")
    private String name;

    @NotNull(message = "쿠폰의 금액은 공백 일 수 없습니다.")
    @PositiveOrZero(message = "쿠폰의 금액은 음수 일 수 없습니다.")
    private Long amount;

    @NotNull(message = "쿠폰의 할인 퍼센트는 공백 일 수 없습니다.")
    @PositiveOrZero(message = "쿠폰의 할인 퍼센트는 음수 일 수 없습니다.")
    private Integer percent;

    @NotNull(message = "쿠폰의 포인트 금액은 공백 일 수 없습니다.")
    @PositiveOrZero(message = "쿠폰의 포인트 금액은 음수 일 수 없습니다.")
    private Long point;

    @PositiveOrZero(message = "쿠폰의 최소 사용 가능 금액은 음수 일 수 없습니다.")
    private Long standardAmount;

    @Positive(message = "쿠폰의 최대 할인 금액은 0이거나 음수 일 수 없습니다.")
    private Long maxDiscountAmount;

    @NotNull(message = "쿠폰의 생성 가능일은 공백 일 수 없습니다.")
    private String couponCreatedAt;

    @NotNull(message = "쿠폰의 만료일은 공백 일 수 없습니다.")
    private String couponExpiredAt;

    private String couponModifiedAt;

    private String image;

    @Length(min = 1, max = 255, message = "쿠폰의 코드 길이는 255자를 넘을 수 없습니다.")
    private String code;

    @NotNull(message = "중복 쿠폰여부는 공백 일 수 없습니다.")
    private boolean isDuplicateUse;

    @Positive(message = "쿠폰 발급수량이 음수 일 수는 없습니다.")
    private Integer totalQuantity;
}
