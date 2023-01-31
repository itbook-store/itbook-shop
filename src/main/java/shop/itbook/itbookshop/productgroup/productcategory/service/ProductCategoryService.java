package shop.itbook.itbookshop.productgroup.productcategory.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품카테고리 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductCategoryService {

    /**
     * 상품-카테고리 테이블에 상품과 카테고리를 등록하는 메서드입니다.
     *
     * @param product      등록할 상품입니다.
     * @param categoryList 등록할 카테고리 리스트입니다.                     (한 상품은 카테고리를 최대 3개까지 가질 수 있습니다.)
     * @return 등록한 상품의 대분류 카테고리를 반환합니다.
     * @author 이하늬
     */
    Category addProductCategory(Product product, List<Integer> categoryList);

    /**
     * 상품 번호로 상품-카테고리 삭제를 담당하는 메서드입니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     * @author 이하늬
     */
    void removeProductCategory(Long productNo);

    /**
     * 상품 번호로 상품-카테고리 수정을 담당하는 메서드입니다.
     *
     * @param product        상품 번호입니다.
     * @param categoryNoList 수정할 카테고리 리스트입니다.
     * @return 수정한 상품의 대분류 카테고리를 반환합니다.
     * @author 이하늬
     */
    Category modifyProductCategory(Product product, List<Integer> categoryNoList);

    /**
     * 상품 번호로 해당 상품의 카테고리 리스트를 조회하는 기능을 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 조회한 카테고리 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<CategoryDetailsResponseDto> findCategoryList(Pageable pageable, Long productNo);

    /**
     * 카테고리 번호로 해당 카테고리의 상품 리스트를 조회하는 기능을 담당하는 메서드입니다.
     *
     * @param categoryNo 조회할 카테고리 번호입니다.
     * @return 조회한 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductList(Pageable pageable, Integer categoryNo);
}
