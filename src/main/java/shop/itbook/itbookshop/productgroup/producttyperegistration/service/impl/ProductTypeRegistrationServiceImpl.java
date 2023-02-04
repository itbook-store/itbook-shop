package shop.itbook.itbookshop.productgroup.producttyperegistration.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepository;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.exception.ProductTypeRegistrationNotFoundException;
import shop.itbook.itbookshop.productgroup.producttyperegistration.repository.ProductTypeRegistrationRepository;
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
public class ProductTypeRegistrationServiceImpl implements
    ProductTypeRegistrationService {

    private final ProductTypeRegistrationRepository productTypeRegistrationRepository;
    private final ProductTypeService productTypeService;
    private final ProductTypeRepository productTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<FindProductTypeResponseDto> findProductTypeNameList(Pageable pageable,
                                                                    Long productNo) {
        Page<FindProductTypeResponseDto> productTypeListWithProductNo =
            productTypeRegistrationRepository.findProductTypeListWithProductNo(pageable, productNo);

        if (Objects.isNull(productTypeListWithProductNo)) {
            throw new ProductTypeRegistrationNotFoundException();
        }

        return productTypeListWithProductNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductList(Pageable pageable, Integer productTypeNo,
                                                           boolean isAdmin) {
        Page<ProductDetailsResponseDto> productListWithProductTypeNo =
            productTypeRegistrationRepository.findProductListAdminWithProductTypeNo(pageable,
                productTypeNo);

        if (Objects.isNull(productListWithProductTypeNo)) {
            throw new ProductTypeRegistrationNotFoundException();
        }

        return productListWithProductTypeNo;
    }
}
