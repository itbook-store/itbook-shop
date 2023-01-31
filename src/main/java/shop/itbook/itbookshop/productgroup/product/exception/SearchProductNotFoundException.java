package shop.itbook.itbookshop.productgroup.product.exception;

/**
 * 검색 상품이 존재하지 않을 때 발생시킬 예외 클래스입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
public class SearchProductNotFoundException extends RuntimeException {

    public static final String MESSAGE = "상품 검색결과가 존재하지 않습니다.";

    public SearchProductNotFoundException() {
        super(MESSAGE);
    }
}
