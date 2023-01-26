package shop.itbook.itbookshop.productgroup.productcategory.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductCategoryRepositoryCustom {
    List<ProductDetailsResponseDto> getProductListWithCategoryNo(Integer categoryNo);

    List<CategoryDetailsResponseDto> getCategoryListWithProductNo(Long productNo);
}
