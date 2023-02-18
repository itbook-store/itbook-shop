package shop.itbook.itbookshop.elastic.service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.elastic.document.SearchProductBook;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductBookSearchService {
    List<SearchProductBook> findByName(String keyword);

    @Transactional
    Long addSearchProduct(Product product);
}
