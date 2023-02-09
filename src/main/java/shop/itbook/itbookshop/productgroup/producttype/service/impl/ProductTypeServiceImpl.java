package shop.itbook.itbookshop.productgroup.producttype.service.impl;

import static shop.itbook.itbookshop.productgroup.product.service.impl.ProductServiceImpl.setExtraFieldsForList;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.exception.ProductTypeNotFoundException;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;
import shop.itbook.itbookshop.productgroup.producttypeenum.ProductTypeEnum;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.ProductTypeRegistrationService;

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
    private final ProductService productService;
    private final ProductTypeRegistrationService productTypeRegistrationService;
    private final MemberRoleService memberRoleService;

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
    public Page<ProductDetailsResponseDto> findProductListByProductTypeNo(Pageable pageable,
                                                                          Integer productTypeNo,
                                                                          Long memberNo) {
        Page<ProductDetailsResponseDto> productList;
        ProductTypeEnum productTypeEnum =
            this.findProductType(productTypeNo).getProductTypeEnum();

        switch (productTypeEnum) {
            case DISCOUNT:
                productList =
                    this.findDiscountBookList(pageable);
                break;

            case NEW_ISSUE:
                productList = this.findNewBookList(pageable);
                break;

            case BESTSELLER:
                productList = this.findBestSellerBookList(pageable);
                break;

            case POPULARITY:
                productList = this.findPopularityBookList(pageable);
                break;

            case RECOMMENDATION:
                List<Long> productNoList =
                    this.findRecommendationBookList(memberNo);
                productList =
                    productService.findProductListByProductNoListForUser(pageable, productNoList);
                break;

            case RECENTLY_SEEN_PRODUCT:
                productList = this.findRecentlySeenProductList();
                break;

            default:
                productList =
                    productTypeRegistrationService.findProductList(pageable, productTypeNo);
                break;
        }

        setExtraFieldsForList(productList);

        return productList;
    }


    public Page<ProductDetailsResponseDto> findNewBookList(Pageable pageable/*,
                                                           boolean isAdmin*/) {
        return productTypeRepository.findNewBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findDiscountBookList(Pageable pageable/*,
                                                                boolean isAdmin*/) {
        return productTypeRepository.findDiscountBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findBestSellerBookList(Pageable pageable/*,
                                                                  boolean isAdmin*/) {
        return productTypeRepository.findBestSellerBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findPopularityBookList(Pageable pageable) {
        return productTypeRepository.findPopularityBookListUser(pageable);
    }

    public List<Long> findRecommendationBookList(Long memberNo) {
        Long basedProductNo;

        if (Optional.ofNullable(memberNo).isEmpty()) {
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
    }


    // TODO 수연님 여기서 최근 본 상품 개발하시면 됩니당
    public Page<ProductDetailsResponseDto> findRecentlySeenProductList() {
        return null;
    }
}
