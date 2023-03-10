package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthInfoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
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
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.service.GiftIncreaseDecreasePointHistoryService;
import shop.itbook.itbookshop.role.service.RoleService;

/**
 * @author ?????????
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

    @MockBean
    GiftIncreaseDecreasePointHistoryService giftIncreaseDecreasePointHistoryService;

    MemberResponseDto member1;

    MemberRequestDto memberRequestDto;

    MemberStatusResponseDto memberStatusDto;

    Membership membership;

    MemberStatus normalMemberStatus;

    Member member;

    @BeforeEach
    void setup() {
        member1 = new MemberResponseDto(1L, "user1000", "white", "????????????", "??????", "??????1000", true,
            LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
            LocalDateTime.now(), false, false);

        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        memberRequestDto = new MemberRequestDto();
        ReflectionTestUtils.setField(memberRequestDto, "membershipName", "??????");
        ReflectionTestUtils.setField(memberRequestDto, "memberStatusName", "????????????");
        ReflectionTestUtils.setField(memberRequestDto, "memberId", "user1000");
        ReflectionTestUtils.setField(memberRequestDto, "nickname", "??????");
        ReflectionTestUtils.setField(memberRequestDto, "name", "?????????");
        ReflectionTestUtils.setField(memberRequestDto, "isMan", true);
        ReflectionTestUtils.setField(memberRequestDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(memberRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberRequestDto, "email", "user1000@test.com");

        memberStatusDto =
            MemberStatusResponseDto.builder().memberStatusName("????????????").memberStatusNo(1).build();
        given(memberStatusAdminService.findMemberStatus("????????????")).willReturn(
            memberStatusDto);
    }

    @Test
    void findMemberByMemberNo() {
        member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);

        given(memberRepository.findById(1L)).willReturn(Optional.of(member));

        Member testMember = memberService.findMemberByMemberNo(1L);

        assertThat(testMember.getMemberId()).isEqualTo("user1000");
    }

    @Test
    void findMember() {
        given(memberRepository.findByMemberNoAllInfo(1L)).willReturn(Optional.of(member1));

        MemberResponseDto testMember = memberService.findMember(1L);

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

        ReflectionTestUtils.setField(memberUpdateRequestDto, "nickname", "?????????");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "name", "?????????");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "email", "banana@test.com");

        Member member = mock(Member.class);
        given(memberRepository.findByMemberNoReceiveMember(anyLong())).willReturn(
            Optional.of(member));

        memberService.modifyMember(1L, memberUpdateRequestDto);

    }

    @Test
    void withDrawMember() {
        MemberStatusUpdateAdminRequestDto memberStatusUpdateAdminRequestDto =
            new MemberStatusUpdateAdminRequestDto();

        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "????????????");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "??????????????? ??????");

        Member member = mock(Member.class);
        given(memberRepository.findByMemberNoReceiveMember(anyLong())).willReturn(
            Optional.of(member));

        MemberStatusResponseDto memberStatusDto =
            MemberStatusResponseDto.builder().memberStatusName("????????????").memberStatusNo(3).build();
        given(memberStatusAdminService.findMemberStatus(anyString())).willReturn(memberStatusDto);

        memberService.withDrawMember(1L, memberStatusUpdateAdminRequestDto);

        verify(member).setMemberStatus(any());
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
        given(memberRepository.existsByNickname("??????")).willReturn(true);

        assertThat(memberService.checkNickNameDuplicate("??????").getIsExists()).isEqualTo(true);
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

    @DisplayName("?????? ???????????? ?????? ?????? ?????? ????????? ?????????")
    @Test
    void findMemberAuthInfoTest() {

        MemberAuthInfoResponseDto memberAuthInfoResponseDto = new MemberAuthInfoResponseDto(
            member1.getMemberNo(),
            member1.getMemberId(),
            member1.getPassword()
        );

        List<MemberRoleResponseDto> roleList = Collections.emptyList();

        MemberAuthResponseDto memberAuthResponseDto = new MemberAuthResponseDto(
            member1.getMemberNo(),
            member1.getMemberId(),
            member1.getPassword(),
            Collections.emptyList()
        );

        // given
        given(memberRepository.findAuthInfoByMemberId(member1.getMemberId()))
            .willReturn(Optional.of(memberAuthInfoResponseDto));
        given(memberRoleService.findMemberRoleWithMemberNo(member1.getMemberNo()))
            .willReturn(roleList);

        // when
        MemberAuthResponseDto memberAuthInfo =
            memberService.findMemberAuthInfo(member1.getMemberId());

        // then
        assertThat(memberAuthInfo.getMemberNo()).isEqualTo(member1.getMemberNo());
    }
}