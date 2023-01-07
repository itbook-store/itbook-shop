package shop.itbook.itbookshop.membergroup.member.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.member.domain.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.domain.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.domain.MemberUpdateRequestDto;

/**
 * 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberService {

    MemberResponseDto getMember(Long memberNo);

    List<MemberResponseDto> getMembers();

    Long saveMember(MemberSaveRequestDto requestDto);

    Boolean updateMember(Long memberNo, MemberUpdateRequestDto requestDto);

    void deleteMember(Long memberNo);

}
