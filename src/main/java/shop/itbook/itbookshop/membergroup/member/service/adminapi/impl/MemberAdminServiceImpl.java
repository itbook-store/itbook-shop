package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;
import shop.itbook.itbookshop.membergroup.memberstatushistory.entity.MemberStatusHistory;
import shop.itbook.itbookshop.membergroup.memberstatushistory.repository.MemberStatusHistoryRepository;

/**
 * 멤버 서비스 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;
    private final MemberStatusHistoryRepository memberStatusHistoryRepository;
    private final MemberStatusAdminService memberStatusAdminService;

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberExceptPwdResponseDto findMember(String memberId) {

        return memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MemberExceptPwdResponseDto> findMemberList() {

        return memberRepository.findMemberList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyMember(String memberId, MemberStatusUpdateAdminRequestDto requestDto) {

        Member member = memberRepository.findByMemberIdReceiveMember(memberId)
            .orElseThrow(MemberNotFoundException::new);

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(requestDto.getMemberStatusName()));

        member.setMemberStatus(memberStatus);

        MemberStatusHistory memberStatusHistory =
            MemberStatusHistory.builder().member(member).memberStatus(memberStatus)
                .statusChangedReason(requestDto.getStatusChangedReason())
                .memberStatusHistoryCreatedAt(
                    LocalDateTime.now()).build();

        memberStatusHistoryRepository.save(memberStatusHistory);
    }
}
