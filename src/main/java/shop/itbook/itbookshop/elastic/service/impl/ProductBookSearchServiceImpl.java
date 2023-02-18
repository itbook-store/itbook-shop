package shop.itbook.itbookshop.elastic.service.impl;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.elastic.document.SearchProductBook;
import shop.itbook.itbookshop.elastic.repository.ProductBookSearchRepository;
import shop.itbook.itbookshop.elastic.service.ProductBookSearchService;
import shop.itbook.itbookshop.elastic.transfer.SearchProductBookTransfer;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;
import shop.itbook.itbookshop.productgroup.product.transfer.SearchProductTransfer;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductBookSearchServiceImpl implements ProductBookSearchService {
//    private final ElasticsearchClient esClient;

    private final ProductBookSearchRepository productBookSearchRepository;

    @Override
    public List<SearchProductBook> findByName(String keyword) {
        List<SearchProductBook> productBooks = new ArrayList<>();
//        try {
//            SearchResponse<SearchProductBook> response = esClient.search(s -> s
//                    .index("products")
//                    .query(q -> q
//                        .match(t -> t
//                            .field("name")
//                            .query(keyword)
//                        )
//                    ),
//                SearchProductBook.class
//            );
//
//            TotalHits total = response.hits().total();
//            boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
//
//            if (isExactResult) {
//                log.info("There are " + total.value() + " results");
//            } else {
//                log.info("There are more than " + total.value() + " results");
//            }
//
//            List<Hit<SearchProductBook>> hits = response.hits().hits();
//            for (Hit<SearchProductBook> hit : hits) {
//                productBooks.add(hit.source());
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        return productBooks;
    }

    @Override
    @Transactional
    public Long addSearchProduct(Product product) {

        SearchProductBook searchProduct = SearchProductBookTransfer.entityToDocument(product);

        return productBookSearchRepository.save(searchProduct).getProductNo();
    }
}
