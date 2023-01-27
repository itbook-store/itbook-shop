package shop.itbook.itbookshop.productgroup.productcategory.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 쿼리 dsl을 사용하기 위한 ProductCategoryRepository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductCategoryRepositoryCustom {


    /**
     * 카테고리 별 상품 리스트를 조회하는 기능을 담당합니다.
     *
     * @param categoryNo 조회할 카테고리 번호입니다.
     * @return 카테고리 번호로 필터링한 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<ProductDetailsResponseDto> getProductListWithCategoryNo(Integer categoryNo);

    /**
     * 상품 별 카테고리 리스트를 조회하는 기능을 담당합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 상품 번호로 필터링한 카테고리 리스트를 반환합니다.
     * @author 이하늬
     */
    List<CategoryDetailsResponseDto> getCategoryListWithProductNo(Long productNo);
}
