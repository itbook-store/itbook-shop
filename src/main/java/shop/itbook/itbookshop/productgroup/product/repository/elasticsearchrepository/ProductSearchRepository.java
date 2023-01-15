package shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchRepository extends ElasticsearchRepository<Product, Long> {
    List<Product> findByName(String name);
}
