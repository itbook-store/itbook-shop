package shop.itbook.itbookshop.membergroup.memberdestination.transfer;

import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * 회원 배송지의 Dto 와 Entity 간의 변환을 담당하는 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public class MemberDestinationTransfer {

    /**
     * 회원 배송지 엔티티를 Dto 로 변환
     *
     * @param memberDestination 회원 배송지 엔티티
     * @return 회원 배송지 엔티티의 모든 정보를 포함하는 Dto
     * @author 정재원 *
     */
    public static MemberDestinationResponseDto entityToDto(MemberDestination memberDestination) {
        return MemberDestinationResponseDto.builder()
            .recipientName(memberDestination.getRecipientName())
            .recipientPhoneNumber(memberDestination.getRecipientPhoneNumber())
            .postcode(memberDestination.getPostcode())
            .roadNameAddress(memberDestination.getRoadNameAddress())
            .recipientAddressDetails(memberDestination.getRecipientAddressDetails())
            .build();
    }
}
