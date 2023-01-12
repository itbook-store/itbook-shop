package shop.itbook.itbookshop.membergroup.membership.transfer;

import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * 회원 등급에 대한 DTO 와 Entity 사이의 상호 변환을 위한 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public class MembershipTransfer {

    private MembershipTransfer() {

    }


    /**
     * 회원 등급에 대한 DTO를 Entity 객체로 변환하는 메서드 입니다.
     *
     * @param membershipRequestDTO the membership request dto
     * @return the membership
     * @author 강명관 *
     */
    public static Membership dtoToEntity(MembershipRequestDto membershipRequestDTO) {
        return new Membership().builder()
            .membershipGrade(membershipRequestDTO.getMembershipGrade())
            .membershipStandardAmount(membershipRequestDTO.getMembershipStandardAmount())
            .membershipPoint(membershipRequestDTO.getMembershipPoint())
            .build();
    }

    /*public static Membership ResponseDtoToEntity(MembershipResponseDto membershipResponseDto) {
        return new Membership().builder()
            .membershipGrade().build();
    }*/

    /**
     * 멤버십 엔티티를 dto로 변경하는 메서드입니다.
     *
     * @param membership dto로 변경하기 위한 엔티티 클래스입니다.
     * @return 멤버십 dto를 만들어서 반환합니다.
     * @author 노수연
     */
    public static MembershipResponseDto entityToDto(Membership membership) {
        return MembershipResponseDto.builder().membershipNo(membership.getMembershipNo())
            .membershipGrade(
                membership.getMembershipGrade())
            .membershipStandardAmount(membership.getMembershipStandardAmount()).membershipPoint(
                membership.getMembershipPoint()).build();
    }


}
