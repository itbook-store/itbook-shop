package shop.itbook.itbookshop.category.service.adminapi.impl;

import java.util.List;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;

/**
 * CategoryAdminService 를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryAdminServiceImpl implements CategoryAdminService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer saveCategory(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> findCategoryList() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseDto> findCategoryChildList(Integer categoryNo) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {
        return null;
    }
}
