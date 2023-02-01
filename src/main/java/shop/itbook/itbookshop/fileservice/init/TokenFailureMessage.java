package shop.itbook.itbookshop.fileservice.init;

/**
 * @author 이하늬
 * @since 1.0
 */
public enum TokenFailureMessage {
    FAILURE_INVALID_MESSAGE("토큰이 유효하지 않습니다."),
    FAILURE_REQUEST_MESSAGE("토큰 요청을 실패하였습니다.");

    private String message;

    TokenFailureMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
