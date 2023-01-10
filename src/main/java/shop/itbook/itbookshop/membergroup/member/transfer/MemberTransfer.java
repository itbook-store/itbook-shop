package shop.itbook.itbookshop.membergroup.member.transfer;

import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
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
     * 저장하는 로직이 실행될 때 Dto를 멤버 엔티티로 변경하는 메서드입니다.
     *
     * @param memberSaveRequestDto entity로 변경하기위한 정보를 담은 dto입니다.
     * @return 멤버 엔티티를 만들어서 반환합니다.
     * @author 노수연
     */
    public static Member dtoToEntityInSave(MemberSaveRequestDto memberSaveRequestDto) {
        return Member.builder().id(memberSaveRequestDto.getId()).nickname(
                memberSaveRequestDto.getNickname()).name(memberSaveRequestDto.getName()).isMan(
                memberSaveRequestDto.getIsMan()).birth(memberSaveRequestDto.getBirth())
            .password(memberSaveRequestDto.getPassword()).phoneNumber(
                memberSaveRequestDto.getPhoneNumber()).email(memberSaveRequestDto.getEmail())
            .build();
    }

    /**
     * 수장하는 로직이 실행될 때 Dto를 멤버 엔티티로 변경하는 메서드입니다.
     *
     * @param memberUpdateRequestDto entity로 변경하기위한 정보를 담은 dto입니다.
     * @return 멤버 엔티티를 만들어서 반환합니다.
     * @author 노수연
     */
    public static Member dtoToEntityInUpdate(MemberUpdateRequestDto memberUpdateRequestDto) {
        return Member.builder().nickname(memberUpdateRequestDto.getNickname())
            .name(memberUpdateRequestDto.getName()).birth(memberUpdateRequestDto.getBirth())
            .password(memberUpdateRequestDto.getPassword()).phoneNumber(
                memberUpdateRequestDto.getPhoneNumber()).email(memberUpdateRequestDto.getEmail())
            .build();
    }
}
