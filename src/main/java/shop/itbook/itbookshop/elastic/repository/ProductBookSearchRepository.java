package shop.itbook.itbookshop.elastic.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.itbook.itbookshop.elastic.document.SearchProductBook;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductBookSearchRepository extends ElasticsearchRepository<SearchProductBook, Long> {
    Page<SearchProduct> findByName(Pageable pageable, String name);

    List<SearchProduct> findByName(String name);
}
