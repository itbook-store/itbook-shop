package shop.itbook.itbookshop.category.service.adminapi.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.adminapi.CategoryAdminService;
import shop.itbook.itbookshop.category.transfer.CategoryTransfer;

/**
 * CategoryAdminService 를 구현한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer saveCategory(CategoryRequestDto categoryRequestDto) {

        Category category = CategoryTransfer.dtoToEntity(categoryRequestDto);

        category.setParentCategory(
            this.findCategoryEntity(categoryRequestDto.getParentCategoryNo()));
        return category.getCategoryNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseProjectionDto> findCategoryList() {

        return categoryRepository.findCategoryList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryChildResponseProjectionDto> findCategoryChildList(Integer categoryNo) {

        return categoryRepository.findCategoryThroughParentCategoryNo(categoryNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findCategoryEntity(Integer categoryNo) {
        return categoryRepository.findById(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto findCategory(Integer categoryNo) {

        Category category = findCategoryEntity(categoryNo);
        return CategoryTransfer.entityToDto(category);
    }
}
