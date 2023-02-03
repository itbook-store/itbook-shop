package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;

/**
 * The interface Product type registration repository custom.
 */
@NoRepositoryBean
public interface ProductTypeRegistrationRepositoryCustom {


    /**
     * 상품 번호로 상품유형타입들을 조회하여 반환합니다.
     *
     * @param pageable  페이지네이션을 위한 pageable입니다.
     * @param productNo the product no
     * @return the page
     * @author 이하늬
     */
    Page<FindProductTypeResponseDto> findProductTypeListWithProductNo(Pageable pageable,
                                                                      Long productNo);

    /**
     * 상품유형타입 번호로 상품리스트를 조회하여 반환합니다.
     *
     * @param pageable      페이지네이션을 위한 pageable입니다.
     * @param productTypeNo the product type no
     * @return the page
     * @author 이하늬
     */

    Page<ProductDetailsResponseDto> findProductListUserWithProductTypeNo(Pageable pageable,
                                                                         Integer productTypeNo);

    Page<ProductDetailsResponseDto> findProductListAdminWithProductTypeNo(Pageable pageable,
                                                                          Integer productTypeNo);

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
     * 판매 부수 기준으로 베스트셀러 도서를 조회하여 반환합니다.
     * 관리자는 모든 상품을 조회 가능합니다.
     *
     * @param pageable 페이지네이션을 위한 pageable입니다.
     * @return 베스트셀러 도서 리스트입니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findBestSellerBookListAdmin(Pageable pageable);
}
