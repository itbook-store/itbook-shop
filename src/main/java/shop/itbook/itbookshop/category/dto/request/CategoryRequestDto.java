package shop.itbook.itbookshop.category.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 카테고리 생성 및 수정 요청시 정보를 보관할 DTO 입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public class CategoryRequestDto {

    @NotNull(message = "부모 카테고리 번호는 널일수 없습니다.")
    @Min(value = 0, message = "부모 카테고리 번호는 존재하지 않는경우가 가장 작은 값이며 최소값은 0입니다.")
    private Integer parentCategoryNo;

    @Length(min = 1, max = 20, message = "카테고리 명은 최소 한자 이상 스무자 이하여야합니다.")
    @NotBlank(message = "카테고리 명은 null 일수 없으며 빈문자열이나 공백하나만 들어올 수 없습니다.")
    private String categoryName;

    @NotNull(message = "카테고리를 숨길 것인지 말 것인지에 해당하는 값은 필수값입니다.")
    private Boolean isHidden;

    private Integer sequence;
}
