package shop.itbook.itbookshop.productgroup.productinquiryreply.resultmessageenum;

import lombok.Getter;

/**
 * 상품문의 답글 성공 메시지 enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum ProductInquiryReplyResultMessageEnum {

    PRODUCT_INQUIRY_REPLY_ADD_SUCCESS("상품문의 답글 생성에 성공하였습니다."),
    PROCUCT_INQUIRY_LIST_GET_SUCCESS("상품문의 답글 리스트 반환에 성공하였습니다.");

    private String resultMessage;

    ProductInquiryReplyResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
