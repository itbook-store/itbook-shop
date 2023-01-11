package shop.itbook.itbookshop.category.transfer;

import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * 카테고리에 대해 entity 와 dto 간의 자유로운 변환을 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryTransfer {

    private CategoryTransfer() {
    }


    /**
     * entity 로 넘어온 카테고리를 반환을 위한 dto 로 변경하는 기능입니다.
     *
     * @param category dto 로 변경해야할 정보를 담은 category entity 입니다.
     * @return dto 형태로 만들어서 반환합니다.
     * @author 최겸준
     */
    public static CategoryAllFieldResponseDto entityToDto(Category category) {

        return CategoryAllFieldResponseDto.builder()
            .categoryNo(category.getCategoryNo())
            .categoryName(category.getCategoryName())
            .isHidden(category.getIsHidden())
            .parentCategoryNo(category.getParentCategory().getCategoryNo())
            .parentCategoryName(category.getCategoryName())
            .parentCategoryIsHidden(category.getIsHidden())
            .build();
    }

    /**
     * dto로 넘어온 정보들을 Category 엔티티로 변경하는 기능입니다.
     *
     * @param categoryRequestDto entity 로 변경하기위한 정보를 담은 dto 입니다.
     * @return 카테고리 엔티티를 만들어서 반환합니다.
     * @author 최겸준
     */
    public static Category dtoToEntity(CategoryRequestDto categoryRequestDto) {

        return new Category(categoryRequestDto.getCategoryName(), categoryRequestDto.getIsHidden());
    }
}
