package shop.itbook.itbookshop.membergroup.memberdestination.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * 회원 배송지 엔티티 관련 로직을 처리하기 위한 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public interface MemberDestinationService {
    List<MemberDestinationResponseDto> findMemberDestinationResponseDtoByMemberNo(
        Long memberNo);

    void deleteMemberDestination(
        List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList);

    Long addMemberDestination(MemberDestinationRequestDto memberDestinationRequestDto);

    MemberDestination findByRecipientDestinationNo(Long recipientDestinationNo);

    void modifyMemberDestination(Long recipientDestinationNo,
                                 MemberDestinationRequestDto memberDestinationRequestDto);
}
