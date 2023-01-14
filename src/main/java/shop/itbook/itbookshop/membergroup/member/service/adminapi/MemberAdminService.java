package shop.itbook.itbookshop.membergroup.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;

/**
 * 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    /**
     * 특정 memberNo로 멤버 조회 기능을 담당하는 메서드입니다.
     *
     * @param memberId 멤버 아이디로 테이블에서 멤버를 찾습니다.
     * @return MemberResponseDto를 반환합니다.
     * @author 노수연
     */
    MemberResponseProjectionDto findMember(String memberId);

    /**
     * 모든 멤버 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return MemberResponseDto 리스트를 반환합니다.
     * @author 노수연
     */
    List<MemberResponseProjectionDto> findMemberList();

    /**
     * 특정 멤버를 수정하는 로직을 담당하는 메서드입니다.
     *
     * @param memberId   멤버 아이디로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto 멤버 수정 dto를 받아와 테이블에 멤버 정보를 수정합니다.
     * @return 반환값은 없습니다.
     * @author 노수연
     */
    void modifyMember(String memberId, MemberUpdateAdminRequestDto requestDto);

}
