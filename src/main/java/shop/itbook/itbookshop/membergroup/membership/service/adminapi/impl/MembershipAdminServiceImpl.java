package shop.itbook.itbookshop.membergroup.membership.service.adminapi.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.exception.MembershipNotFoundException;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.adminapi.MembershipAdminService;
import shop.itbook.itbookshop.membergroup.membership.transfer.MembershipTransfer;

/**
 * 관리자 회원등급 기능과 관련된 비지니스 로직을 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipAdminServiceImpl implements MembershipAdminService {

    private final MembershipRepository membershipRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer addMembership(MembershipRequestDto membershipRequestDTO) {

        Membership membership = MembershipTransfer.dtoToEntity(membershipRequestDTO);
        return membershipRepository.save(membership).getMemberNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeMembership(Integer membershipNo) {

        Membership membership = membershipRepository.findById(membershipNo)
            .orElseThrow(MembershipNotFoundException::new);

        membershipRepository.deleteById(membershipNo);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyMembership(Integer membershipNo,
                                 MembershipModifyRequestDto membershipModifyRequestDto) {
        Membership membership = membershipRepository.findById(membershipNo)
            .orElseThrow(MembershipNotFoundException::new);

        membership.updateMembershipInfo(
            membershipModifyRequestDto.getMembershipGrade(),
            membershipModifyRequestDto.getMembershipStandardAmount(),
            membershipModifyRequestDto.getMembershipPoint()
        );

    }
}
