package shop.itbook.itbookshop.productgroup.producttype.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 상품유형 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeAdminService {

    /**
     * 모든 상품유형 리스트를 조회를 담당하는 메서드입니다.
     *
     * @return 모든 상품유형을 리스트로 반환합니다.
     * @author 이하늬
     */
    List<ProductType> findProductTypeList();

}
