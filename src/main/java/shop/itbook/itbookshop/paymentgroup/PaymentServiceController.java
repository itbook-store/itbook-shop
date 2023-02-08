package shop.itbook.itbookshop.paymentgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.paymentgroup.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.service.impl.TossPayServiceImpl;
import shop.itbook.itbookshop.paymentgroup.paymentstatusenum.PaymentStatusEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;

/**
 * The type Payment service controller.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class PaymentServiceController {
    private final TossPayServiceImpl tossPayServiceImpl;

    /**
     * 결제창에서 성공 시 반환 받은 파라미터를 요청 본문에 담아 결제 승인 API를 호출합니다.
     *
     * @param paymentApproveRequestDto 결제창에서 성공 시 파라미터로 반환 받은 파라미터들입니다.
     * @return 결제 승인 시 반환 받은 Payment 객체입니다.
     * @author 이하늬
     */
    @PostMapping("api/admin/payment/request-pay")
    public ResponseEntity<CommonResponseBody<PaymentResponseDto>> requestPayment(
        @RequestBody PaymentApproveRequestDto paymentApproveRequestDto) {
        PaymentResponseDto responseDto =
            tossPayServiceImpl.requestApprovePayment(paymentApproveRequestDto);

        CommonResponseBody<PaymentResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                PaymentStatusEnum.DONE.getPaymentStatus()), responseDto);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
