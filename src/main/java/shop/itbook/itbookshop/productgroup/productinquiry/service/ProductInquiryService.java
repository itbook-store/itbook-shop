package shop.itbook.itbookshop.productgroup.productinquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;

/**
 * 상품문의 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryService {


    /**
     * 상품문의 번호로 테이블에서 데이터를 찾아 엔티티로 반환합니다.
     *
     * @param productInquiryNo 상품 문의 번호입니다.
     * @return 상품문의 엔티티를 반환합니다.
     * @author 노수연
     */
    ProductInquiry findProductInquiryByProductInquiryNo(Long productInquiryNo);

    /**
     * 모든 상품 문의 리스트를 반환합니다.
     *
     * @param pageable 페이징 처리하여 불러옵니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ProductInquiryResponseDto> findProductInquiryList(Pageable pageable);


    /**
     * 프론트 서버에서 dto를 통해 상품문의 정보들이 넘어오면 이를 테이블에 저장합니다.
     *
     * @param requestDto 프론트에서 넘어온 정보들을 저장한 DTO 입니다.
     * @return the long 상품문의를 테이블에 저장한 뒤 저장된 productInquiryNo를 반환합니다.
     * @author 노수연
     */
    Long addProductInquiry(ProductInquiryRequestDto requestDto);


    /**
     * 전체 상품 문의 카운트 수와 답변된 상품문의 카운트 수를 구하는 메서드입니다.
     *
     * @return 구한 카운트 수들을 dto로 반환합니다.
     * @author 노수연
     */
    ProductInquiryCountResponseDto countProductInquiry();


    /**
     * 주문 상품 정보들을 가져오는 메서드입니다.
     *
     * @param pageable 페이징 처리하여 불러옵니다.
     * @param memberNo 멤버 번호로 해당 멤버의 주문 상품 정보들만 불러옵니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ProductInquiryOrderProductResponseDto> findProductInquiryOrderProductList(
        Pageable pageable, Long memberNo);


    /**
     * 상품 문의 정보를 반환하는 메서드입니다.
     *
     * @param productInquiryNo 상품 문의 번호로 테이블에서 데이터를 찾습니다.
     * @return 상품 문의 정보를 담은 dto를 반환합니다.
     * @author 노수연
     */
    ProductInquiryResponseDto findProductInquiry(Long productInquiryNo);

    /**
     * 해당 회원의 모든 상품 문의 리스트를 반환합니다.
     *
     * @param pageable 페이징 처리하여 불러옵니다.
     * @param memberNo 회원번호로 해당 회원의 상품문의들을 찾습니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ProductInquiryResponseDto> findProductInquiryListByMemberNo(Pageable pageable,
                                                                     Long memberNo);
}
