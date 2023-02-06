package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 쿼리 dsl을 사용하기 위한 ProductRepository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductRepositoryCustom {
    /**
     * 모든 상품들의 상세 정보를 담아 리스트로 반환하는 기능을 담당합니다.
     *
     * @return 모든 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListAdmin(Pageable pageable);

    /**
     * 노출여부가 true인 모든 상품들의 상세 정보를 담아 리스트로 반환하는 기능을 담당합니다.
     *
     * @return 사용자가 상품을 조회하기 위해 노출 여부가 false인 모든 상품 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListUser(Pageable pageable);

    /**
     * 상품 번호로 상품을 조회해 상품의 상세정보를 담아 반환하는 기능을 담당합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 조회한 상품의 상세정보를 반환합니다.
     * @author 이하늬
     */
    Optional<ProductDetailsResponseDto> findProductDetails(Long productNo);

    /**
     * <사용자> 상품 번호 리스트로 상품을 조회해 상품들의 상세정보를 담아 반환하는 기능을 담당합니다.
     * 삭제 여부가 false인 상품과 판매 여부가 true인 상품만 담습니다.
     *
     * @param productNoList 조회할 상품 번호 리스트입니다..
     * @return 조회한 상품의 상세정보를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListByProductNoListForUser(
        Pageable pageable, List<Long> productNoList);

    /**
     * <관리자> 상품 번호 리스트로 상품을 조회해 상품들의 상세정보를 담아 반환하는 기능을 담당합니다.
     * 모든 상품을 담습니다.
     *
     * @param productNoList 조회할 상품 번호 리스트입니다..
     * @return 조회한 상품의 상세정보를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListByProductNoListForAdmin(Pageable pageable,
                                                                           List<Long> productNoList);
}
