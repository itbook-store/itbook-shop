package shop.itbook.itbookshop.productgroup.product.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
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
     * @return Pk 값인 상품 번호를 반환합니다.
     * @author 이하늬
     */
    Long addProduct(ProductAddRequestDto requestDto, MultipartFile thumbnails);


    /**
     * 상품 수정을 담당하는 메서드입니다.
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등의 수정까지 포함하여 상품 수정을 수행합니다.
     *
     * @param productNo  the product no
     * @param requestDto 도서 정보를 포함한 상품 수정에 필요한 dto 객체입니다.                   도서가 아닐 경우 도서와 관련된 정보는 null 값이 되어 이후 수정할 예정입니다.
     * @param thumbnails 수정할 썸네일 사진 파일입니다.
     * @author 이하늬
     */
    void modifyProduct(Long productNo, ProductModifyRequestDto requestDto,
                       MultipartFile thumbnails);

    /**
     * 상품 삭제를 담당하는 메서드입니다.
     * 상품 테이블과 연관관계가 있는 상품 테이블, 도서 테이블, 카테고리-상품 테이블 등의 삭제까지 포함하여 상품 수정을 수행합니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     * @param fieldName
     * @author 이하늬
     */
    void changeBooleanField(Long productNo, String fieldName);

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
     * @return 찾은 상품 상세정보를 담은 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductList(Pageable pageable, boolean isAdmin);

    /**
     * 장바구니에 상품을 담기 위해 상품 번호 리스트에 해당하는 상품 조회를 담당하는 메서드입니다.
     * 판매여부가 true인 상품만 장바구니에 담기가 가능합니다.
     * 파라미터로 1,2,,4와 같은 값이 들어왔을 때 null을 제거해주는 로직을 추가했습니다.
     *
     * @param pageable      the pageable
     * @param productNoList 조회할 상품 번호 리스트 입니다.
     * @return 찾은 상품의 상세정보를 담은 리스트를 반환합니다.
     * @author 이하늬
     */
    Page<ProductDetailsResponseDto> findProductListByProductNoListForUser(Pageable pageable,
                                                                          List<Long> productNoList);

    Page<ProductDetailsResponseDto> findProductListByProductNoListForAdmin(Pageable pageable,
                                                                           List<Long> productNoList);

    /**
     * 상품 조회를 담당하는 메서드입니다.
     * 관리자는 모든 상품을 조회 가능하지만, 사용자는 노출여부가 false인 상품을 조회 가능합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 찾은 상품을 FindProductResponseDto 객체에 담아 반환합니다.
     * @author 이하늬
     */
    ProductDetailsResponseDto findProduct(Long productNo);

    Product updateProduct(BookModifyRequestDto requestDto, Long productNo);

    ProductAddRequestDto toProductRequestDto(ProductBookRequestDto requestDto);

    /**
     * 모든 제품이 팔 수 있는 상품인지 검사 합니다.
     *
     * @param productNoList 검사할 상품들의 번호 리스트.
     * @param productCnt    검사할 상품들의 각각 구매할 개수 리스트.
     * @author 정재원 *
     */
    void checkSellProductList(List<Long> productNoList, List<Integer> productCnt);

    /**
     * 팔 수 있는 상품인지 검사합니다.
     *
     * @param productNo  검사할 상품의 번호
     * @param productCnt 검사할 상품의 구매할 개수
     * @return 팔 수 있으면 true, 팔 수 없으면 false
     * @author 정재원 *
     */
    boolean canSellProduct(Long productNo, Integer productCnt);

    void changeDailyHits(Long productNo);
}
