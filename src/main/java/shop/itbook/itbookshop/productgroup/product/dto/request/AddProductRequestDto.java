package shop.itbook.itbookshop.productgroup.product.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 상품을 등록하기 위한 데이터를 전달하는 requestDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequestDto {

    @Length(min = 1, max = 255)
    private String name;
    @Length(min = 1, max = 255)
    @NotNull
    private String simpleDescription;
    private String detailsDescription;
    private Integer stock;
    @NotNull
    private boolean isSelled;
    @NotNull
    private boolean isDeleted;
    @NotNull
    private boolean isSubscription;
    @NotNull
    private String thumbnailUrl;
    @NotNull
    private Long fixedPrice;
    private Integer increasePointPercent;
    private Integer discountPercent;
    @NotNull
    private Long rawPrice;
}
