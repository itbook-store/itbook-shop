package shop.itbook.itbookshop.productgroup.product.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ModifyProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.FindProductResponseDto;
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
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등을 포함하여 상품 등록을 수행합니다.
     *
     * @param requestDto 도서 정보를 포함한 상품 등록에 필요한 dto 객체입니다.
     *                   도서가 아닐 경우 도서와 관련된 정보는 null 값이 되어 이후 수정할 예정입니다.
     * @param thumbnails 썸네일 사진 파일입니다.
     * @param ebook      ebook 파일입니다.
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addProduct(ProductBookRequestDto requestDto, MultipartFile thumbnails,
                    MultipartFile ebook);


    /**
     * @param productNo  the product no
     * @param requestDto the request dto
     * @author
     */
    void modifyProduct(Long productNo, ProductBookRequestDto requestDto);

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
    Product findProductEntity(Long productNo);

    List<FindProductResponseDto> findProductList();
}
