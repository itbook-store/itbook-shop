package shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<SearchProduct, Long> {
    List<SearchProduct> findByName(String name);
}
