package shop.itbook.itbookshop.fileservice.exception;

/**
 * @author 이하늬
 * @since 1.0
 */
public enum TokenFailureMessage {
    INVALID_TOKEN_MESSAGE("토큰이 유효하지 않습니다."),
    FAILURE_REQUEST_TOKEN_MESSAGE("토큰 요청을 실패하였습니다.");

    private String message;

    TokenFailureMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
