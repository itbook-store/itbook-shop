package shop.itbook.itbookshop.productgroup.producttype.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.exception.ProductTypeNotFoundException;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;

/**
 * ProductTypeAdminService 인터페이스를 구현한 상품유형 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductTypeServiceImpl implements ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductTypeResponseDto> findProductTypeList(Pageable pageable) {
        return productTypeRepository.findProductTypeList(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductType findProductType(Integer productTypeNo) {
        return productTypeRepository.findById(productTypeNo)
            .orElseThrow(ProductTypeNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findNewBookList(Pageable pageable,
                                                           boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findNewBookListAdmin(pageable);
        } else {
            return productTypeRepository.findNewBookListUser(pageable);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findDiscountBookList(Pageable pageable,
                                                                boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findDiscountBookListAdmin(pageable);
        } else {
            return productTypeRepository.findDiscountBookListUser(pageable);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findBestSellerBookList(Pageable pageable,
                                                                  boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findBestSellerBookListAdmin(pageable);
        } else {
            return productTypeRepository.findBestSellerBookListUser(pageable);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findPopularityBookList(Pageable pageable,
                                                                  boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findPopularityBookListAdmin(pageable);
        } else {
            return productTypeRepository.findPopularityBookListUser(pageable);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> findRecommendationBookList(Pageable pageable,
                                                 Long memberNo,
                                                 boolean isAdmin) {
        Long basedProductNo;

        if(Optional.ofNullable(memberNo).isEmpty()) {
            basedProductNo = productTypeRepository.findBestSellingBook();
            return
                productTypeRepository.findPurchasedTogetherProductList(basedProductNo);
        }

        // 1순위 : 최근 구매한 상품 기준으로 함께구매한 상품 추천
        basedProductNo =
            productTypeRepository.findRecentlyPurchaseProduct(memberNo);

        // 2순위 : 최근 본 상품 기준으로 함께구매한 상품 추천
        if (Optional.ofNullable(basedProductNo).isEmpty()) {
            basedProductNo = productTypeRepository.findRecentlyViewedProduct(memberNo);
        }

        // 3순위: 가장 많이 팔린 서적 기준으로 함께구매한 상품 추천
        if (Optional.ofNullable(basedProductNo).isEmpty()) {
            basedProductNo = productTypeRepository.findBestSellingBook();
        }

        return
            productTypeRepository.findPurchasedTogetherProductList(basedProductNo);

//        if (isAdmin) {
//            return productService.findProductListByProductNoList(pageable, productNoList);
//        } else {
//            return productService.findProductListByProductNoList(pageable, productNoList);
//        }
    }


    // TODO 수연님 여기서 최근 본 상품 개발하시면 됩니당
    @Override
    public Page<ProductDetailsResponseDto> findRecentlySeenProductList(Pageable pageable) {
        return null;
    }
}
