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
}
