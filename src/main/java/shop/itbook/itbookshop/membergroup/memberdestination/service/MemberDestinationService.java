package shop.itbook.itbookshop.membergroup.memberdestination.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;

/**
 * 회원 배송지 엔티티 관련 로직을 처리하기 위한 클래스
 *
 * @author 정재원
 * @since 1.0
 */
public interface MemberDestinationService {
    List<MemberDestinationResponseDto> findMemberDestinationResponseDtoByMemberNo(
        Long memberNo);
}
