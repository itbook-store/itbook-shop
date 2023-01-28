package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
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
import shop.itbook.itbookshop.membergroup.memberstatushistory.entity.MemberStatusHistory;
import shop.itbook.itbookshop.membergroup.memberstatushistory.repository.MemberStatusHistoryRepository;

/**
 * 서비스 API 멤버 서비스 인터페이스를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final MemberStatusHistoryRepository memberStatusHistoryRepository;

    private final MemberStatusAdminService memberStatusAdminService;

    private final MembershipAdminService membershipAdminService;

    private final MemberRoleService memberRoleService;

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseDto findMember(String memberId) {
        return memberRepository.findByMemberIdAllInfo(memberId)
            .orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addMember(MemberRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntity(requestDto);

        Membership membership = membershipAdminService.findMembership(requestDto.getMembershipNo());

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatusWithMemberStatusNo(
                requestDto.getMemberStatusNo()));

        member.setMembership(membership);
        member.setMemberStatus(memberStatus);

        return memberRepository.save(member).getMemberNo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyMember(String memberId, MemberUpdateRequestDto requestDto) {

        Member member = memberRepository.findByMemberIdReceiveMember(memberId)
            .orElseThrow(MemberNotFoundException::new);

        member.setNickname(requestDto.getNickname());
        member.setName(requestDto.getName());
        member.setPassword(requestDto.getPassword());
        member.setPhoneNumber(requestDto.getPhoneNumber());
        member.setEmail(requestDto.getEmail());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void withDrawMember(String memberId, MemberStatusUpdateAdminRequestDto requestDto) {

        Member member = memberRepository.findByMemberIdReceiveMember(memberId)
            .orElseThrow(MemberNotFoundException::new);

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(requestDto.getMemberStatusName()));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mmss");

        UUID uuid = UUID.randomUUID();
        
        String replace = Integer.toString(ByteBuffer.wrap(uuid.toString().getBytes()).getInt(), 9);
        replace = now.format(formatter) + replace;

        member.setMemberStatus(memberStatus);
        member.setNickname(replace);
        member.setName("탈퇴한 사용자");
        member.setBirth(LocalDateTime.of(1900, 1, 1, 0, 0, 0));
        member.setPassword(replace);
        member.setPhoneNumber(replace);
        member.setEmail(replace);
        member.setMemberCreatedAt(LocalDateTime.of(1900, 1, 1, 0, 0, 0));

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

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBooleanResponseDto checkMemberIdDuplicate(String memberId) {
        return new MemberBooleanResponseDto(memberRepository.existsByMemberId(memberId));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBooleanResponseDto checkNickNameDuplicate(String nickname) {
        return new MemberBooleanResponseDto(memberRepository.existsByNickname(nickname));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBooleanResponseDto checkEmailDuplicate(String email) {
        return new MemberBooleanResponseDto(memberRepository.existsByEmail(email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBooleanResponseDto checkPhoneNumberDuplicate(String phoneNumber) {
        return new MemberBooleanResponseDto(memberRepository.existsByPhoneNumber(phoneNumber));
    }
}
