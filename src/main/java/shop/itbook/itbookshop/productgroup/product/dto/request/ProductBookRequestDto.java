package shop.itbook.itbookshop.productgroup.product.dto.request;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductBookRequestDto {
    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "이름 길이는 1자-255자가 되어야 합니다.")
    private String productName;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "상세설명 길이는 1자-255자가 되어야 합니다.")
    private String simpleDescription;

    private String detailsDescription;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isSelled;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isForceSoldOut;

    @NotNull(message = "null을 허용하지 않습니다.")
    @PositiveOrZero(message = "재고는 0개 이상이어야 합니다.")
    private Integer stock;

    @NotNull(message = "카테고리를 선택해주세요.")
    private List<Integer> categoryNoList;

    @Min(value = 0, message = "적립율은 0% 이상이어야 합니다.")
    @Max(value = 100, message = "적립율은 최대 100%입니다.")
    private Integer increasePointPercent;

    @NotNull(message = "null을 허용하지 않습니다.")
    @Min(value = 0, message = "매입원가는 0원 이상이어야 합니다.")
    private Long rawPrice;

    @NotNull(message = "null을 허용하지 않습니다.")
    @PositiveOrZero(message = "정가는 0원 이상이어야 합니다.")
    private Long fixedPrice;

    @Min(value = 0, message = "할인율은 0% 이상이어야 합니다.")
    @Max(value = 100, message = "할인율은 최대 100%입니다.")
    private Integer discountPercent;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isPointApplying;

    private Boolean isPointApplyingBasedSellingPrice;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isSubscription;

    @Setter
    private String fileThumbnailsUrl;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(min = 10, max = 13, message = "isbn은 10자-13자가 되어야 합니다.")
    private String isbn;

    @NotNull(message = "null을 허용하지 않습니다.")
    @PositiveOrZero(message = "페이지 수는 0p 이상이어야 합니다.")
    private Integer pageCount;

    @NotNull(message = "null을 허용하지 않습니다.")
    private String bookCreatedAt;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isEbook;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "출판사 길이는 1자-20자가 되어야 합니다.")
    private String publisherName;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "작가 이름은 1자-255자가 되어야 합니다.")
    private String authorName;

    @Setter
    private String fileEbookUrl;
}
