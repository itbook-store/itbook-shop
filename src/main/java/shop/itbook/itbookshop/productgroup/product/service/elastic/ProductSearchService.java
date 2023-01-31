package shop.itbook.itbookshop.productgroup.product.service.elastic;

import java.util.List;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품검색 Service 인터페이스 입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public interface ProductSearchService {

    /**
     * 엘라스틱 서치에 상품정보를 추가하는 메서드입니다.
     *
     * @param product 엘라스틱 서치에 추가될 상품 정보를 담고 있는 엔터티입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     */
    Long addSearchProduct(Product product);

    /**
     * 엘라스틱 서치에 상품정보를 수정하는 메서드입니다.
     *
     * @param product 엘라스틱 서치에 수정될 상품 정보를 담고 있는 엔터티입니다.
     */
    void modifySearchProduct(Product product);

    /**
     * 엘라스틱 서치에 상품정보를 삭제하는 메서드입니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     */
    void removeSearchProduct(Long productNo);

    /**
     * 상품 제목으로 상품들을 검색하여 반환하는 메소드입니다.
     *
     * @param name 검색할 정보를 받습니다.
     * @return 검색된 내용을 반환합니다.
     */
    List<ProductSearchResponseDto> searchProductByTitle(String name);
}
