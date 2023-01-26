package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductRepositoryCustom {
    List<ProductDetailsResponseDto> findProductList();

    ProductDetailsResponseDto findProduct(Long id);
}
