package shop.itbook.itbookshop.paymentgroup.paymentstatus.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.exception.PaymentStatusNotFoundException;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.repository.PaymentStatusRepository;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.service.PaymentStatusService;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class PaymentStatusImpl implements PaymentStatusService {
    private final PaymentStatusRepository paymentStatusRepository;

    @Override
    public PaymentStatus findPaymentStatusEntity(PaymentStatusEnum paymentStatusEnum) {
        return paymentStatusRepository.findPaymentStatusByPaymentStatusEnum(paymentStatusEnum)
            .orElseThrow(PaymentStatusNotFoundException::new);
    }
}
