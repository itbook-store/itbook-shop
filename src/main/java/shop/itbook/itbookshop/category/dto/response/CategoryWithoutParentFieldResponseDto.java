package shop.itbook.itbookshop.category.dto.response;

import lombok.Getter;

/**
 * 부모 번호를 통해서 자식객체들의 정보를 저장할때 사용하는 dto입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Getter
public class CategoryWithoutParentFieldResponseDto {

    private Integer categoryNo;

    private String categoryName;

    private Boolean isHidden;
}
