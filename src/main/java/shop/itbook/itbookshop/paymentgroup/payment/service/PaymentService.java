package shop.itbook.itbookshop.paymentgroup.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderNoResponseDto;

/**
 * The interface Payment service.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentService {
    /**
     * 주문번호로 paymentKey를 반환하는 기능을 합니다.
     *
     * @param orderNo 조회할 주문 번호입니다.
     * @return the string
     */
    String findPaymentKey(Long orderNo);

    OrderNoResponseDto requestPayment(PaymentApproveRequestDto paymentApproveRequestDto);

    /**
     * 토스 서버에 결제 취소를 요청 하여 결제 취소 하는 기능을 담당합니다.
     *
     * @param paymentCanceledRequestDto 결제 취소를 하기 위해 필요한 주문 번호와 취소 사유에 대한 정보를 담고 있습니다.
     * @return 결제 취소가 된 주문 번호를 반환합니다.
     */
    OrderNoResponseDto cancelPayment(PaymentCanceledRequestDto paymentCanceledRequestDto)
        throws JsonProcessingException;
}
