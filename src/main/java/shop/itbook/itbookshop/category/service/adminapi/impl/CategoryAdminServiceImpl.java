package shop.itbook.itbookshop.category.service.adminapi.impl;

import java.util.List;
import java.util.Objects;
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

    private static final int NO_PARENT_NUMBER = 0;
    private final CategoryRepository categoryRepository;


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer saveCategory(CategoryRequestDto categoryRequestDto) {

        Category category = CategoryTransfer.dtoToEntity(categoryRequestDto);
        boolean hasParentCategory =
            !Objects.equals(categoryRequestDto.getParentCategoryNo(), NO_PARENT_NUMBER);
        if (hasParentCategory) {
            putParentCategory(categoryRequestDto, category);
            return category.getCategoryNo();
        }

        categoryRepository.save(category);
        category.setParentCategory(
            this.findCategoryEntity(categoryRequestDto.getParentCategoryNo()));
        return category.getCategoryNo();
    }

    private void putParentCategory(CategoryRequestDto categoryRequestDto, Category category) {
        Category parentCategory =
            categoryRepository.findById(categoryRequestDto.getParentCategoryNo())
                .orElseThrow(CategoryNotFoundException::new);

        category.setParentCategory(parentCategory);
        categoryRepository.save(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryResponseProjectionDto> findCategoryList() {

        return categoryRepository.findCategoryListFetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryChildResponseProjectionDto> findCategoryChildList(Integer categoryNo) {

        return categoryRepository.findCategoryListFetchThroughParentCategoryNo(categoryNo);
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
    public Category findCategoryEntityFetch(Integer categoryNo) {
        return categoryRepository.findCategoryFetch(categoryNo)
            .orElseThrow(CategoryNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryResponseDto findCategoryResponseDtoThroughCategoryNo(Integer categoryNo) {

        Category category = this.findCategoryEntityFetch(categoryNo);
        return CategoryTransfer.entityToDto(category);
    }
}
