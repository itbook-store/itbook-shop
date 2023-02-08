package shop.itbook.itbookshop.paymentgroup.payment.service;

import shop.itbook.itbookshop.paymentgroup.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.dto.response.PaymentResponseDto;

/**
 * 결제 서비스 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PayService {

    /**
     * @param paymentRequestDto 결제 승인 요청 시 담아 보낼 request body 파라미터 입니다.
     * @return 결제 승인 성공 시 반환 받을 응답 객체입니다.
     * @author 이하늬
     */
    PaymentResponseDto requestApprovePayment(PaymentApproveRequestDto paymentRequestDto);
}
