package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
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
    public Page<MemberExceptPwdResponseDto> findMemberList(Pageable pageable) {

        return memberRepository.findMemberList(pageable);

    }

    @Override
    public Page<MemberExceptPwdResponseDto> findNormalMemberList(Pageable pageable) {
        return memberRepository.findNormalMemberList(pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findBlockMemberList(Pageable pageable) {
        return memberRepository.findBlockMemberList(pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findWithdrawMemberList(Pageable pageable) {
        return memberRepository.findWithdrawMemberList(pageable);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<MemberExceptPwdResponseDto> findMemberListByMemberId(String memberId,
                                                                     String memberStatusName,
                                                                     Pageable pageable) {

        return memberRepository.findMemberListByMemberId(memberId, memberStatusName, pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findMemberListByNickname(String nickname,
                                                                     String memberStatusName,
                                                                     Pageable pageable) {

        return memberRepository.findMemberListByNickname(nickname, memberStatusName, pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findMemberListByName(String name,
                                                                 String memberStatusName,
                                                                 Pageable pageable) {

        return memberRepository.findMemberListByName(name, memberStatusName, pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findMemberListByPhoneNumber(String phoneNumber,
                                                                        String memberStatusName,
                                                                        Pageable pageable) {

        return memberRepository.findMemberListByPhoneNumber(phoneNumber, memberStatusName,
            pageable);
    }

    @Override
    public Page<MemberExceptPwdResponseDto> findMemberListBySearchWord(String searchWord,
                                                                       String memberStatusName,
                                                                       Pageable pageable) {

        return memberRepository.findMemberListBySearchWord(searchWord, memberStatusName, pageable);
    }

    @Override
    public MemberExceptPwdBlockResponseDto findBlockMember(String memberId) {

        return memberRepository.findBlockMemberByMemberId(memberId);
    }

    @Override
    public MemberCountResponseDto memberCount() {

        return memberRepository.MemberCountBy();
    }

    @Override
    public MemberCountResponseDto blockMemberCount() {

        return memberRepository.blockMemberCountBy();
    }

    @Override
    public MemberCountResponseDto withdrawMemberCount() {

        return memberRepository.withdrawMemberCountBy();
    }
}
