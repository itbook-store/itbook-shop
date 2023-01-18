package shop.itbook.itbookshop.deliverygroup.deliverystatus.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import shop.itbook.itbookshop.deliverygroup.deliverystatusenum.DeliveryStatusEnum;

/**
 * 배송 상태 테이블의 Enum 의 한글을 지원합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Converter
public class DeliveryStatusEnumConverter implements AttributeConverter<DeliveryStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(DeliveryStatusEnum deliveryStatusEnum) {
        return deliveryStatusEnum.getDeliveryStatus();
    }

    @Override
    public DeliveryStatusEnum convertToEntityAttribute(String s) {
        return DeliveryStatusEnum.stringToEnum(s);
    }
}
