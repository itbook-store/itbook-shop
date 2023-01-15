package shop.itbook.itbookshop.productgroup.product.service.elastic.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.repository.elasticsearchrepository.ProductSearchRepository;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductSearchRepository productSearchRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public String save(Product requestDto) {

        return productSearchRepository.save(requestDto).getName();
    }

    @Override
    public List<ProductSearchResponseDto> searchProductByTitle(String name) {

        return productSearchRepository.findByName(name)
            .stream()
            .map(product -> ProductSearchResponseDto.builder()
                .name(product.getName())
                .simpleDescription(product.getSimpleDescription())
                .rawPrice(product.getRawPrice())
                .stock(product.getStock())
                .isSelled(product.getIsSelled())
                .thumbnailUrl(product.getThumbnailUrl())
                .isDeleted(product.getIsDeleted())
                .fixedPrice(product.getFixedPrice())
                .increasePointPercent(product.getIncreasePointPercent())
                .discountPercent(product.getDiscountPercent())
                .build())
            .collect(Collectors.toList());
    }
}
