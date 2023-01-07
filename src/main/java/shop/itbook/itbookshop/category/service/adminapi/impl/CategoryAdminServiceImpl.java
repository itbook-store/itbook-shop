package shop.itbook.itbookshop.category.service.adminapi.impl;

import java.util.List;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;

/**
 * CategoryAdminService 를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryAdminServiceImpl implements CategoryAdminService {
    @Override
    public CategoryNoResponseDto saveCategory(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    public List<CategoryResponseDto> findCategoryList() {
        return null;
    }

    @Override
    public List<CategoryResponseDto> findCategoryChildList(String categoryNo) {
        return null;
    }

    @Override
    public CategoryResponseDto findCategory(String categoryNo) {
        return null;
    }
}
