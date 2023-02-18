package shop.itbook.itbookshop.ordergroup.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 보기 시에 배송했던 주소 정보를 담고 있는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDestinationDto {
    String recipientName;
    String recipientPhoneNumber;
    Integer postcode;
    String roadNameAddress;
    String recipientAddressDetails;
}
