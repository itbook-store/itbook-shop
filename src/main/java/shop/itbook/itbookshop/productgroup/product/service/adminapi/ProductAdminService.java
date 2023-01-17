package shop.itbook.itbookshop.productgroup.product.service.adminapi;

import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductAdminService {
    /**
     * 상품 등록을 담당하는 메서드입니다.
     *
     * @param requestDto 상품 등록을 위한 정보를 담은 dto 객체입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addProduct(AddProductRequestDto requestDto);


    /**
     * 도서 등록을 담당하는 메서드입니다.
     *
     * @param requestDto 도서 등록을 위한 정보를 담은 dto 객체입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */

    void modifyProduct(Long productNo, ModifyProductRequestDto requestDto);

    /**
     * @param productNo 삭제할 상품 번호입니다.
     * @author 이하늬
     */
    void removeProduct(Long productNo);

    /**
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품 entity를 반환합니다.
     * @author 이하늬
     */
    Product findProduct(Long productNo);
}
