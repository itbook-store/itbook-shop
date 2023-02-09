package shop.itbook.itbookshop.membergroup.membership.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.exception.MembershipNotFoundException;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.membership.transfer.MembershipTransfer;

/**
 * 관리자 회원등급 기능과 관련된 비지니스 로직을 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Integer addMembership(MembershipRequestDto membershipRequestDto) {

        Membership membership = MembershipTransfer.dtoToEntity(membershipRequestDto);
        return membershipRepository.save(membership).getMembershipNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void removeMembership(Integer membershipNo) {

        Membership membership = membershipRepository.findById(membershipNo)
            .orElseThrow(MembershipNotFoundException::new);

        membershipRepository.deleteById(membership.getMembershipNo());
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

    @Override
    public MembershipResponseDto findMembership(Integer membershipNo) {
        return MembershipTransfer.entityToDto(membershipRepository.findById(membershipNo)
            .orElseThrow(MembershipNotFoundException::new));
    }

    @Override
    public Membership findMembershipByMembershipGrade(String membershipGrade) {
        return membershipRepository.findByMembershipGrade(membershipGrade)
            .orElseThrow(MembershipNotFoundException::new);
    }

    @Override
    public List<MembershipResponseDto> findMembershipList() {
        return membershipRepository.findAllBy();
    }
}
