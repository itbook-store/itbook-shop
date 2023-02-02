package shop.itbook.itbookshop.productgroup.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품 Service 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductService {


    /**
     * 상품 등록을 담당하는 메서드입니다.
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등의 등록까지 포함하여 상품 등록을 수행합니다.
     *
     * @param requestDto 도서 정보를 포함한 상품 등록에 필요한 dto 객체입니다.                   도서가 아닐 경우 도서와 관련된 정보는 null 값이 되어 이후 수정할 예정입니다.
     * @param thumbnails 썸네일 사진 파일입니다.
     * @param ebook      ebook 파일입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addProduct(ProductBookRequestDto requestDto, MultipartFile thumbnails,
                    MultipartFile ebook);


    /**
     * 상품 수정을 담당하는 메서드입니다.
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등의 수정까지 포함하여 상품 수정을 수행합니다.
     *
     * @param productNo  the product no
     * @param requestDto 도서 정보를 포함한 상품 수정에 필요한 dto 객체입니다.                   도서가 아닐 경우 도서와 관련된 정보는 null 값이 되어 이후 수정할 예정입니다.
     * @param thumbnails 수정할 썸네일 사진 파일입니다.
     * @param ebook      수정할 ebook 파일입니다.
     * @author 이하늬
     */
    void modifyProduct(Long productNo, ProductBookRequestDto requestDto, MultipartFile thumbnails,
                       MultipartFile ebook);

    /**
     * 상품 삭제를 담당하는 메서드입니다.
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등의 삭제까지 포함하여 상품 수정을 수행합니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     * @author 이하늬
     */
    void removeProduct(Long productNo);

    /**
     * 상품 엔티티 조회를 담당하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품 entity를 반환합니다.
     * @author 이하늬
     */
    Product findProductEntity(Long productNo);

    /**
     * 모든 상품 조회를 담당하는 메서드입니다.
     * 관리자는 모든 상품을 조회 가능하지만, 사용자는 노출여부가 false인 상품을 조회 가능합니다.
     *
     * @param pageable the pageable
     * @param isAdmin  관리자 여부 입니다.
     * @return 찾은 상품 entity를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductList(Pageable pageable, boolean isAdmin);

    /**
     * 상품유형별로 상품 조회를 담당하는 메서드입니다.
     * 관리자는 모든 상품을 조회 가능하지만, 사용자는 노출여부가 false인 상품을 조회 가능합니다.
     *
     * @param pageable the pageable
     * @return 노출여부가 false인 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListByProductTypeNo(Pageable pageable,
                                                                   Integer productTypeNo,
                                                                   boolean isAdmin);

    /**
     * 상품 조회를 담당하는 메서드입니다.
     * 관리자는 모든 상품을 조회 가능하지만, 사용자는 노출여부가 false인 상품을 조회 가능합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품을 FindProductResponseDto 객체에 담아 반환합니다.
     * @author 이하늬
     */
    ProductDetailsResponseDto findProduct(Long productNo);

}
