package shop.itbook.itbookshop.productgroup.producttyperegistration.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * The interface Product type registration admin service.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeRegistrationAdminService {

    /**
     * 해당 상품의 모든 상품유형 리스트 조회를 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품유형 리스트를 반환합니다.
     * @author 이하늬
     */
    List<FindProductTypeResponseDto> findProductTypeNameList(Long productNo);

    /**
     * 상품 유형 별로 상품 리스트 조회를 담당하는 메서드입니다.
     *
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 찾은 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<FindProductResponseDto> findProductList(Integer productTypeNo);
}
