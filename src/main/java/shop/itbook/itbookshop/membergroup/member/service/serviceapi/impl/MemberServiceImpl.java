package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.service.adminapi.MembershipAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;

/**
 * @author 노수연
 * @since 1.0
 */

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberStatusAdminService memberStatusAdminService;

    private final MembershipAdminService membershipAdminService;

    private final MemberRoleService memberRoleService;

    @Override
    public MemberResponseProjectionDto findMember(String memberId) {
        return memberRepository.querydslFindByMemberId(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    @Override
    public Long addMember(MemberRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntity(requestDto);

        //TODO 2. membership 가져오기
        Membership membership = membershipAdminService.findMembership(requestDto.getMembershipNo());

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatusWithMemberStatusNo(
                requestDto.getMemberStatusNo()));

        member.setMembership(membership);
        member.setMemberStatus(memberStatus);


        return memberRepository.save(member).getMemberNo();
    }

    @Transactional
    @Override
    public void modifyMember(String memberId, MemberUpdateRequestDto requestDto) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthResponseDto findMemberAuthInfo(String memberId) {

        MemberAuthInfoResponseDto memberAuthInfoResponseDto =
            memberRepository.findAuthInfoByMemberId(memberId)
                .orElseThrow(MemberNotFoundException::new);

        List<String> roleList =
            memberRoleService.findMemberRoleWithMemberNo(memberAuthInfoResponseDto.getMemberNo())
                .stream()
                .map(MemberRoleResponseDto::getRole)
                .collect(Collectors.toList());

        return new MemberAuthResponseDto(
            memberAuthInfoResponseDto.getMemberNo(),
            memberAuthInfoResponseDto.getMemberId(),
            memberAuthInfoResponseDto.getPassword(),
            roleList
        );

    }
}
