package shop.itbook.itbookshop.membergroup.member.service.adminapi;

import java.util.List;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;

/**
 * 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberAdminService {

    MemberResponseDto findMember(Long memberNo);

    List<MemberResponseDto> findMemberList();

    Long saveMember(MemberSaveRequestDto requestDto);

    Boolean updateMember(Long memberNo, MemberUpdateRequestDto requestDto);

    void deleteMember(Long memberNo);

}
