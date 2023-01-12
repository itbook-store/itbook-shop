package shop.itbook.itbookshop.membergroup.member.service.serviceapi;

import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;

/**
 * 서비스 api 멤버 서비스 인터페이스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberServiceService {

    MemberResponseProjectionDto findMember(String id);

    Long addMember(MemberSaveRequestDto requestDto);

    void modifyMember(String id, MemberUpdateRequestDto requestDto);
}
