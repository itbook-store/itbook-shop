package shop.itbook.itbookshop.paymentgroup.paymentstatus.service;

import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentStatusService {
    PaymentStatus findPaymentStatusEntity(PaymentStatusEnum paymentStatusEnum);
}
