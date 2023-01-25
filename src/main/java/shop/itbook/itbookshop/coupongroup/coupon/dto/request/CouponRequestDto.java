package shop.itbook.itbookshop.coupongroup.coupon.dto.request;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 송다혜
 * @since 1.0
 */
@Getter
public class CouponRequestDto {

    @NotNull(message = "")
    @Length(min = 1, max = 20, message = "")
    private String name;

    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Long amount;

    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Integer percent;

    @NotNull(message = "")
    @PositiveOrZero(message = "")
    private Long point;

    @PositiveOrZero(message = "")
    private Long standardAmount;

    @PositiveOrZero(message = "")
    private Long maxDiscountAmount;

    @NotNull(message = "")
    private LocalDateTime couponCreatedAt;

    @NotNull(message = "")
    private LocalDateTime couponExpiredAt;

    @NotNull(message = "")
    private LocalDateTime couponModifiedAt;

    private String image;

    @NotNull(message = "")
    @Length(min = 1, max = 255, message = "")
    private String code;

    @NotNull(message = "")
    private boolean isReserved;
}
