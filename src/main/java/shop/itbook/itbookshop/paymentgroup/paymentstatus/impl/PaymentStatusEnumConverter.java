package shop.itbook.itbookshop.paymentgroup.paymentstatus.impl;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Converter
public class PaymentStatusEnumConverter implements AttributeConverter<PaymentStatusEnum, String> {
    @Override
    public String convertToDatabaseColumn(PaymentStatusEnum paymentStatusEnum) {
        return paymentStatusEnum.getPaymentStatus();
    }

    @Override
    public PaymentStatusEnum convertToEntityAttribute(String dbData) {
        return PaymentStatusEnum.stringToEnum(dbData);
    }
}
