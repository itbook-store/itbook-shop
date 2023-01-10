package shop.itbook.itbookshop.category.service.adminapi.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryWithoutParentFieldResponseDto;
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
    public Integer addCategory(CategoryRequestDto categoryRequestDto) {

        Category category = CategoryTransfer.dtoToEntity(categoryRequestDto);
        boolean isNoParentCategory =
            Objects.equals(categoryRequestDto.getParentCategoryNo(), NO_PARENT_NUMBER);

        if (isNoParentCategory) {
            category = categoryRepository.save(category);
            category.setParentCategory(category);
            return category.getCategoryNo();
        }

        settingParentCategory(categoryRequestDto.getParentCategoryNo(), category);
        category = categoryRepository.save(category);
        return category.getCategoryNo();
    }

    /**
     * 상위카테고리가 있는경우 해당 번호로 카테고리를 찾아서 넣어주는 메소드입니다.
     *
     * @param parentCategoryNo 상위카테고리 번호입니다.
     * @param category         현제 저장이 진행되고있는 카테고리 객체입니다.
     * @author 최겸준
     */
    private void settingParentCategory(Integer parentCategoryNo, Category category) {

        Category parentCategory = this.findCategoryEntity(parentCategoryNo);
        category.setParentCategory(parentCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryAllFieldResponseDto> findCategoryList(Boolean isHidden) {

        return categoryRepository.findCategoryListFetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryWithoutParentFieldResponseDto> findCategoryChildList(Integer categoryNo) {

        return categoryRepository.findCategoryChildListThroughParentCategoryNo(categoryNo);
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyCategory(int categoryNo, CategoryRequestDto categoryRequestDto) {
        Category category = findCategoryEntityFetch(categoryNo);
        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setIsHidden(categoryRequestDto.getIsHidden());

        boolean isNoParent =
            Objects.equals(categoryRequestDto.getParentCategoryNo(), NO_PARENT_NUMBER);
        if (isNoParent) {
            return;
        }

        Category parentCategory = findCategoryEntity(categoryRequestDto.getParentCategoryNo());
        category.setParentCategory(parentCategory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeCategory(Integer categoryNo) {
        categoryRepository.deleteById(categoryNo);
    }
}
