package shop.itbook.itbookshop.category.transfer;

import java.util.Objects;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * 카테고리 엔티티에서 dto로 dto에서 엔티티로 변경을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryTransfer {

    public static CategoryResponseDto entityToDto(Category category) {

        return CategoryResponseDto.builder()
            .categoryNo(category.getCategoryNo())
            .categoryName(category.getCategoryName())
            .parentCategoryNo(category.getParentCategory().getCategoryNo())
            .isHidden(category.isHidden())
            .build();
    }

    public static Category dtoToEntity(CategoryRequestDto dto) {

        return new Category(dto.getCategoryName(), dto.isHidden());
    }
}
