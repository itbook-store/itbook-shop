package shop.itbook.itbookshop.category.dto.request;

import lombok.Getter;

/**
 * 카테고리 생성 및 수정 요청시 정보를 보관할 DTO 입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public class CategoryRequestDto {

    private Integer parentCategoryNo;
    private String categoryName;
    private boolean isHidden;
}
