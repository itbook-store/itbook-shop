package shop.itbook.itbookshop.productgroup.product.service.elastic;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchService {

    @Transactional
    Long addSearchProduct(Product product);

    @Transactional
    void modifySearchProduct(Product product);

    @Transactional
    void removeSearchProduct(Long productNo);

    List<ProductSearchResponseDto> searchProductByTitle(String name);
}
