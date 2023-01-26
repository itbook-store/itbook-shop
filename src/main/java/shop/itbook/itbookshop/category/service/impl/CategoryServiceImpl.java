package shop.itbook.itbookshop.category.service.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.exception.NoParentCategoryException;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private static final int NO_PARENT_NUMBER = 0;
    private static final int MAIN_CATEGORY_LEVEL = 0;
    private static final int CHILD_CATEGORY_LEVEL = MAIN_CATEGORY_LEVEL + 1;

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

        return saveCategoryAndGetCategoryNo(categoryRequestDto, category,
            isNoParentCategory);
    }

    private Integer saveCategoryAndGetCategoryNo(CategoryRequestDto categoryRequestDto,
                                                 Category category, boolean isNoParentCategory) {
        if (isNoParentCategory) {
            category.setLevel(MAIN_CATEGORY_LEVEL);
            categoryRepository.modifyMainCategorySequence(1);

            category = categoryRepository.save(category);
            category.setParentCategory(category);

            return category.getCategoryNo();
        }

        settingParentCategory(categoryRequestDto.getParentCategoryNo(), category);
        categoryRepository.modifyChildCategorySequence(category.getParentCategory().getCategoryNo(),
            1);
        category = categoryRepository.save(category);

        return category.getCategoryNo();
    }

    /**
     * 상위카테고리가 있는경우 해당 번호로 카테고리를 찾아서 넣어주는 메소드입니다.
     * 또한 상위카테고리가 있을경우 상위카테고리의 레벨에서 하나를 더 추가해서 삽입해 줍니다.
     *
     * @param parentCategoryNo 상위카테고리 번호입니다.
     * @param category         현제 저장이 진행되고있는 카테고리 객체입니다.
     * @author 최겸준
     */
    private void settingParentCategory(Integer parentCategoryNo, Category category) {

        Category parentCategory = this.findCategoryEntity(parentCategoryNo);
        category.setParentCategory(parentCategory);
        category.setLevel(CHILD_CATEGORY_LEVEL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryListResponseDto> findCategoryListByEmployee() {

        List<CategoryListResponseDto> categoryListByEmployee =
            categoryRepository.findCategoryListByEmployee();

        settingMainCategoryCount(categoryListByEmployee);

        return categoryListByEmployee;
    }

    @Override
    public List<CategoryListResponseDto> findCategoryListByNotEmployee() {
        List<CategoryListResponseDto> categoryListByNotEmployee =
            categoryRepository.findCategoryListByNotEmployee();

        settingMainCategoryCount(categoryListByNotEmployee);

        return categoryListByNotEmployee;
    }

    private static void settingMainCategoryCount(
        List<CategoryListResponseDto> categoryList) {

        CategoryListResponseDto mainCategoryDto = null;
        Long sum = 0L;
        for (CategoryListResponseDto categoryListResponseDto : categoryList) {
            if (Objects.equals(categoryListResponseDto.getLevel(), MAIN_CATEGORY_LEVEL)) {
                if (sum != 0L) {
                    mainCategoryDto.setCount(sum);
                    sum = 0L;
                }
                mainCategoryDto = categoryListResponseDto;
                continue;
            }

            sum += categoryListResponseDto.getCount();
        }

        if (Objects.isNull(mainCategoryDto)) {
            return;
        }
        mainCategoryDto.setCount(sum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryListResponseDto> findCategoryListAboutChild(Integer categoryNo) {

        List<CategoryListResponseDto> categoryListAboutChild =
            categoryRepository.findCategoryListAboutChild(categoryNo);

        return categoryListAboutChild;
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
    public CategoryDetailsResponseDto findCategoryDetailsResponseDto(
        Integer categoryNo) {

        Category category = this.findCategoryEntityFetch(categoryNo);
        return CategoryTransfer.entityToDto(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyCategory(Integer categoryNo, CategoryModifyRequestDto categoryRequestDto) {
        Category category = findCategoryEntityFetch(categoryNo);
        category.setCategoryName(categoryRequestDto.getCategoryName());
        category.setIsHidden(categoryRequestDto.getIsHidden());
    }

    @Override
    @Transactional
    public void modifyChildSequence(Integer categoryNo, Integer hopingPositionCategoryNo) {
        Category hopingPositionCategory = this.findCategoryEntity(hopingPositionCategoryNo);
        Integer hopingSequence = hopingPositionCategory.getSequence();

        Category currentCategory = getSequenceNotSameCategory(categoryNo, hopingSequence);
        if (Objects.isNull(currentCategory)) {
            return;
        }

        Category parentCategoryOfCurrentCategory = currentCategory.getParentCategory();
        if (Objects.isNull(parentCategoryOfCurrentCategory)) {
            throw new NoParentCategoryException();
        }

        Category ParentCategoryOfhopingPositionCategory =
            hopingPositionCategory.getParentCategory();
        Integer parentCategoryNoOfHopingPositionCategory =
            ParentCategoryOfhopingPositionCategory.getCategoryNo();
        Integer parentCategoryNoOfCurrentCategory = parentCategoryOfCurrentCategory.getCategoryNo();

        categoryRepository.modifyChildCategorySequence(parentCategoryNoOfHopingPositionCategory,
            hopingSequence);
        currentCategory.setSequence(hopingSequence);
        if (Objects.equals(parentCategoryNoOfCurrentCategory,
            parentCategoryNoOfHopingPositionCategory)) {
            return;
        }

        currentCategory.setParentCategory(ParentCategoryOfhopingPositionCategory);
    }

    @Override
    @Transactional
    public void modifyMainSequence(Integer categoryNo, Integer sequence) {

        Category category = getSequenceNotSameCategory(categoryNo, sequence);
        if (Objects.isNull(category)) {
            return;
        }

        categoryRepository.modifyMainCategorySequence(sequence);
        category.setSequence(sequence);
    }

    private Category getSequenceNotSameCategory(Integer categoryNo, Integer sequence) {
        Category category = this.findCategoryEntity(categoryNo);
        if (Objects.equals(category.getSequence(), sequence)) {
            return null;
        }
        return category;
    }

    @Override
    @Transactional
    public void modifyCategoryHidden(Integer categoryNo) {
        Category category = findCategoryEntityFetch(categoryNo);
        category.setIsHidden(!category.getIsHidden());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeCategory(Integer categoryNo) {
        categoryRepository.deleteById(categoryNo);
    }

    @Override
    public List<CategoryListResponseDto> findMainCategoryList() {
        return categoryRepository.findMainCategoryList();
    }
}
