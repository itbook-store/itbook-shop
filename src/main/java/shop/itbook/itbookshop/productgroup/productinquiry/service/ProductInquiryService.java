package shop.itbook.itbookshop.productgroup.productinquiry.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;

/**
 * 상품문의 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryService {


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
}
