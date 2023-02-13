package shop.itbook.itbookshop.productgroup.product.service.salesstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * The type Product sales status service.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductSalesStatusService {
    private final ProductRepository productRepository;

    /**
     * 정렬기준에 따라 판매현황 집계 결과를 반환합니다.
     *
     * @param sortingCriteria the sorting criteria
     * @param pageable        the pageable
     * @return the page
     * @author 이하늬
     */
    public Page<ProductSalesRankResponseDto> findSortingList(String sortingCriteria,
                                                             Pageable pageable) {
        ProductSortingCriteriaEnum productSortingCriteriaEnum =
            ProductSortingCriteriaEnum.valueOf(sortingCriteria);
        switch (productSortingCriteriaEnum) {
            case CANCELED:
                return this.findCanceledRankList(pageable);
            case COMPLETED:
                return this.findCompletedRankList(pageable);
            default:
                return null;
        }
    }

    /**
     * 구매 완료된 판매량을 합산하여 완료된 건 수 순으로 상품 리스트를 반환합니다.
     *
     * @param pageable the pageable
     * @return the page
     * @author 이하늬
     */
    public Page<ProductSalesRankResponseDto> findCompletedRankList(Pageable pageable) {
        return productRepository.findCompleteRankProducts(pageable);
    }

    /**
     * 구매 취소 및 환불된 판매량을 합산하여 취소된 건 수 순으로 상품 리스트를 반환합니다.
     *
     * @param pageable the pageable
     * @return the page
     * @author 이하늬
     */
    public Page<ProductSalesRankResponseDto> findCanceledRankList(Pageable pageable) {
        return productRepository.findCompleteRankProducts(pageable);
    }
}
