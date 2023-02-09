package shop.itbook.itbookshop.productgroup.product.service.elastic.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.SearchProduct;
import shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository.ProductSearchRepository;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;
import shop.itbook.itbookshop.productgroup.product.transfer.SearchProductTransfer;

/**
 * ProductSearchService 인터페이스를 구현한 상품 검색 Service 클래스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final ProductSearchRepository productSearchRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addSearchProduct(Product product) {

        SearchProduct searchProduct = SearchProductTransfer.entityToDocument(product);

        return productSearchRepository.save(searchProduct).getProductNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifySearchProduct(Product product) {

        SearchProduct searchProduct = SearchProductTransfer.entityToDocument(product);

        productSearchRepository.save(searchProduct);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeSearchProduct(Long productNo) {

        productSearchRepository.deleteById(productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductSearchResponseDto> searchProductByTitle(Pageable pageable, String name) {

        Page<SearchProduct> searchProducts = productSearchRepository.findByName(pageable, name);

        List<ProductSearchResponseDto> result = searchProducts
            .stream()
            .map(SearchProductTransfer::documentToDto)
            .collect(Collectors.toList());


        return new PageImpl<>(result, pageable, searchProducts.getTotalElements());
    }

}
