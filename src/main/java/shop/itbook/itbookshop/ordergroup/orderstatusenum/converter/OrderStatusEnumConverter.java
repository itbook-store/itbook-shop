package shop.itbook.itbookshop.ordergroup.orderstatusenum.converter;

import javax.persistence.AttributeConverter;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * DB 데이터 교환 시에 주문 상태 Enum 의 한글 상태값 변환을 담당합니다.
 *
 * @author 정재원
 * @since 1.0
 */
public class OrderStatusEnumConverter implements AttributeConverter<OrderStatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatusEnum orderStatusEnum) {
        return orderStatusEnum.getOrderStatus();
    }

    @Override
    public OrderStatusEnum convertToEntityAttribute(String s) {
        return OrderStatusEnum.stringToEnum(s);
    }
}
