package shop.itbook.itbookshop.productgroup.productrelationgroup.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;

/**
 * 연관상품 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductRelationGroupService {

    /**
     * 연관상품 테이블에 상품을 등록하는 메서드입니다.
     *
     * @param basedProductNo 등록할 기준 상품 번호입니다.
     * @param productNoList  기준 상품에 대한 연관 상품 번호입니다.
     * @return 저장한 연관 상품의 기준 상품을 반환합니다.
     * @author 이하늬
     */
    Product addProductRelation(Long basedProductNo, List<Long> productNoList);

    /**
     * <관리자> 기준 상품 번호로 해당 상품의 연관상품 리스트를 추가하기 위해 자신을 제외한 나머지 상품을 기능을 담당하는 메서드입니다.
     *
     * @param productNo 기준이 돼서 조회할 상품 번호입니다.
     * @return 조회한 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductExceptBasedProductForAdmin(Pageable pageable,
                                                                          Long productNo);

    /**
     * 기준 상품에 연관된 상품을 상품 번호로 연관상품 삭제를 담당하는 메서드입니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     * @author 이하늬
     */
    void removeProductRelation(Long basedProductNo, Long productNo);

    /**
     * 상품 번호로 연관상품 수정을 담당하는 메서드입니다.
     *
     * @param basedProductNo 수정할 상품의 기준 상품 번호입니다.
     * @param productNoList  수정할 상품 번호 리스트입니다.
     * @return 수정한 연관상품의 기준 상품을 반환합니다.
     * @author 이하늬
     */
    Product modifyProductRelation(Long basedProductNo, ProductRelationRequestDto productNoList);

    /**
     * <관리자> 기준 상품 번호로 해당 상품의 연관상품 리스트를 조회하는 기능을 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 조회한 연관상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductRelationForAdmin(Pageable pageable, Long productNo);

    /**
     * <관리자> 모든 연관상품 리스트의 상위 상품을 조회하는 기능을 담당하는 메서드입니다.
     *
     * @return 조회한 연관상품 리스트의 상위 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductRelationResponseDto> findAllMainProductRelationForAdmin(Pageable pageable);

    /**
     * <사용자> 기준 상품 번호로 해당 상품의 연관상품 리스트를 조회하는 기능을 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 조회한 연관상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductRelationForUser(Pageable pageable, Long productNo);

}