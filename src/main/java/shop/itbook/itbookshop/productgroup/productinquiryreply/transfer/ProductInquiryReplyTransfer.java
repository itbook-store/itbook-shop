package shop.itbook.itbookshop.productgroup.productinquiryreply.transfer;

import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;

/**
 * 상품문의 답글의 엔티티와 dto 간의 변환을 작성한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryReplyTransfer {

    public ProductInquiryReplyTransfer() {
    }

    public static ProductInquiryReply dtoToEntity(ProductInquiryReplyRequestDto requestDto) {
        return ProductInquiryReply.builder()
            .productInquiryReplyTitle(requestDto.getProductInquiryReplyTitle())
            .productInquiryReplyContent(requestDto.getProductInquiryReplyContent())
            .build();
    }
}
