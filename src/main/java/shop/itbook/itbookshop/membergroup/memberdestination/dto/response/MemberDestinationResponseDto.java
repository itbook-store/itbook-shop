package shop.itbook.itbookshop.membergroup.memberdestination.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 주문서 작성 시 회원의 배송지 정보들을 반환하기 위한 Dto
 *
 * @author 정재원
 * @since 1.0
 */
@Getter
@Builder
public class MemberDestinationResponseDto {
    String recipientName;
    String phoneNumber;
    Integer postcode;
    String address;
    String detailAddress;
}
