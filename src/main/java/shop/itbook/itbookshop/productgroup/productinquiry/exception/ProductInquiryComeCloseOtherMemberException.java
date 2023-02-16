package shop.itbook.itbookshop.productgroup.productinquiry.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryComeCloseOtherMemberException extends RuntimeException {

    public static final String MESSAGE = "다른 회원의 비공개 상품문의는 접근할 수 없습니다.";

    public ProductInquiryComeCloseOtherMemberException() {
        super(MESSAGE);
    }
}
