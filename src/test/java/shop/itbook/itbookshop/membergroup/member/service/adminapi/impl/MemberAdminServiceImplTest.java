package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;
import shop.itbook.itbookshop.membergroup.memberstatushistory.repository.MemberStatusHistoryRepository;

@ExtendWith(SpringExtension.class)
@Import(MemberAdminServiceImpl.class)
class MemberAdminServiceImplTest {

    @Autowired
    MemberAdminService memberAdminService;

    @MockBean
    MemberStatusAdminService memberStatusAdminService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MemberStatusHistoryRepository memberStatusHistoryRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    void findMember() {

        //given
        MemberExceptPwdResponseDto memberDto =
            new MemberExceptPwdResponseDto(1L, "user1", "white",
                MemberStatusEnum.NORMAL.getMemberStatus(), "유저1", "홍길동", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                "010-0000-0000", "user1@test.com", LocalDateTime.now(), false, false);

        given(memberRepository.findByMemberNo(1L)).willReturn(Optional.of(memberDto));

        MemberExceptPwdResponseDto testMember = memberAdminService.findMember(1L);

        assertThat(testMember.getMemberId()).isEqualTo("user1");
    }

    @Test
    void findMemberList() {
        MemberExceptPwdResponseDto member1 =
            new MemberExceptPwdResponseDto(1L, "user1", "white",
                MemberStatusEnum.NORMAL.getMemberStatus(), "유저1", "홍길동", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                "010-0000-0000", "user1@test.com", LocalDateTime.now(), false, false);

        MemberExceptPwdResponseDto member2 =
            new MemberExceptPwdResponseDto(2L, "user2", "white",
                MemberStatusEnum.NORMAL.getMemberStatus(), "유저2", "유저2", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                "010-1000-0000", "user2@test.com", LocalDateTime.now(), false, false);

        given(memberRepository.findMemberList(any())).willReturn(
            new PageImpl<>(List.of(member1, member2)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<MemberExceptPwdResponseDto> page = memberAdminService.findMemberList(pageRequest);

        // when
        List<MemberExceptPwdResponseDto> memberList = page.getContent();

        // then
        assertThat(memberList.get(0).getMemberNo()).isEqualTo(1L);
        assertThat(memberList.get(1).getMemberNo()).isEqualTo(2L);

    }

    @Test
    void updateMember() {

        MemberStatusUpdateAdminRequestDto memberStatusUpdateAdminRequestDto =
            new MemberStatusUpdateAdminRequestDto();

        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "차단회원");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "악성 민원");

        Member member = mock(Member.class);
        given(memberRepository.findByMemberNoReceiveMember(anyLong())).willReturn(
            Optional.of(member));

        MemberStatusResponseDto memberStatusDto =
            MemberStatusResponseDto.builder().memberStatusName("차단회원").memberStatusNo(1).build();
        given(memberStatusAdminService.findMemberStatus(anyString())).willReturn(memberStatusDto);

        memberAdminService.modifyMemberStatusInfo(1L, memberStatusUpdateAdminRequestDto);

        verify(member).setMemberStatus(any());
    }

}