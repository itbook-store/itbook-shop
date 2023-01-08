package shop.itbook.itbookshop.productgroup.product.service.adminapi.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductService;

/**
 * ProductService 인터페이스를 구현한 상품 서비스 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Long addProduct(AddProductRequestDto requestDto) {
        Product product = Product.builder().name(requestDto.getName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription()).stock(requestDto.getStock())
            .isSelled(requestDto.isSelled()).isDeleted(requestDto.isDeleted())
            .isSubscription(requestDto.isSubscription()).thumbnailUrl(requestDto.getThumbnailUrl())
            .fixedPrice(requestDto.getFixedPrice())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent()).rawPrice(requestDto.getRawPrice())
            .build();

        productRepository.save(product);
        return product.getProductNo();
    }

    @Override
    @Transactional
    public void modifyProduct(Long productNo, ModifyProductRequestDto requestDto) {
        Product product = Product.builder().name(requestDto.getName())
            .simpleDescription(requestDto.getSimpleDescription())
            .detailsDescription(requestDto.getDetailsDescription()).stock(requestDto.getStock())
            .isSelled(requestDto.isSelled()).isDeleted(requestDto.isDeleted())
            .isSubscription(requestDto.isSubscription()).thumbnailUrl(requestDto.getThumbnailUrl())
            .fixedPrice(requestDto.getFixedPrice())
            .increasePointPercent(requestDto.getIncreasePointPercent())
            .discountPercent(requestDto.getDiscountPercent()).rawPrice(requestDto.getRawPrice())
            .build();

        productRepository.save(product);
    }

    @Override
    @Transactional
    public Boolean removeProduct(Long productNo) {
        if (Objects.isNull(productNo)) {
            return Boolean.FALSE;
        }

        productRepository.deleteById(productNo);
        return Boolean.TRUE;
    }

    @Override
    public Product findProduct(Long productNo) {
        return productRepository.findById(productNo)
            .orElseThrow(() -> new ProductNotFoundException(productNo));
    }
}
