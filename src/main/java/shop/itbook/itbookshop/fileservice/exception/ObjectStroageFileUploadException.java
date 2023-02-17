package shop.itbook.itbookshop.fileservice.exception;

/**
 * 파일 업로드에 실패했을 때 발생시킬 예외 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ObjectStroageFileUploadException extends RuntimeException {

    public static final String MESSAGE = "파일을 업로드를 실패하였습니다.";

    public ObjectStroageFileUploadException() {
        super(MESSAGE);
    }
}