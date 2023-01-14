package shop.itbook.itbookshop.membergroup.member.service.serviceapi;

import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;

/**
 * 서비스 api 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberService {

    MemberResponseProjectionDto findMember(String memberId);

    Long addMember(MemberRequestDto requestDto);

    void modifyMember(String memberId, MemberUpdateRequestDto requestDto);


    /**
     * 인증에 대한 회원 정보를 반환받는 메서드 입니다.
     *
     * @param memberId 회원 아이디
     * @return 회원 넘버, 아이디, 비밀번호
     * @author 강명관
     */
    MemberAuthResponseDto findMemberAuthInfo(String memberId);
}
