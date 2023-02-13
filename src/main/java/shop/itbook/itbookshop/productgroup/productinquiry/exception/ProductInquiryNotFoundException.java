package shop.itbook.itbookshop.productgroup.productinquiry.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryNotFoundException extends RuntimeException {

    public static final String MESSAGE = "상품문의를 찾을 수 없습니다.";

    public ProductInquiryNotFoundException() {
        super(MESSAGE);
    }
}
