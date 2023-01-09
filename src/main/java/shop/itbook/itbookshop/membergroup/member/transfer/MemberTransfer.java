package shop.itbook.itbookshop.membergroup.member.transfer;

import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 멤버의 엔티티와 dto 간의 변환을 작성한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class MemberTransfer {

    private MemberTransfer() {
    }

    /**
     * Dto를 멤버 엔티티로 변경하는 메서드입니다.
     *
     * @param memberSaveRequestDto entity로 변경하기위한 정보를 담은 dto입니다.
     * @return 멤버 엔티티를 만들어서 반환합니다.
     * @author 노수연
     */
    public static Member dtoToEntity(MemberSaveRequestDto memberSaveRequestDto) {
        return Member.builder().id(memberSaveRequestDto.getId()).nickname(
                memberSaveRequestDto.getNickname()).name(memberSaveRequestDto.getName()).isMan(
                memberSaveRequestDto.isMan()).birth(memberSaveRequestDto.getBirth())
            .password(memberSaveRequestDto.getPassword()).phoneNumber(
                memberSaveRequestDto.getPhoneNumber()).email(memberSaveRequestDto.getEmail())
            .build();
    }
}
