package shop.itbook.itbookshop.productgroup.productrelationgroup.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;

/**
 * 쿼리 dsl을 사용하기 위한 ProductRelationGroupRepository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductRelationGroupRepositoryCustom {


    /**
     * <관리자> 기준상품 번호로 기준상품의 연관상품 리스트를 조회하는 기능을 담당합니다.
     * 관리자는 삭제된 연관상품도 볼 수 있습니다.
     *
     * @param basedProductNo 조회할 기준 상품 번호입니다.
     * @return 조회한 연관 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<Long> getRelationProductNoListWithBasedProductNoAdmin(Long basedProductNo);

    /**
     * <관리자> 연관상품을 추가하기 위해 자신을 제외한 나머지 상품을 조회합니다.
     *
     * @param basedProductNo 조회할 기준 상품 번호입니다.
     * @return 조회한 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<Long> getProductNoListToAddRelationAdmin(Long basedProductNo);

    /**
     * <사용자> 기준상품 번호로 기준상품의 연관상품 리스트를 조회하는 기능을 담당합니다.
     * 사용자는 삭제된 연관상품은 볼 수 없습니다.
     *
     * @param basedProductNo 조회할 기준 상품 번호입니다.
     * @return 조회한 연관 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    List<Long> getRelationProductNoListWithBasedProductNoUser(Long basedProductNo);

    Page<ProductRelationResponseDto> getAllBasedProductNoListAdmin(Pageable pageable);
}
