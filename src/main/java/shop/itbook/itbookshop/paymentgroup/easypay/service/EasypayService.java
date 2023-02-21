package shop.itbook.itbookshop.paymentgroup.easypay.service;

import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface EasypayService {
    Easypay addEasyPay(PaymentResponseDto.PaymentDataResponseDto response);
}
