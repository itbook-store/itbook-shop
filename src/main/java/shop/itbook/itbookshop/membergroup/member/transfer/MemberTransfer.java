package shop.itbook.itbookshop.membergroup.member.transfer;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.membership.transfer.MembershipTransfer;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;

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
     * 수장하는 로직이 실행될 때 Dto를 멤버 엔티티로 변경하는 메서드입니다.
     *
     * @param memberUpdateRequestDto entity로 변경하기위한 정보를 담은 dto입니다.
     * @return 멤버 엔티티를 만들어서 반환합니다.
     * @author 노수연
     */
    public static Member dtoToEntityInUpdate(MemberUpdateRequestDto memberUpdateRequestDto) {
        return Member.builder().nickname(memberUpdateRequestDto.getNickname())
            .name(memberUpdateRequestDto.getName())
            .password(memberUpdateRequestDto.getPassword()).phoneNumber(
                memberUpdateRequestDto.getPhoneNumber()).email(memberUpdateRequestDto.getEmail())
            .build();
    }

    public static Member dtoToEntity(MemberRequestDto memberRequestDto) {
        return Member.builder().memberId(memberRequestDto.getMemberId()).nickname(
                memberRequestDto.getNickname()).name(memberRequestDto.getName())
            .isMan(memberRequestDto.getIsMan()).birth(memberRequestDto.getBirth()).password(
                memberRequestDto.getPassword()).phoneNumber(memberRequestDto.getPhoneNumber())
            .email(
                memberRequestDto.getEmail()).memberCreatedAt(LocalDateTime.now())
            .build();
    }

    /**
     * 멤버 엔티티를 dto로 변경하는 메서드입니다.
     *
     * @param member dto로 변경하기 위한 엔티티 클래스입니다.
     * @return 멤버 dto로 만들어서 반환합니다.
     * @author 노수연
     */
    public static MemberResponseProjectionDto entityToDto(Member member) {
        return MemberResponseProjectionDto.builder()
            .membershipGrade(
                MembershipTransfer.entityToDto(member.getMembership()).getMembershipGrade())
            .memberStatusName(
                MemberStatusTransfer.entityToDto(member.getMemberStatus()).getMemberStatusName())
            .memberId(
                member.getMemberId()).nickname(member.getNickname()).name(member.getName())
            .isMan(member.getIsMan()).birth(member.getBirth()).phoneNumber(member.getPhoneNumber())
            .email(member.getEmail())
            .memberCreatedAt(member.getMemberCreatedAt()).build();
    }
}
