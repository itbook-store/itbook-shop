package shop.itbook.itbookshop.productgroup.producttype.service.impl;

import static shop.itbook.itbookshop.productgroup.product.service.impl.ProductServiceImpl.setExtraFieldsForList;

import java.util.List;
import java.util.Objects;
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
    public Page<ProductDetailsResponseDto> findProductListByProductTypeNoForUser(Pageable pageable,
                                                                                 Integer productTypeNo,
                                                                                 Long memberNo) {
        Page<ProductDetailsResponseDto> productList;
        ProductTypeEnum productTypeEnum =
            this.findProductType(productTypeNo).getProductTypeEnum();

        switch (productTypeEnum) {
            case DISCOUNT:
                productList =
                    this.findDiscountBookListForUser(pageable);
                break;

            case NEW_ISSUE:
                productList = this.findNewBookListForUser(pageable);
                break;

            case BESTSELLER:
                productList = this.findBestSellerBookListForUser(pageable);
                break;

            case POPULARITY:
                productList = this.findPopularityBookListForUser(pageable);
                break;

            case RECOMMENDATION:
                List<Long> productNoList = this.findRecommendationBookListForUser(memberNo);
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

    /**
     * {@inheritDoc}
     */

    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductTypeNoForAdmin(Pageable pageable,
                                                                                  Integer productTypeNo,
                                                                                  Long memberNo) {
        Page<ProductDetailsResponseDto> productList;
        ProductTypeEnum productTypeEnum =
            this.findProductType(productTypeNo).getProductTypeEnum();

        switch (productTypeEnum) {
            case DISCOUNT:
                productList =
                    this.findDiscountBookListForUser(pageable);
                break;

            case NEW_ISSUE:
                productList = this.findNewBookListForUser(pageable);
                break;

            case BESTSELLER:
                productList = this.findBestSellerBookListForUser(pageable);
                break;

            case POPULARITY:
                productList = this.findPopularityBookListForUser(pageable);
                break;

            case RECOMMENDATION:
                List<Long> productNoList = this.findRecommendationBookListForUser(memberNo);
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


    public Page<ProductDetailsResponseDto> findNewBookListForUser(Pageable pageable) {
        return productTypeRepository.findNewBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findNewBookListForAdmin(Pageable pageable) {
        return productTypeRepository.findNewBookListAdmin(pageable);
    }

    public Page<ProductDetailsResponseDto> findDiscountBookListForUser(Pageable pageable) {
        return productTypeRepository.findDiscountBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findDiscountBookListForAdmin(Pageable pageable) {
        return productTypeRepository.findDiscountBookListAdmin(pageable);
    }

    public Page<ProductDetailsResponseDto> findBestSellerBookListForUser(Pageable pageable) {
        return productTypeRepository.findBestSellerBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findBestSellerBookListForAdmin(Pageable pageable) {
        return productTypeRepository.findBestSellerBookListAdmin(pageable);
    }

    public Page<ProductDetailsResponseDto> findPopularityBookListForUser(Pageable pageable) {
        return productTypeRepository.findPopularityBookListUser(pageable);
    }

    public Page<ProductDetailsResponseDto> findPopularityBookListForAdmin(Pageable pageable) {
        return productTypeRepository.findPopularityBookListAdmin(pageable);
    }

    public List<Long> findRecommendationBookListForUser(Long memberNo) {

        Long basedProductNo;
        List<Long> purchasedTogetherProductNoList;

        if (Objects.nonNull(memberNo)) {

            // 1순위 : 최근 구매한 상품 기준으로 함께 구매한 상품 추천
            basedProductNo = productTypeRepository.findRecentlyPurchaseProduct(memberNo);

            purchasedTogetherProductNoList = findPurchasedTogetherProductList(basedProductNo);
            if (purchasedTogetherProductNoList != null) {
                return purchasedTogetherProductNoList;
            }

            basedProductNo = productTypeRepository.findRecentlyViewedProduct(memberNo);

            // 2순위 : 최근 본 상품 기준으로 함께구매한 상품 추천
            purchasedTogetherProductNoList = findPurchasedTogetherProductList(basedProductNo);
            if (purchasedTogetherProductNoList != null) {
                return purchasedTogetherProductNoList;
            }
        }

        // 3순위: 가장 많이 팔린 서적 기준으로 함께구매한 상품 추천 - 비회원은 바로 3순위
        basedProductNo = productTypeRepository.findBestSellingBook();
        purchasedTogetherProductNoList =
            productTypeRepository.findPurchasedTogetherProductList(basedProductNo);
        return purchasedTogetherProductNoList;
    }

    public List<Long> findRecommendationBookListForAdmin(Long memberNo) {

        Long basedProductNo;
        List<Long> purchasedTogetherProductNoList;

        if (Objects.nonNull(memberNo)) {

            // 1순위 : 최근 구매한 상품 기준으로 함께 구매한 상품 추천
            basedProductNo = productTypeRepository.findRecentlyPurchaseProduct(memberNo);

            purchasedTogetherProductNoList = findPurchasedTogetherProductList(basedProductNo);
            if (purchasedTogetherProductNoList != null) {
                return purchasedTogetherProductNoList;
            }

            basedProductNo = productTypeRepository.findRecentlyViewedProduct(memberNo);

            // 2순위 : 최근 본 상품 기준으로 함께구매한 상품 추천
            purchasedTogetherProductNoList = findPurchasedTogetherProductList(basedProductNo);
            if (purchasedTogetherProductNoList != null) {
                return purchasedTogetherProductNoList;
            }
        }

        // 3순위: 가장 많이 팔린 서적 기준으로 함께구매한 상품 추천 - 비회원은 바로 3순위
        basedProductNo = productTypeRepository.findBestSellingBook();
        purchasedTogetherProductNoList =
            productTypeRepository.findPurchasedTogetherProductList(basedProductNo);
        return purchasedTogetherProductNoList;
    }

    private List<Long> findPurchasedTogetherProductList(Long basedProductNo) {
        List<Long> purchasedTogetherProductList;
        if (Objects.nonNull(basedProductNo)) {
            purchasedTogetherProductList =
                productTypeRepository.findPurchasedTogetherProductList(basedProductNo);
            if (Objects.nonNull(purchasedTogetherProductList)) {
                return purchasedTogetherProductList;
            }
        }
        return null;
    }


    // TODO 수연님 여기서 최근 본 상품 개발하시면 됩니당
    public Page<ProductDetailsResponseDto> findRecentlySeenProductList() {
        return null;
    }
}
