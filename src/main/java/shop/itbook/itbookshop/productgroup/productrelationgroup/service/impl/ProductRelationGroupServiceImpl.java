package shop.itbook.itbookshop.productgroup.productrelationgroup.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;
import shop.itbook.itbookshop.productgroup.productrelationgroup.repository.ProductRelationGroupRepository;
import shop.itbook.itbookshop.productgroup.productrelationgroup.service.ProductRelationGroupService;

/**
 * ProductTypeAdminService 인터페이스를 구현한 상품유형 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductRelationGroupServiceImpl implements ProductRelationGroupService {
    private final ProductRelationGroupRepository productRelationRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Product addProductRelation(Long basedProductNo, List<Long> productNoList) {

        Product basedProduct = productService.findProductEntity(basedProductNo);
        List<Product> products = productRepository.findAllById(productNoList);
        try {
            for (Product product : products) {
                productRelationRepository.save(new ProductRelationGroup(basedProduct, product));
            }
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return basedProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductExceptBasedProductForAdmin(Pageable pageable,
                                                                                 Long productNo) {
        List<Long> productNoList =
            productRelationRepository.getProductNoListToAddRelationAdmin(productNo);

        return productService.findProductListByProductNoListForAdmin(pageable, productNoList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Product modifyProductRelation(Long basedProductNo,
                                         ProductRelationRequestDto productNoList) {
        Product basedProduct = productService.findProductEntity(basedProductNo);

        try {
            productRelationRepository.deleteByBasedProduct_productNo(basedProduct.getProductNo());
            List<Product> products = productRepository.findAllById(productNoList.getRelationList());
            for (Product product : products) {
                productRelationRepository.save(new ProductRelationGroup(basedProduct, product));
            }
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return basedProduct;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeProductRelation(Long basedProductNo, Long productNo) {

        ProductRelationGroup productRelation =
            productRelationRepository.findProductRelationGroupByBasedProduct_ProductNoAndProduct_ProductNo(
                basedProductNo, productNo);

        productRelation.setIsDeleted(true);
        try {
            productRelationRepository.save(productRelation);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductRelationForAdmin(Pageable pageable,
                                                                       Long productNo) {
        List<Long> productNoList =
            productRelationRepository.getRelationProductNoListWithBasedProductNoAdmin(productNo);

        return productService.findProductListByProductNoListForAdmin(pageable, productNoList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductRelationResponseDto> findAllMainProductRelationForAdmin(Pageable pageable) {

        Page<ProductRelationResponseDto> basedProductNoListAdmin =
            productRelationRepository.getAllBasedProductNoListAdmin(pageable);
        ;
        return basedProductNoListAdmin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductRelationForUser(Pageable pageable,
                                                                      Long productNo) {
        List<Long> productNoList =
            productRelationRepository.getRelationProductNoListWithBasedProductNoUser(productNo);

        Page<ProductDetailsResponseDto> list =
            productService.findProductListByProductNoListForUser(pageable, productNoList);
        return list;
    }

}
