package shop.itbook.itbookshop.productgroup.producttyperegistration.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * The interface Product type registration admin service.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeRegistrationService {

    /**
     * 해당 상품의 모든 상품유형 리스트 조회를 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품유형 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<FindProductTypeResponseDto> findProductTypeNameList(Pageable pageable, Long productNo);

    /**
     * 상품 유형 별로 상품 리스트 조회를 담당하는 메서드입니다.
     *
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @param isAdmin       노출 여부에 따른 상품 조회 여부입니다.
     * @return 찾은 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductList(Pageable pageable, Integer productTypeNo,
                                                    boolean isAdmin);
    
}
