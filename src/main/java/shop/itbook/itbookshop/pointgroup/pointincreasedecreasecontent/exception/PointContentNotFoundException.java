package shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.exception;

/**
 * @author 최겸준
 * @since 1.0
 */
public class PointContentNotFoundException extends RuntimeException {

    public static final String MESSAGE = "요청한 포인트적립차감 내용은 실제로 존재하지 않습니다.";

    public PointContentNotFoundException() {
        super(MESSAGE);
    }
}
