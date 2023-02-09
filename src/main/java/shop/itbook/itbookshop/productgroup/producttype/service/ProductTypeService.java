package shop.itbook.itbookshop.productgroup.producttype.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
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
    Page<ProductTypeResponseDto> findProductTypeList(Pageable pageable);

    /**
     * 상품 번호로 상품 유형을 조회하는 기능을 담당합니다.
     *
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 찾은 상품유형 entity를 반환합니다.
     * @author 이하늬
     */
    ProductType findProductType(Integer productTypeNo);

    /**
     * 상품 유형 번호로 상품을 조회하는 메서드입니다.
     *
     * @param pageable      the pageable
     * @param productTypeNo 조회할 상품 유형 번호입니다.
     * @param memberNo      현재 로그인한 회원 정보입니다.
     * @return
     */
    Page<ProductDetailsResponseDto> findProductListByProductTypeNo(Pageable pageable,
                                                                   Integer productTypeNo,
                                                                   Long memberNo);
}
