package shop.itbook.itbookshop.productgroup.review.resultMessageEnum;

import lombok.Getter;

/**
 * 성공했을때 반환할 메세지를 작성한 enum 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum ReviewResultMessageEnum {

    REVIEW_LIST_GET_SUCCESS("리뷰 리스트 반환에 성공하였습니다."),
    REVIEW_GET_SUCCESS("리뷰 반환에 성공하였습니다."),
    REVIEW_ADD_SUCCESS("리뷰 등록에 성공하였습니다."),
    REVIEW_DELETE_SUCCESS("리뷰 삭제에 성공하였습니다."),
    REVIEW_MODIFY_SUCCESS("리뷰 수정에 성공하였습니다."),

    UNWRITTEN_REVIEW_ORDER_PRODUCT_LIST_GET_SUCCESS("아직 리뷰가 쓰여지지 않은 주문 상품 리스트 반환에 성공하였습니다.");

    private String resultMessage;

    ReviewResultMessageEnum(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
