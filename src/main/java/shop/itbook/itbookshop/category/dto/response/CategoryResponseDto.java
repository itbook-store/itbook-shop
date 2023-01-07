package shop.itbook.itbookshop.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리의 모든 정보를 반환하는 dto 입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {

    private Integer categoryNo;
    private Integer parentCategoryNo;
    private String categoryName;
    private boolean isHidden;
}
