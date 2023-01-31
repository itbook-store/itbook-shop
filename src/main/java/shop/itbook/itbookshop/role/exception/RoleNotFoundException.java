package shop.itbook.itbookshop.role.exception;

/**
 * @author 노수연
 * @since 1.0
 */
public class RoleNotFoundException extends RuntimeException {

    public static final String MESSAGE = "해당 권한이 존재하지 않습니다.";

    public RoleNotFoundException() {
        super(MESSAGE);
    }
}
