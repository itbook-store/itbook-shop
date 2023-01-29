package shop.itbook.itbookshop.membergroup.member.transfer;

import java.time.LocalDateTime;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
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
     * 서비스 클래스에서 멤버를 저장하는 메서드가 실행될 때의 파라미터 DTO를 엔티티로 변환하는 메서드입니다.
     *
     * @param memberRequestDto 엔티티로 변경하기 위한 DTO 클래스입니다.
     * @return 멤버 엔티티를 반환합니다.
     * @author 노수연
     */
    public static Member dtoToEntity(MemberRequestDto memberRequestDto) {
        return Member.builder().memberId(memberRequestDto.getMemberId()).nickname(
                memberRequestDto.getNickname()).name(memberRequestDto.getName())
            .isMan(memberRequestDto.getIsMan()).birth(memberRequestDto.getBirth()).password(
                memberRequestDto.getPassword()).phoneNumber(memberRequestDto.getPhoneNumber())
            .email(
                memberRequestDto.getEmail()).memberCreatedAt(LocalDateTime.now())
            .isSocial(memberRequestDto.getIsSocial())
            .build();
    }

    /**
     * 멤버 엔티티를 dto로 변경하는 메서드입니다.
     *
     * @param member dto로 변경하기 위한 엔티티 클래스입니다.
     * @return 멤버 dto로 만들어서 반환합니다.
     * @author 노수연
     */
    public static MemberExceptPwdResponseDto entityToDto(Member member) {
        return MemberExceptPwdResponseDto.builder()
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
