package shop.itbook.itbookshop.productgroup.productcategory.service;

import java.util.List;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 상품카테고리 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductCategoryService {

    /**
     * 상품-카테고리 테이블에 상품과 카테고리를 등록하는 메서드입니다.
     * 부모 카테고리와 서브 카테고리를 등록합니다.
     *
     * @param product      등록할 상품입니다.
     * @param categoryList 등록할 카테고리 리스트입니다.
     *                     (한 상품은 카테고리를 최대 3개까지 가질 수 있습니다.)
     * @return 등록한 상품의 대분류 카테고리를 반환합니다.
     * @author 이하늬
     */
    Category addProductCategory(Product product, List<Integer> categoryList);

    void removeProductCategory(Long productNo);

    Category modifyProductCategory(Product product, List<Integer> categoryNoList);

    List<CategoryDetailsResponseDto> findCategoryList(Long productNo);

    List<ProductDetailsResponseDto> findProductList(Integer categoryNo);
}
