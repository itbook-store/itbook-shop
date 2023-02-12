package shop.itbook.itbookshop.productgroup.productinquiryreply.service;

import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;

/**
 * 상품 문의 답글 서비스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryReplyService {


    /**
     * 프론트 서버에서 상품문의 답글 정보가 넘어오면 이를 테이블에 저장하는 메서드입니다.
     *
     * @param requestDto 프론트에서 넘어온 정보들을 저장한 DTO 입니다.
     * @return the long 상품문의답글을 저장한 뒤 저장된 상품문의답글번호를 반환합니다.
     * @author 노수연
     */
    Integer addProductInquiryReply(ProductInquiryReplyRequestDto requestDto);
}
