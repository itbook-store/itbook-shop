package shop.itbook.itbookshop.category.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.dto.CategoryNoAndProductNoDto;
import shop.itbook.itbookshop.category.dto.request.CategoryModifyRequestDto;
import shop.itbook.itbookshop.category.dto.request.CategoryRequestDto;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.exception.AlreadyAddedCategoryNameException;
import shop.itbook.itbookshop.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.exception.NotChildCategoryException;
import shop.itbook.itbookshop.category.exception.ParentCategoryNotFoundException;
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

    private static final int PARENT_CATEGORY_NO = 0;
    private static final int MAIN_CATEGORY_LEVEL = 0;
    private static final int CHILD_CATEGORY_LEVEL = MAIN_CATEGORY_LEVEL + 1;
    private static final int FIRST_SEQUENCE = 1;

    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer addCategory(CategoryRequestDto categoryRequestDto) {

        Category category = CategoryTransfer.dtoToEntity(categoryRequestDto);

        boolean isParentCategory =
            Objects.equals(categoryRequestDto.getParentCategoryNo(), PARENT_CATEGORY_NO);

        return saveCategoryAndGetCategoryNo(categoryRequestDto, category,
            isParentCategory);
    }

    private Integer saveCategoryAndGetCategoryNo(CategoryRequestDto categoryRequestDto,
                                                 Category category, boolean isParentCategory) {

        if (isParentCategory) {
            this.checkAlreadyAddedCategoryNameAboutParentCategory(categoryRequestDto);

            category.setLevel(MAIN_CATEGORY_LEVEL);
            categoryRepository.modifyMainCategorySequence(FIRST_SEQUENCE);

            category = categoryRepository.save(category);
            category.setParentCategory(category);

            return category.getCategoryNo();
        }

        settingParentCategory(categoryRequestDto.getParentCategoryNo(), category);
        categoryRepository.modifyChildCategorySequence(category.getParentCategory().getCategoryNo(),
            FIRST_SEQUENCE);

        category = this.saveAndCheckAlreadyAddedCategoryNameAboutChildCategory(category);
        return category.getCategoryNo();
    }

    private Category saveAndCheckAlreadyAddedCategoryNameAboutChildCategory(Category category) {
        try {
            category = categoryRepository.save(category);
        } catch (DataIntegrityViolationException e) {
            Throwable rootCause = e.getRootCause();

            String message = "";
            if (Objects.nonNull(rootCause)) {
                message = rootCause.getMessage();
            }

            if (message.contains("category.parentNoAndCategoryName")) {
                throw new AlreadyAddedCategoryNameException();
            }

            throw e;
        }
        return category;
    }

    private void checkAlreadyAddedCategoryNameAboutParentCategory(
        CategoryRequestDto categoryRequestDto) {
        Optional<Category> optionalCategory =
            categoryRepository.findByCategoryNameAndLevel(categoryRequestDto.getCategoryName(),
                MAIN_CATEGORY_LEVEL);

        if (optionalCategory.isPresent()) {
            throw new AlreadyAddedCategoryNameException();
        }
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
        Category parentCategory;
        try {
            parentCategory = this.findCategoryEntity(parentCategoryNo);
        } catch (CategoryNotFoundException e) {
            throw new ParentCategoryNotFoundException();
        }

        category.setParentCategory(parentCategory);
        category.setLevel(CHILD_CATEGORY_LEVEL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryListResponseDto> findCategoryListByEmployee(Pageable pageable) {

        Page<CategoryListResponseDto> page =
            categoryRepository.findCategoryListByEmployee(pageable);

        List<CategoryListResponseDto> categoryListByEmployee = page.getContent();
        List<Integer> mainCategoryNoList = getMainCategoryNoList(categoryListByEmployee);

        List<CategoryNoAndProductNoDto> categoryNoAndProductNoDtoList =
            categoryRepository.getMainCategoryNoAndProductNoForSettingCount(mainCategoryNoList);

        settingCount(categoryListByEmployee, categoryNoAndProductNoDtoList);

        return page;
    }

    @Override
    public Page<CategoryListResponseDto> findCategoryListByNotEmployee(Pageable pageable) {
        return categoryRepository.findCategoryListByNotEmployee(pageable);
    }

    @Override
    public Page<CategoryListResponseDto> findMainCategoryList(Pageable pageable) {

        Page<CategoryListResponseDto> mainCategoryListPage =
            categoryRepository.findMainCategoryList(pageable);

        List<CategoryListResponseDto> mainCategoryList = mainCategoryListPage.getContent();
        List<Integer> mainCategoryNoList = getMainCategoryNoList(mainCategoryList);

        List<CategoryNoAndProductNoDto> categoryNoAndProductNoDtoList =
            categoryRepository.getMainCategoryNoAndProductNoForSettingCount(mainCategoryNoList);

        settingCount(mainCategoryList, categoryNoAndProductNoDtoList);
        return mainCategoryListPage;
    }

    private List<Integer> getMainCategoryNoList(List<CategoryListResponseDto> content) {
        List<Integer> mainCategoryNoList = new ArrayList<>();
        for (CategoryListResponseDto dto : content) {
            if (Objects.equals(dto.getLevel(), MAIN_CATEGORY_LEVEL)) {
                mainCategoryNoList.add(dto.getCategoryNo());
            }
        }

        return mainCategoryNoList;
    }

    private static void settingCount(List<CategoryListResponseDto> categoryListByEmployee,
                                     List<CategoryNoAndProductNoDto> categoryNoAndProductNoDtoList) {

        Map<Integer, Long> mainCategoryNoCountMap = new HashMap<>();

        for (CategoryNoAndProductNoDto dto : categoryNoAndProductNoDtoList) {
            Integer categoryNo = dto.getCategoryNo();
            mainCategoryNoCountMap.put(categoryNo,
                mainCategoryNoCountMap.getOrDefault(categoryNo, 0L) + 1);
        }

        for (CategoryListResponseDto categoryListResponseDto : categoryListByEmployee) {
            if (!Objects.equals(categoryListResponseDto.getLevel(), MAIN_CATEGORY_LEVEL)) {
                continue;
            }

            Integer categoryNo = categoryListResponseDto.getCategoryNo();
            Long productCount = mainCategoryNoCountMap.get(categoryNo);
            if (Objects.isNull(productCount)) {
                categoryListResponseDto.setCount(0L);
                continue;
            }

            categoryListResponseDto.setCount(productCount);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryListResponseDto> findCategoryListAboutChild(Integer categoryNo,
                                                                    Pageable pageable) {

        return categoryRepository.findCategoryListAboutChild(categoryNo, pageable);
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

        Category currentCategory =
            getSequenceNotSameSubCategory(categoryNo, hopingPositionCategoryNo);

        if (Objects.isNull(currentCategory)) {
            return;
        }

        Category parentCategoryOfCurrentCategory = currentCategory.getParentCategory();
        if (Objects.equals(categoryNo, parentCategoryOfCurrentCategory.getCategoryNo())) {
            throw new NotChildCategoryException();
        }

        Category hopingPositionCategory = this.findCategoryEntityFetch(hopingPositionCategoryNo);
        Integer hopingSequence = hopingPositionCategory.getSequence();

        Category parentCategoryOfhopingPositionCategory =
            hopingPositionCategory.getParentCategory();
        Integer parentCategoryNoOfHopingPositionCategory =
            parentCategoryOfhopingPositionCategory.getCategoryNo();
        categoryRepository.modifyChildCategorySequence(parentCategoryNoOfHopingPositionCategory,
            hopingSequence);

        Integer parentCategoryNoOfCurrentCategory = parentCategoryOfCurrentCategory.getCategoryNo();
        currentCategory.setSequence(hopingSequence);

        if (Objects.equals(parentCategoryNoOfCurrentCategory,
            parentCategoryNoOfHopingPositionCategory)) {
            return;
        }

        currentCategory.setParentCategory(parentCategoryOfhopingPositionCategory);
    }

    private Category getSequenceNotSameSubCategory(Integer categoryNo,
                                                   Integer hopingPositionCategoryNo) {

        if (Objects.equals(categoryNo, hopingPositionCategoryNo)) {
            return null;
        }

        return this.findCategoryEntityFetch(categoryNo);
    }

    @Override
    @Transactional
    public void modifyMainSequence(Integer categoryNo, Integer sequence) {

        Category category = getSequenceNotSameMainCategory(categoryNo, sequence);
        if (Objects.isNull(category)) {
            return;
        }

        categoryRepository.modifyMainCategorySequence(sequence);
        category.setSequence(sequence);
    }

    private Category getSequenceNotSameMainCategory(Integer categoryNo, Integer sequence) {
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

        Category category = this.findCategoryEntityFetch(categoryNo);

        if (Objects.equals(category.getLevel(), MAIN_CATEGORY_LEVEL)) {
            checkMainCategoryContainsProducts(categoryNo);
            categoryRepository.deleteById(categoryNo);
            return;
        }

        checkSubCategoryContainsProducts(categoryNo);
        categoryRepository.deleteById(categoryNo);
    }

    private void checkSubCategoryContainsProducts(Integer categoryNo) {
        List<CategoryNoAndProductNoDto> categoryNoAndProductNoDtoList =
            categoryRepository.getSubCategoryNoAndProductNoDtoForContainsProducts(categoryNo);

        if (!categoryNoAndProductNoDtoList.isEmpty()) {
            throw new CategoryContainsProductsException();
        }
    }

    private void checkMainCategoryContainsProducts(Integer categoryNo) {
        CategoryNoAndProductNoDto categoryNoAndProductNoDto =
            categoryRepository.getMainCategoryNoAndProductNoDtoForContainsProducts(categoryNo);

        if (Objects.nonNull(categoryNoAndProductNoDto)
            && Objects.nonNull(categoryNoAndProductNoDto.getCategoryNo())) {
            throw new CategoryContainsProductsException();
        }
    }


}
