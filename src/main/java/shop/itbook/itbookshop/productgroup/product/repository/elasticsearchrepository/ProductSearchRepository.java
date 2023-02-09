package shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;

/**
 * 상품 ProductSearchRepository 클래스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchRepository extends ElasticsearchRepository<SearchProduct, Long> {
    Page<SearchProduct> findByName(Pageable pageable, String name);
}
