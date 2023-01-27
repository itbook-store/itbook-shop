package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

@ExtendWith(SpringExtension.class)
@Import(MemberAdminServiceImpl.class)
class MemberAdminServiceImplTest {

    @Autowired
    private MemberAdminService memberAdminService;
    @MockBean
    private MemberStatusAdminService memberStatusAdminService;
    @MockBean
    MemberRepository memberRepository;
    @MockBean
    MembershipRepository membershipRepository;
    @MockBean
    MemberStatusRepository memberStatusRepository;


    Membership membership;
    MemberStatus memberStatus;

    @BeforeEach
    void setup() {
        membership =
            Membership.builder().membershipGrade("white").membershipStandardAmount(100_000L)
                .membershipPoint(10_000L).build();

        membershipRepository.save(membership);

        memberStatus = MemberStatus.builder().memberStatusEnum(MemberStatusEnum.NORMAL).build();

        memberStatusRepository.save(memberStatus);
    }

    @Test
    void findMember() {
        //given
        MemberExceptPwdResponseDto memberDto =
            new MemberExceptPwdResponseDto(1L, "user1", "white",
                MemberStatusEnum.NORMAL.getMemberStatus(), "유저1", "홍길동", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0),
                "010-0000-0000", "user1@test.com", LocalDateTime.now());

        Member member =
            Member.builder().membership(membership).memberStatus(memberStatus).memberId("user1")
                .nickname("유저1").name("홍길동").isMan(true).birth(
                    LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234").phoneNumber("010-0000-0000")
                .email("user1@test1.com").memberCreatedAt(LocalDateTime.now()).build();

        given(memberRepository.findByMemberId("user1")).willReturn(Optional.of(memberDto));

        MemberExceptPwdResponseDto testMember = memberAdminService.findMember("user1");

        assertThat(testMember.getMemberId()).isEqualTo("user1");
    }

    @Test
    void findMemberList() {
    }

    @Test
    void addMember() {
    }

    @Test
    void updateMember() {

    }

    @Test
    void deleteMember() {
    }
}