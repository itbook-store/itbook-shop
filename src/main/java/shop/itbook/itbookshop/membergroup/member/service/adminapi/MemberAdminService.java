package shop.itbook.itbookshop.membergroup.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

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
     * @param memberNo 멤버 번호로 테이블에서 멤버를 찾습니다.
     * @return MemberResponseDto를 반환합니다.
     * @author 노수연
     */
    Member findMember(Long memberNo);

    /**
     * 모든 멤버 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return MemberResponseDto 리스트를 반환합니다.
     * @author 노수연
     */
    List<MemberResponseDto> findMemberList();

    /**
     * 멤버를 추가하는 로직을 담당하는 메서드입니다.
     *
     * @param requestDto 멤버 저장 dto를 받아와 테이블에 멤버를 추가합니다.
     * @return 추가된 멤버의 멤버 번호를 반환합니다.
     * @author 노수연
     */
    Long addMember(MemberSaveRequestDto requestDto);

    /**
     * 특정 멤버를 수정하는 로직을 담당하는 메서드입니다.
     *
     * @param memberNo   멤버 번호로 테이블에서 해당 멤버를 찾습니다.
     * @param requestDto 멤버 수정 dto를 받아와 테이블에 멤버 정보를 수정합니다.
     * @return 반환값은 없습니다.
     * @author 노수연
     */
    Void updateMember(Long memberNo, MemberUpdateRequestDto requestDto);

    /**
     * 특정 멤버를 삭제하는 로직을 담당한 메서드입니다.
     *
     * @param memberNo 멤버 번호로 테이블에서 해당 멤버를 찾아 삭제합니다.
     * @return 반환 값은 없습니다.
     * @author 노수연
     */
    Void deleteMember(Long memberNo);

}
