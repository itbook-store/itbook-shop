package shop.itbook.itbookshop.productgroup.producttype.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
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
    public List<ProductType> findProductTypeList() {
        return productTypeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductType findProductType(Integer productTypeNo) {
        return productTypeRepository.findById(productTypeNo)
            .orElseThrow(ProductTypeNotFoundException::new);
    }

    @Override
    public Page<ProductDetailsResponseDto> findNewBookList(Pageable pageable,
                                                           boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findNewBookListAdmin(pageable);
        } else {
            return productTypeRepository.findNewBookListUser(pageable);
        }
    }

    @Override
    public Page<ProductDetailsResponseDto> findDiscountBookList(Pageable pageable,
                                                                boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findDiscountBookListAdmin(pageable);
        } else {
            return productTypeRepository.findDiscountBookListUser(pageable);
        }
    }

    @Override
    public Page<ProductDetailsResponseDto> findBestSellerBookList(Pageable pageable,
                                                                  boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findBestSellerBookListAdmin(pageable);
        } else {
            return productTypeRepository.findBestSellerBookListUser(pageable);
        }
    }

    @Override
    public Page<ProductDetailsResponseDto> findPopularityBookList(Pageable pageable,
                                                                  boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findPopularityBookListAdmin(pageable);
        } else {
            return productTypeRepository.findPopularityBookListUser(pageable);
        }
    }

    @Override
    public Page<ProductDetailsResponseDto> findRecommendationBookList(Pageable pageable,
                                                                      boolean isAdmin) {
        if (isAdmin) {
            return productTypeRepository.findRecommendationBookListAdmin(pageable);
        } else {
            return productTypeRepository.findRecommendationBookListUser(pageable);
        }
    }
}
