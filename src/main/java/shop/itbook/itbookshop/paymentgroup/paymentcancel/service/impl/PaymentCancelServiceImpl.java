package shop.itbook.itbookshop.paymentgroup.paymentcancel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.repository.PaymentCancelRepository;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.service.PaymentCancelService;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.transfer.PaymentCancelTransfer;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentCancelServiceImpl implements PaymentCancelService {

    private final PaymentCancelRepository paymentCancelRepository;

    @Override
    @Transactional
    public PaymentCancel addPaymentCancel(Payment payment,
                                          PaymentResponseDto.PaymentDataResponseDto response) {
        PaymentCancel paymentCancel = PaymentCancelTransfer.dtoToEntity(response);
        paymentCancel.setPaymentNo(payment.getPaymentNo());
        paymentCancel.setPayment(payment);
        return paymentCancelRepository.save(paymentCancel);
    }

}
