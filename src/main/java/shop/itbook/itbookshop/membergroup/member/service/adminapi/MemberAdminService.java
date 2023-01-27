package shop.itbook.itbookshop.membergroup.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;

/**
 * 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    /**
     * 특정 memberNo로 멤버 조회 기능을 담당하는 메서드입니다.
     * 관리자는 개별 회원을 조회할 때 비밀번호를 제외한 정보들을 조회할 수 있습니다.
     *
     * @param memberId 멤버 아이디로 테이블에서 멤버를 찾습니다.
     * @return MemberResponseDto를 반환합니다.
     * @author 노수연
     */
    MemberExceptPwdResponseDto findMember(String memberId);

    /**
     * 모든 멤버 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return MemberResponseDto 리스트를 반환합니다.
     * @author 노수연
     */
    List<MemberExceptPwdResponseDto> findMemberList();

    /**
     * 관리자는 특정 멤버의 상태를 '차단회원'과 '정상회원'으로 변경할 수 있습니다.
     *
     * @param memberId   멤버 아이디로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto 멤버 상태 값이 담긴 dto를 받아와 테이블에서 해당 멤버의 멤버상태를 변경합니다.
     * @author 노수연
     */
    void modifyMember(String memberId, MemberStatusUpdateAdminRequestDto requestDto);

}
