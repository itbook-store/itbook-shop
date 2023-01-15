package shop.itbook.itbookshop.productgroup.producttyperegistration.service.adminapi.impl;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.exception.ProductTypeRegistrationNotFoundException;
import shop.itbook.itbookshop.productgroup.producttyperegistration.repository.ProductTypeRegistrationRepository;
import shop.itbook.itbookshop.productgroup.producttyperegistration.service.adminapi.ProductTypeRegistrationAdminService;

/**
 * ProductTypeAdminService 인터페이스를 구현한 상품유형 Service 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductTypeRegistrationAdminServiceImpl implements
    ProductTypeRegistrationAdminService {

    private final ProductTypeRegistrationRepository productTypeRegistrationRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FindProductTypeResponseDto> findProductTypeNameList(Long productNo) {
        List<FindProductTypeResponseDto> productTypeListWithProductNo =
            productTypeRegistrationRepository.findProductTypeListWithProductNo(productNo);
        
        if (Objects.isNull(productTypeListWithProductNo)) {
            throw new ProductTypeRegistrationNotFoundException();
        }

        return productTypeListWithProductNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FindProductResponseDto> findProductList(Integer productTypeNo) {
        List<FindProductResponseDto> productListWithProductTypeNo =
            productTypeRegistrationRepository.findProductListWithProductTypeNo(productTypeNo);

        if (Objects.isNull(productListWithProductTypeNo)) {
            throw new ProductTypeRegistrationNotFoundException();
        }

        return productListWithProductTypeNo;
    }
}
