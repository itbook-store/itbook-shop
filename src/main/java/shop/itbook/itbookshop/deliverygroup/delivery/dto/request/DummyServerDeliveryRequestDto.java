package shop.itbook.itbookshop.deliverygroup.delivery.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 배송 더미 서버에 요청할 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class DummyServerDeliveryRequestDto {

    private Long orderNo;
    private String receiverName;
    private String receiverAddress;
    private String receiverDetailAddress;
    private String receiverPhoneNumber;

    /**
     * 배송 더미서버에 보낼 객체를 만들기 위한 생성자입니다.
     *
     * @param orderNo               주문 번호
     * @param receiverName          수령인 이름
     * @param receiverAddress       수령할 주소
     * @param receiverDetailAddress 수령할 상세주소
     * @param receiverPhoneNumber   수령인 핸드폰 번호
     */
    @Builder
    public DummyServerDeliveryRequestDto(Long orderNo, String receiverName, String receiverAddress,
                                         String receiverDetailAddress, String receiverPhoneNumber) {
        this.orderNo = orderNo;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverDetailAddress = receiverDetailAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
    }
}
