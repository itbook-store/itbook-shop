package shop.itbook.itbookshop.deliverygroup.delivery.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송 정보와 주문자의 정보까지 상세한 정보를 반환하는 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class DeliveryDetailResponseDto {

    private Long deliveryNo;
    private Long orderNo;
    private String receiverName;
    private String receiverAddress;
    private String receiverDetailAddress;
    private String receiverPhoneNumber;
    private String trackingNo;

    /**
     * ResponseDto 의 모든 값을 포함하는 생성자 입니다.
     *
     * @param deliveryNo            배송 번호
     * @param orderNo               주문 번호
     * @param receiverName          수령인 이름
     * @param receiverAddress       수령 주소
     * @param receiverDetailAddress 수령 상세 주소
     * @param receiverPhoneNumber   수령인 핸드폰 번호
     * @param trackingNo            운송장 번호
     */
    @Builder
    public DeliveryDetailResponseDto(Long deliveryNo, Long orderNo, String receiverName,
                                     String receiverAddress, String receiverDetailAddress,
                                     String receiverPhoneNumber, String trackingNo) {
        this.deliveryNo = deliveryNo;
        this.orderNo = orderNo;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverDetailAddress = receiverDetailAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.trackingNo = trackingNo;
    }
}
