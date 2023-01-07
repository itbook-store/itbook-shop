package shop.itbook.itbookshop.category.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;

/**
 * 관리자에대한 카테고리 서비스를 제공하는 인터페이스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryAdminService {

    CategoryNoResponseDto saveCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> findCategoryList();

    List<CategoryResponseDto> findCategoryChildList(String categoryNo);

    CategoryResponseDto findCategory(String categoryNo);
}
