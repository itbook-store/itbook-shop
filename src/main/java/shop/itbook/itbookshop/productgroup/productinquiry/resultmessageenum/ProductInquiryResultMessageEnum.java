package shop.itbook.itbookshop.productgroup.productinquiry.resultmessageenum;

import lombok.Getter;

/**
 * 상품문의 성공 메세지 enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum ProductInquiryResultMessageEnum {

    PRODUCT_INQUIRY_LIST_GET_SUCCESS("상품 문의 리스트 반환에 성공하였습니다."),
    PRODUCT_INQUIRY_ADD_SUCCESS("상품 문의 생성에 성공하였습니다."),
    PRODUCT_INQUIRY_COUNT_GET_SUCCESS("상품 문의 갯수 반환에 성공하였습니다."),
    PRODUCT_INQUIRY_ORDER_PRODUCT_LIST_GET_SUCCESS("상품문의 작성가능한 주문 상품 리스트 반환에 성공하였습니다."),
    PRODUCT_INQUIRY_GET_SUCCESS("상품 문의 상세 정보 반환에 성공하였습니다.");

    private String resultMessage;

    ProductInquiryResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
