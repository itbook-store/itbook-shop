package shop.itbook.itbookshop.productgroup.productcategory.service.impl;

import static shop.itbook.itbookshop.productgroup.product.service.impl.ProductServiceImpl.setExtraFieldsForList;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.category.service.CategoryService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * ProductTypeAdminService 인터페이스를 구현한 상품유형 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Category addProductCategory(Product product, List<Integer> categoryList) {

        Category parentCategory =
            categoryService.findCategoryEntity(categoryList.get(0)).getParentCategory();

        List<Category> categories = categoryRepository.findAllById(categoryList);

        for (Category category : categories) {
            productCategoryRepository.save(new ProductCategory(product, category));
        }

        return parentCategory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Category modifyProductCategory(Product product, List<Integer> categoryList) {
        Category parentCategory =
            categoryService.findCategoryEntity(categoryList.get(0)).getParentCategory();

        this.removeProductCategory(product.getProductNo());
        List<Category> categories = categoryRepository.findAllById(categoryList);

        for (Category category : categories) {
            productCategoryRepository.save(new ProductCategory(product, category));
        }

        return parentCategory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeProductCategory(Long productNo) {
        productCategoryRepository.deleteByPk_productNo(productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CategoryDetailsResponseDto> findCategoryList(Pageable pageable, Long productNo) {
        return productCategoryRepository.getCategoryListWithProductNo(pageable, productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductList(Pageable pageable, Integer categoryNo) {
        Page<ProductDetailsResponseDto> productList =
            productCategoryRepository.getProductListWithCategoryNo(pageable, categoryNo);
        setExtraFieldsForList(productList);
        return productList;
    }
}
