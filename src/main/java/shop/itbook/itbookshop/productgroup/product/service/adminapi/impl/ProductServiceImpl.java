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
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addProduct(AddProductRequestDto requestDto) {
        Product product = ProductTransfer.dtoToEntityAdd(requestDto);
        productRepository.save(product);
        return product.getProductNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyProduct(Long productNo, ModifyProductRequestDto requestDto) {
        Product product = ProductTransfer.dtoToEntityModify(requestDto);
        Product saveProduct = productRepository.save(product);
        if (Objects.isNull(saveProduct)) {
            throw new ProductNotFoundException(saveProduct.getProductNo());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeProduct(Long productNo) {
        productRepository.deleteById(productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product findProduct(Long productNo) {
        return productRepository.findById(productNo)
            .orElseThrow(() -> new ProductNotFoundException(productNo));
    }
}
