package shop.itbook.itbookshop.category.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 카테고리의 모든 정보를 반환하는 dto 입니다.
 *
 * @author 최겸준
 * @since 1.0
 */

@Getter
@Builder
public class CategoryResponseDto {

    private Integer categoryNo;
    private Integer parentCategoryNo;
    private String categoryName;
    private Boolean isHidden;
}
