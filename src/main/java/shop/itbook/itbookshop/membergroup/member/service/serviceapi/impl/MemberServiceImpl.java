package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.coupongroup.couponissue.eventlistner.SignedUpEvent;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSocialRequestDto;
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
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.membershiphistory.entity.MembershipHistory;
import shop.itbook.itbookshop.membergroup.membershiphistory.repository.MembershipHistoryRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;
import shop.itbook.itbookshop.membergroup.memberstatushistory.entity.MemberStatusHistory;
import shop.itbook.itbookshop.membergroup.memberstatushistory.repository.MemberStatusHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.GiftIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.service.RoleService;

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

    private final MembershipHistoryRepository membershipHistoryRepository;

    private final MemberStatusAdminService memberStatusAdminService;

    private final MembershipService membershipService;

    private final MemberRoleService memberRoleService;

    private final RoleService roleService;

    private final GiftIncreaseDecreasePointHistoryService giftIncreaseDecreasePointHistoryService;

    private final ApplicationEventPublisher publisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public Member findMemberByMemberNo(Long memberNo) {
        return memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseDto findMember(Long memberNo) {

        return memberRepository.findByMemberNoAllInfo(memberNo)
            .orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberResponseDto findMemberByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId).orElseThrow(MemberNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addMember(MemberRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntity(requestDto);

        Membership membership =
            membershipService.findMembershipByMembershipGrade(requestDto.getMembershipName());

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(
                requestDto.getMemberStatusName()));

        member.setMembership(membership);
        member.setMemberStatus(memberStatus);

        Long memberNo = memberRepository.save(member).getMemberNo();

        MembershipHistory membershipHistory =
            MembershipHistory.builder().membership(membership).member(member).monthlyUsageAmount(0L)
                .membershipHistoryCreatedAt(LocalDateTime.now()).build();

        membershipHistoryRepository.save(membershipHistory);

        Role role = roleService.findRole("USER");

        memberRoleService.addMemberRole(member, role);

        publisher.publishEvent(new SignedUpEvent(this, member));

        return memberNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyMember(Long memberNo, MemberUpdateRequestDto requestDto) {

        Member member = memberRepository.findByMemberNoReceiveMember(memberNo)
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
    public void withDrawMember(Long memberNo, MemberStatusUpdateAdminRequestDto requestDto) {

        Member member = memberRepository.findByMemberNoReceiveMember(memberNo)
            .orElseThrow(MemberNotFoundException::new);

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(requestDto.getMemberStatusName()));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");

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

        if (member.getIsSocial()) {
            member.setMemberId(replace);
        }

        MemberStatusHistory memberStatusHistory =
            MemberStatusHistory.builder().member(member).memberStatus(memberStatus)
                .statusChangedReason(requestDto.getStatusChangedReason())
                .memberStatusHistoryCreatedAt(
                    LocalDateTime.now()).build();

        memberStatusHistoryRepository.save(memberStatusHistory);

    }

    @Override
    public Boolean checkMemberOauthEmailExists(String email) {

        return memberRepository.existsByEmailAndIsSocial(email);
    }

    @Override
    public Boolean checkMemberOauthInfoExists(String email) {

        return memberRepository.existsByMemberIdAndIsSocial(email);
    }

    @Override
    @Transactional
    public Long socialMemberAdd(String email, String encodedEmail) {

        Membership membership = membershipService.findMembershipByMembershipGrade("일반");

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus("정상회원"));

        Member member =
            Member.builder().membership(membership).memberStatus(memberStatus).memberId(email)
                .nickname(email).name(email).isMan(true).birth(LocalDateTime.now())
                .password(encodedEmail).phoneNumber(email).email(email)
                .memberCreatedAt(LocalDateTime.now()).isSocial(true).isWriter(false).build();

        Long memberNo = memberRepository.save(member).getMemberNo();

        MembershipHistory membershipHistory =
            MembershipHistory.builder().membership(membership).member(member).monthlyUsageAmount(0L)
                .membershipHistoryCreatedAt(LocalDateTime.now()).build();

        membershipHistoryRepository.save(membershipHistory);

        Role role = roleService.findRole("USER");

        memberRoleService.addMemberRole(member, role);

        return memberNo;
    }

    @Override
    @Transactional
    public Long modifySocialMember(MemberSocialRequestDto requestDto) {
        Member member = memberRepository.findByMemberNoReceiveMember(requestDto.getMemberNo())
            .orElseThrow(MemberNotFoundException::new);

        member.setNickname(requestDto.getNickname());
        member.setName(requestDto.getName());
        member.setPhoneNumber(requestDto.getPhoneNumber());
        member.setIsMan(requestDto.getIsMan());
        member.setBirth(LocalDate.parse(requestDto.getBirth(),
            DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay());

        return member.getMemberNo();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberBooleanResponseDto checkNameDuplicate(String memberId, String name) {

        return new MemberBooleanResponseDto(
            memberRepository.existsByNameAndFindNameWithMemberId(memberId, name));
    }

    @Override
    @Transactional
    public Long sendMemberToMemberGiftPoint(Long sendNoMemberNo, Long receiverMemberNo,
                                            Long point) {

        Member sender = findMemberByMemberNo(sendNoMemberNo);

        Member receiver = findMemberByMemberNo(receiverMemberNo);

        return giftIncreaseDecreasePointHistoryService.savePointHistoryAboutGiftDecreaseAndIncrease(
            sender, receiver, point).getPointHistoryNo();
    }
}
