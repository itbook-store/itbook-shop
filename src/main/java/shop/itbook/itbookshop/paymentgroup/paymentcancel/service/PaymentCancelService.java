package shop.itbook.itbookshop.paymentgroup.paymentcancel.service;

import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentCancelService {
    PaymentCancel addPaymentCancel(Payment payment,
                                   PaymentResponseDto.PaymentDataResponseDto response);

}
