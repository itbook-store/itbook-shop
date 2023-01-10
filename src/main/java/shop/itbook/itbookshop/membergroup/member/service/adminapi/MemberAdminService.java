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
     * @param memberNo the member no
     * @return the member
     * @author 노수연
     */
    Member findMember(Long memberNo);

    /**
     * 모든 멤버 리스트를 조회할 수 있는 로직을 담당하는 메서드입니다.
     *
     * @return the list
     * @author 노수연
     */
    List<MemberResponseDto> findMemberList();

    /**
     * 멤버를 추가하는 로직을 담당하는 메서드입니다.
     *
     * @param requestDto the request dto
     * @return the long
     * @author 노수연
     */
    Long addMember(MemberSaveRequestDto requestDto);

    /**
     * 특정 멤버를 수정하는 로직을 담당하는 메서드입니다.
     *
     * @param memberNo   the member no
     * @param requestDto the request dto
     * @return the void
     * @author 노수연
     */
    Void updateMember(Long memberNo, MemberUpdateRequestDto requestDto);

    /**
     * 특정 멤버를 삭제하는 로직을 담당한 메서드입니다.
     *
     * @param memberNo the member no
     * @return the void
     * @author 노수연
     */
    Void deleteMember(Long memberNo);

}
