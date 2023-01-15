package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * 쿼리 dsl 을 처리하기 위한 repository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductTypeRegistrationRepositoryCustom {

    /**
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품유형명 리스트를 반환합니다.
     * @author 이하늬
     */
    List<FindProductTypeResponseDto> findProductTypeListWithProductNo(Long productNo);

    /**
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 찾은 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<FindProductResponseDto> findProductListWithProductTypeNo(Integer productTypeNo);
}
