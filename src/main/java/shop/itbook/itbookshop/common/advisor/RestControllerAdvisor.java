package shop.itbook.itbookshop.common.advisor;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.itbook.itbookshop.auth.exception.InvalidAuthRequestException;
import shop.itbook.itbookshop.book.exception.BookNotFoundException;
import shop.itbook.itbookshop.cart.exception.CartNotFountException;
import shop.itbook.itbookshop.category.exception.AlreadyAddedCategoryNameException;
import shop.itbook.itbookshop.category.exception.CategoryContainsProductsException;
import shop.itbook.itbookshop.category.exception.CategoryNotFoundException;
import shop.itbook.itbookshop.category.exception.NotChildCategoryException;
import shop.itbook.itbookshop.common.exception.MemberForbiddenException;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.coupongroup.coupon.exception.CouponNotFoundException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.AlreadyAddedCouponIssueMemberCouponException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.CouponIssueNotFoundException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.CouponQuantityExhaustedException;
import shop.itbook.itbookshop.coupongroup.couponissue.exception.NotPointCouponException;
import shop.itbook.itbookshop.coupongroup.coupontype.exception.CouponTypeNotFoundException;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.exception.NotMatchCouponException;
import shop.itbook.itbookshop.coupongroup.usagestatus.exception.UsageStatusNotFoundException;
import shop.itbook.itbookshop.deliverygroup.delivery.exception.DeliveryNoWaitStatusException;
import shop.itbook.itbookshop.fileservice.exception.ObjectStroageFileUploadException;
import shop.itbook.itbookshop.fileservice.exception.InvalidTokenException;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.memberdestination.exception.MemberDestinationComeCloseOtherMemberException;
import shop.itbook.itbookshop.membergroup.memberdestination.exception.MemberDestinationNotFoundException;
import shop.itbook.itbookshop.membergroup.membership.exception.MembershipNotFoundException;
import shop.itbook.itbookshop.ordergroup.order.exception.CanNotSaveRedisException;
import shop.itbook.itbookshop.ordergroup.order.exception.AmountException;
import shop.itbook.itbookshop.ordergroup.order.exception.InvalidOrderCodeException;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchCategoryNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.MismatchProductNoWhenCouponApplyException;
import shop.itbook.itbookshop.ordergroup.order.exception.NotOrderTotalCouponException;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderSubscriptionNotFirstSequenceException;
import shop.itbook.itbookshop.ordergroup.order.exception.ProductStockIsZeroException;
import shop.itbook.itbookshop.ordergroup.order.util.CanNotApplyCouponException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentCancelException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentException;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.exception.PaymentStatusNotFoundException;
import shop.itbook.itbookshop.pointgroup.pointhistory.exception.LackOfPointException;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.exception.PointContentNotFoundException;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidProductException;
import shop.itbook.itbookshop.productgroup.product.exception.NotSellableProductException;
import shop.itbook.itbookshop.productgroup.product.exception.ProductNotFoundException;
import shop.itbook.itbookshop.productgroup.product.exception.SearchProductNotFoundException;
import shop.itbook.itbookshop.productgroup.productinquiry.exception.ProductInquiryComeCloseOtherMemberException;
import shop.itbook.itbookshop.productgroup.producttype.exception.ProductTypeNotFoundException;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewAlreadyRegisteredException;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewComeCloseOtherMemberException;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewNotFoundException;
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
    @ExceptionHandler(value = {
        CategoryNotFoundException.class,
        CategoryContainsProductsException.class,
        NotChildCategoryException.class,
        PointContentNotFoundException.class,
        ConstraintViolationException.class,
        LackOfPointException.class,
        CanNotSaveRedisException.class,
        OrderSubscriptionNotFirstSequenceException.class,
        ProductStockIsZeroException.class,
        NotStatusOfOrderCancel.class,
        MismatchCategoryNoWhenCouponApplyException.class,
        MismatchProductNoWhenCouponApplyException.class,
        NotOrderTotalCouponException.class,
        SearchProductNotFoundException.class,
        MethodArgumentNotValidException.class,
        MemberNotFoundException.class,
        MembershipNotFoundException.class,
        DeliveryNoWaitStatusException.class,
        InvalidAuthRequestException.class,
        AlreadyAddedCategoryNameException.class,
        RoleNotFoundException.class,
        CartNotFountException.class,
        RoleNotFoundException.class,
        MemberDestinationNotFoundException.class,
        AlreadyAddedCouponIssueMemberCouponException.class,
        CouponIssueNotFoundException.class,
        NotPointCouponException.class,
        CouponQuantityExhaustedException.class,
        CouponTypeNotFoundException.class,
        UsageStatusNotFoundException.class,
        CouponNotFoundException.class,
        MemberDestinationNotFoundException.class,
        ProductNotFoundException.class,
        BookNotFoundException.class,
        ProductTypeNotFoundException.class,
        InvalidTokenException.class,
        ReviewAlreadyRegisteredException.class,
        InvalidProductException.class,
        ReviewNotFoundException.class,
        InvalidPaymentException.class,
        InvalidPaymentCancelException.class,
        PaymentStatusNotFoundException.class,
        NotSellableProductException.class,
        MismatchCategoryNoWhenCouponApplyException.class,
        MismatchProductNoWhenCouponApplyException.class,
        NotOrderTotalCouponException.class,
        CanNotApplyCouponException.class,
        MemberDestinationComeCloseOtherMemberException.class,
        ReviewComeCloseOtherMemberException.class,
        ProductInquiryComeCloseOtherMemberException.class,
        ObjectStroageFileUploadException.class,
        NotMatchCouponException.class,
        ProductInquiryComeCloseOtherMemberException.class,
        AmountException.class,
        InvalidOrderException.class,
        InvalidOrderCodeException.class
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
