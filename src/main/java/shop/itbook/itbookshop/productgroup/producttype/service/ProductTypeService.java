package shop.itbook.itbookshop.productgroup.producttype.service;

import java.util.List;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 상품유형 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeService {

    /**
     * 모든 상품유형 리스트를 조회를 담당하는 메서드입니다.
     *
     * @return 모든 상품유형을 리스트로 반환합니다.
     * @author 이하늬
     */
    List<ProductType> findProductTypeList();

    /**
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 찾은 상품유형 entity를 반환합니다.
     * @author 이하늬
     */
    ProductType findProductType(Integer productTypeNo);

}
