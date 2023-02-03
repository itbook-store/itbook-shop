package shop.itbook.itbookshop.productgroup.producttype.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * The interface Product type registration repository custom.
 */
@NoRepositoryBean
public interface ProductTypeRepositoryCustom {

    /**
     * 모든 상품유형의 번호와 이름을 반환합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 상품 유형 리스트입니다.
     * @author 이하늬
     */
    Page<ProductTypeResponseDto> findProductTypeList(Pageable pageable);

    /**
     * 발간일 기준으로 신간 도서를 조회하여 반환합니다.
     * 사용자는 노출 여부가 true인 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 신간 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findNewBookListUser(Pageable pageable);

    /**
     * 발간일 기준으로 신간 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 신간 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findNewBookListAdmin(Pageable pageable);

    /**
     * 할인이 적용된 할인 도서를 조회하여 반환합니다.
     * 사용자는 노출 여부가 true인 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 할인 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findDiscountBookListUser(Pageable pageable);

    /**
     * 할인이 적용된 할인 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 할인 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findDiscountBookListAdmin(Pageable pageable);

    /**
     * 판매 부수 기준으로 베스트셀러 도서를 조회하여 반환합니다.
     * 사용자는 노출 여부가 true인 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 베스트셀러 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findBestSellerBookListUser(Pageable pageable);

    /**
     * 조회수 기준으로 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 인기 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findBestSellerBookListAdmin(Pageable pageable);

    /**
     * 조회수 기준으로 도서를 조회하여 반환합니다.
     * 사용자는 노출 여부가 true인 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 인기 도서 리스트입니다.
     * @author 이하늬
     */

    Page<ProductDetailsResponseDto> findPopularityBookListUser(Pageable pageable);

    /**
     * 조회수 기준으로 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 인기 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findPopularityBookListAdmin(Pageable pageable);

    /**
     * 추천 도서를 조회하여 반환합니다.
     * 사용자는 노출 여부가 true인 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 추천 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findRecommendationBookListUser(Pageable pageable);

    /**
     * 추천 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 추천 도서 리스트입니다.
     * @author 이하늬
     */

    Page<ProductDetailsResponseDto> findRecommendationBookListAdmin(
        Long recentlyPurchaseProductNo, Pageable pageable);
}
