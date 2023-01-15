package shop.itbook.itbookshop.productgroup.product.service.elastic;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchService {
    @Transactional
    String save(Product requestDto);

    List<ProductSearchResponseDto> searchProductByTitle(String name);
}
