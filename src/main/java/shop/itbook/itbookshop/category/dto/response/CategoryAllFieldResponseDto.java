package shop.itbook.itbookshop.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 카테고리 조회중 부모카테고리의 정보까지 모두 반환
 *
 * @author 최겸준
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllCategoryResponseDto {

    private Integer categoryNo;

    private String categoryName;

    private Boolean isHidden;

    private Integer parentCategoryNo;

    private String parentCategoryName;

    private Boolean parentIsHidden;

}
