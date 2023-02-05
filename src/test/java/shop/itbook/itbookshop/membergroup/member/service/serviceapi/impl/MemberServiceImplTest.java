package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.membershiphistory.repository.MembershipHistoryRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatushistory.repository.MemberStatusHistoryRepository;
import shop.itbook.itbookshop.role.service.RoleService;

/**
 * @author 노수연
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(MemberServiceImpl.class)
class MemberServiceImplTest {

    @Autowired
    MemberService memberService;

    @MockBean
    MemberStatusAdminService memberStatusAdminService;

    @MockBean
    MembershipService membershipService;

    @MockBean
    MemberRoleService memberRoleService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MemberStatusHistoryRepository memberStatusHistoryRepository;

    @MockBean
    MembershipHistoryRepository membershipHistoryRepository;

    @MockBean
    RoleService roleService;

    MemberResponseDto member1;

    MemberRequestDto memberRequestDto;

    MemberStatusResponseDto memberStatusDto;

    Membership membership;

    MemberStatus normalMemberStatus;

    Member member;

    @BeforeEach
    void setup() {
        member1 = new MemberResponseDto(1L, "user1000", "white", "정상회원", "딸기", "유저1000", true,
            LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
            LocalDateTime.now(), false);

        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        memberRequestDto = new MemberRequestDto();
        ReflectionTestUtils.setField(memberRequestDto, "membershipName", "일반");
        ReflectionTestUtils.setField(memberRequestDto, "memberStatusName", "정상회원");
        ReflectionTestUtils.setField(memberRequestDto, "memberId", "user1000");
        ReflectionTestUtils.setField(memberRequestDto, "nickname", "감자");
        ReflectionTestUtils.setField(memberRequestDto, "name", "신짱구");
        ReflectionTestUtils.setField(memberRequestDto, "isMan", true);
        ReflectionTestUtils.setField(memberRequestDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(memberRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberRequestDto, "email", "user1000@test.com");

        memberStatusDto =
            MemberStatusResponseDto.builder().memberStatusName("정상회원").memberStatusNo(1).build();
        given(memberStatusAdminService.findMemberStatus("정상회원")).willReturn(
            memberStatusDto);
    }

    @Test
    void findMember() {
        given(memberRepository.findByMemberIdAllInfo("user1000")).willReturn(Optional.of(member1));

        MemberResponseDto testMember = memberService.findMember("user1000");

        assertThat(testMember.getMemberId()).isEqualTo("user1000");
    }

    @Test
    void addMember() {
        member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);

        given(memberRepository.save(any(Member.class))).willReturn(member);

        Long actual = memberService.addMember(memberRequestDto);

        assertThat(actual).isEqualTo(member.getMemberNo());
    }

    @Test
    void modifyMember() {

        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto();

        ReflectionTestUtils.setField(memberUpdateRequestDto, "nickname", "바나나");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "name", "신짱구");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "email", "banana@test.com");

        Member member = mock(Member.class);
        given(memberRepository.findByMemberIdReceiveMember(anyString())).willReturn(
            Optional.of(member));

        memberService.modifyMember("user1000", memberUpdateRequestDto);

    }

    @Test
    void withDrawMember() {
        MemberStatusUpdateAdminRequestDto memberStatusUpdateAdminRequestDto =
            new MemberStatusUpdateAdminRequestDto();

        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "탈퇴회원");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "탈퇴처리한 회원");

        Member member = mock(Member.class);
        given(memberRepository.findByMemberIdReceiveMember(anyString())).willReturn(
            Optional.of(member));

        MemberStatusResponseDto memberStatusDto =
            MemberStatusResponseDto.builder().memberStatusName("탈퇴회원").memberStatusNo(3).build();
        given(memberStatusAdminService.findMemberStatus(anyString())).willReturn(memberStatusDto);

        memberService.withDrawMember("user6", memberStatusUpdateAdminRequestDto);

        verify(member).setMemberStatus(any());
    }

    @Test
    void findMemberAuthInfo() {
    }

    @Test
    void checkMemberIdDuplicate() {
        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);
        given(memberRepository.existsByMemberId("user1000")).willReturn(true);

        assertThat(memberService.checkMemberIdDuplicate("user1000").getIsExists()).isEqualTo(true);
    }

    @Test
    void checkNickNameDuplicate() {
        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);
        given(memberRepository.existsByNickname("감자")).willReturn(true);

        assertThat(memberService.checkNickNameDuplicate("감자").getIsExists()).isEqualTo(true);
    }

    @Test
    void checkEmailDuplicate() {
        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);
        given(memberRepository.existsByEmail("user1000@test.com")).willReturn(true);

        assertThat(
            memberService.checkEmailDuplicate("user1000@test.com").getIsExists()).isEqualTo(
            true);
    }

    @Test
    void checkPhoneNumberDuplicate() {
        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);
        given(memberRepository.existsByPhoneNumber("010-9999-9999")).willReturn(true);

        assertThat(
            memberService.checkPhoneNumberDuplicate("010-9999-9999").getIsExists()).isEqualTo(
            true);
    }
}