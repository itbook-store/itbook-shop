package shop.itbook.itbookshop.category.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 부모 번호를 통해서 자식객체들의 정보를 저장할때 사용하는 dto입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
@Setter
public class CategoryListResponseDto {

    private Integer categoryNo;

    private Integer parentCategoryNo;

    private String categoryName;

    private Boolean isHidden;

    private Integer level;

    private Integer sequence;

    private Long count;
}
