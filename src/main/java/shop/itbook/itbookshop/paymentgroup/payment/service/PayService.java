package shop.itbook.itbookshop.paymentgroup.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * 결제 서비스 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PayService {

    /**
     * 결제 서버에 결제 승인요청을 하는 기능을 담당합니다.
     *
     * @param paymentApproveRequestDto 결제 승인 요청 시 담아 보낼 request body 파라미터 입니다.
     * @return 결제 승인 성공 시 반환 받을 응답 객체입니다.
     * @author 이하늬
     */
    PaymentResponseDto.PaymentDataResponseDto requestApprovePayment(
        PaymentApproveRequestDto paymentApproveRequestDto);

    /**
     * 결제 서버에 결제 취소 요청을 하는 기능을 담당합니다.
     *
     * @param paymentApproveRequestDto the payment approve request dto
     * @return the payment response dto
     */
    PaymentResponseDto.PaymentDataResponseDto requestCanceledPayment(
        PaymentCanceledRequestDto paymentApproveRequestDto, String paymentKey)
        throws JsonProcessingException;

}
