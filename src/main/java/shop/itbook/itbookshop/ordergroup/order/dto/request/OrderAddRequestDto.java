package shop.itbook.itbookshop.ordergroup.order.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

/**
 * 주문 등록을 위한 Dto 입니다.
 * 주문서 작성이 완료됐을 경우 해당 정보를 등록합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
public class OrderAddRequestDto {

    private List<Long> productNoList;
    private List<Integer> productCntList;
    private LocalDate selectedDeliveryDate;
    private String recipientName;
    private String recipientPhoneNumber;
    private Integer postcode;
    private String roadNameAddress;
    private String recipientAddressDetails;
}
