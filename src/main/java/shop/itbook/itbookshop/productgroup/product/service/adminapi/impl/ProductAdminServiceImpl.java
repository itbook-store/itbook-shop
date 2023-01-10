package shop.itbook.itbookshop.productgroup.product.service.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;

/**
 * ProductService 인터페이스를 구현한 상품 Service 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductAdminServiceImpl implements ProductAdminService {

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
        Product product = updateProduct(requestDto, productNo);
        productRepository.save(product);
    }

    /**
     * 상품 번호로 상품을 찾아 해당 상품 정보를 수정해주는 메서드입니다.
     *
     * @param requestDto 상품 수정을 위한 정보를 담은 dto 객체입니다.
     * @param productNo  수정해야 할 상품 번호입니다.
     * @return 수정 완료된 상품을 반환합니다.
     */
    private Product updateProduct(ModifyProductRequestDto requestDto, Long productNo) {
        Product product = this.findProduct(productNo);

        product.setName(requestDto.getName());
        product.setSimpleDescription(requestDto.getSimpleDescription());
        product.setDetailsDescription(requestDto.getDetailsDescription());
        product.setStock(requestDto.getStock());
        product.setIsSelled(requestDto.isSelled());
        product.setIsDeleted(requestDto.isDeleted());
        product.setThumbnailUrl(requestDto.getThumbnailUrl());
        product.setFixedPrice(requestDto.getFixedPrice());
        product.setIncreasePointPercent(requestDto.getIncreasePointPercent());
        product.setDiscountPercent(requestDto.getDiscountPercent());
        product.setRawPrice(requestDto.getRawPrice());

        return product;
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
            .orElseThrow(ProductNotFoundException::new);
    }
}
