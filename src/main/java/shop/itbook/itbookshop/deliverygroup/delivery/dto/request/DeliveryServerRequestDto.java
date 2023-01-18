package shop.itbook.itbookshop.deliverygroup.delivery.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 배송 더미 서버에 요청할 정보를 담은 Dto 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoArgsConstructor
@Getter
public class DeliveryServerRequestDto {

    @NotNull(message = "주문 번호는 Null 일 수 없습니다.")
    @Min(value = 1, message = "주문 번호는 최소 1 이상이여야 합니다.")
    private Long orderNo;
    @NotNull(message = "수령인 이름은 Null 일 수 없습니다.")
    private String receiverName;
    @NotNull(message = "수령 주소는 Null 일 수 없습니다.")
    private String receiverAddress;
    @NotNull(message = "수령 상세 주소는 Null 일 수 없습니다.")
    private String receiverDetailAddress;
    @NotNull(message = "수령인의 핸드폰 번호는 Null 일 수 없습니다.")
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
    public DeliveryServerRequestDto(Long orderNo, String receiverName, String receiverAddress,
                                    String receiverDetailAddress, String receiverPhoneNumber) {
        this.orderNo = orderNo;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverDetailAddress = receiverDetailAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
    }
}
