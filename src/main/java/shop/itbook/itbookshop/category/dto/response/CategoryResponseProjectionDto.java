package shop.itbook.itbookshop.category.dto.response;

import lombok.Getter;
import lombok.Setter;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * 모든 카테고리를 반환할때 프로젝션할 용도로 사용될 인터페이스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryResponseProjectionDto {

    Integer getCategoryNo();

    Integer getParentCategory_categoryNo();

    String getCategoryName();

    boolean getIsHidden();
}
