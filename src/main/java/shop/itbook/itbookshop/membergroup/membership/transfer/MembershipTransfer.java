package shop.itbook.itbookshop.membergroup.membership.transfer;

import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDTO;
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
     * @author gwanii *
     */
    public static Membership dtoToEntity(MembershipRequestDTO membershipRequestDTO) {
        return new Membership().builder()
            .membershipGrade(membershipRequestDTO.getMembershipGrade())
            .membershipStandardAmount(membershipRequestDTO.getMembershipStandardAmount())
            .membershipPoint(membershipRequestDTO.getMembershipPoint())
            .build();
    }


}
