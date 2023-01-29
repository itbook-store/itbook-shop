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
 * 상품을 등록 및 수정하기 위한 데이터를 전달하는 상품과 도서 정보가 담긴 requestDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
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
    private Boolean isExposed;

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
    private Double discountPercent;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    private String isbn;

    @NotNull(message = "null을 허용하지 않습니다.")
    @PositiveOrZero(message = "페이지 수는 0페이지 이상이어야 합니다.")
    private Integer pageCount;

    @NotNull(message = "null을 허용하지 않습니다.")
    private String bookCreatedAt;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isEbook;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "이름 길이는 1자-20자가 되어야 합니다.")
    private String publisherName;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "이름 길이는 1자-255자가 되어야 합니다.")
    private String authorName;

    @Setter
    private String fileThumbnailsUrl;
    @Setter
    private String fileEbookUrl;

}
