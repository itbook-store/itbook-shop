package shop.itbook.itbookshop.common.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.itbook.itbookshop.auth.exception.InvalidAuthRequestException;
import shop.itbook.itbookshop.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.exception.NoParentCategoryException;
import shop.itbook.itbookshop.category.service.impl.AlreadyAddedCategoryNameException;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.membership.exception.MembershipNotFoundException;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.role.exception.RoleNotFoundException;
import shop.itbook.itbookshop.role.exception.RoleNotFoundException;

/**
 * rest controller 에서 예외발생시 종합적인 처리를 해주기 위한 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestControllerAdvice
public class RestControllerAdvisor {

    /**
     * 400에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {CategoryNotFoundException.class,
        CategoryContainsProductsException.class,
        NoParentCategoryException.class,
        SearchProductNotFoundException.class,
        MethodArgumentNotValidException.class, MemberNotFoundException.class,
        MembershipNotFoundException.class,
        InvalidAuthRequestException.class,
        AlreadyAddedCategoryNameException.class
        RoleNotFoundException.class
    })
    public ResponseEntity<CommonResponseBody<Void>> badRequestException400(
        Exception e) {

        CommonResponseBody<Void> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                e.getMessage()), null);

        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

    /**
     * 403에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {MemberForbiddenException.class})
    public ResponseEntity<CommonResponseBody<Void>> forbiddenException403(
        RuntimeException e) {

        CommonResponseBody<Void> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                e.getMessage()), null);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

    /**
     * 500에 해당하는 예외들을 한번에 처리하는 메소드입니다.
     *
     * @param e 실제 발생한 예외객체입니다.
     * @return 에러메세지를 response entity 에 담아서 전송합니다.
     * @author 최겸준
     */
    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<CommonResponseBody<Void>> internalErrorException500(Exception e) {

        CommonResponseBody<Void> exceptionCommonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                e.getMessage()), null);

        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON)
            .body(exceptionCommonResponseBody);
    }

}
