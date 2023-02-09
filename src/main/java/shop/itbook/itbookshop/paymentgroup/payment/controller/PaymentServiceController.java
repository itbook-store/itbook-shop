package shop.itbook.itbookshop.paymentgroup.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.service.PaymentService;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * The type Payment service controller.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class PaymentServiceController {
    private final PaymentService paymentService;

    /**
     * 결제창에서 성공 시 반환 받은 파라미터를 요청 본문에 담아 결제 승인 API를 호출합니다.
     *
     * @param paymentApproveRequestDto 결제창에서 성공 시 파라미터로 반환 받은 파라미터들입니다.
     * @return 결제 승인 시 반환 받은 Payment 객체입니다.
     * @author 이하늬
     */
    @PostMapping("api/admin/payment/request-pay")
    public ResponseEntity<CommonResponseBody<OrderNoResponseDto>> requestPayment(
        @RequestBody PaymentApproveRequestDto paymentApproveRequestDto) {

        OrderNoResponseDto responseDto =
            paymentService.requestPayment(paymentApproveRequestDto);

        CommonResponseBody<OrderNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                PaymentStatusEnum.DONE.getPaymentStatus()), responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 고객에게 입력 받은 취소 이유를 요청 본문에 담아 결제 취소 API를 호출합니다.
     *
     * @param paymentCanceledRequestDto 결제창에서 성공 시 파라미터로 반환 받은 파라미터들입니다.
     * @return 결제 승인 시 반환 받은 Payment 객체입니다.
     * @author 이하늬
     */
    @PostMapping("api/admin/payment/request-cancel")
    public ResponseEntity<CommonResponseBody<OrderNoResponseDto>> cancelPayment(
        @RequestBody PaymentCanceledRequestDto paymentCanceledRequestDto)
        throws JsonProcessingException {

        OrderNoResponseDto responseDto =
            paymentService.cancelPayment(paymentCanceledRequestDto);

        CommonResponseBody<OrderNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                PaymentStatusEnum.CANCELED.getPaymentStatus()), responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
