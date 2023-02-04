package shop.itbook.itbookshop.membergroup.memberdestination.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주문서 작성 시 회원의 배송지 정보들을 반환하기 위한 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class MemberDestinationResponseDto {
    String recipientName;

    String recipientPhoneNumber;

    Integer postcode;

    String roadNameAddress;

    String recipientAddressDetails;

    /**
     * 회원 배송지 정보 Dto 의 생성자입니다.
     *
     * @param recipientName           수령인 이름
     * @param recipientPhoneNumber    핸드폰 번호
     * @param postcode                우편번호
     * @param roadNameAddress         도로명 주소
     * @param recipientAddressDetails 상세 주소
     */
    @Builder
    public MemberDestinationResponseDto(String recipientName, String recipientPhoneNumber,
                                        Integer postcode,
                                        String roadNameAddress, String recipientAddressDetails) {
        this.recipientName = recipientName;
        this.recipientPhoneNumber = recipientPhoneNumber;
        this.postcode = postcode;
        this.roadNameAddress = roadNameAddress;
        this.recipientAddressDetails = recipientAddressDetails;
    }
}
