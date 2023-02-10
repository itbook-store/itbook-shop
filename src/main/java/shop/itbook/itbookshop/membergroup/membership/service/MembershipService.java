package shop.itbook.itbookshop.membergroup.membership.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * 관리자가 회원 등급에 대해 처리하는 비지니스 로직을 담당하는 클래스의 인터페이스 입니다.
 *
 * @author 강명관
 * @author 노수연
 * @since 1.0
 */
public interface MembershipService {

    /**
     * 회원등급을 추가하는 메서드 입니다.
     *
     * @param membershipRequestDto the membership request dto
     * @return the integer
     * @author 강명관 *
     */
    Integer addMembership(MembershipRequestDto membershipRequestDto);

    /**
     * 회원등급을 삭제하는 메서드 입니다.
     *
     * @param membershipNo the membership no
     * @author 강명관 *
     */
    void removeMembership(Integer membershipNo);


    /**
     * 회원등급을 수정하는 메서드 입니다.
     *
     * @param membershipNo               the membership no
     * @param membershipModifyRequestDto the membership modify request dto
     * @author 강명관 *
     */
    void modifyMembership(Integer membershipNo,
                          MembershipModifyRequestDto membershipModifyRequestDto);

    MembershipResponseDto findMembership(Integer membershipNo);

    Membership findMembershipByMembershipGrade(String membershipGrade);

    List<MembershipResponseDto> findMembershipList();

}
