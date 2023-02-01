package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;

@WebMvcTest(MemberAdminController.class)
class MemberAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    MemberAdminController memberAdminController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
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
    MemberRoleService memberRoleService;

    Membership membership;
    MemberStatus normalMemberStatus;
    Member member1;
    Member member2;

    @BeforeEach
    void setup() {
        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        member1 = MemberDummy.getMember1();
        member1.setMembership(membership);
        member1.setMemberStatus(normalMemberStatus);

        member2 = MemberDummy.getMember2();
        member2.setMembership(membership);
        member2.setMemberStatus(normalMemberStatus);
    }

    @Test
    @DisplayName("멤버 개별 조회 테스트")
    void memberDetails() throws Exception {

        given(memberAdminService.findMember(any())).willReturn(MemberTransfer.entityToDto(member1));

        mvc.perform(get("/api/admin/members/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("전체 멤버 조회 테스트")
    void memberList() throws Exception {

        given(memberAdminService.findMemberList()).willReturn(List.of(
            MemberTransfer.entityToDto(member1),
            MemberTransfer.entityToDto(member2)
        ));

        mvc.perform(get("/api/admin/members").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 수정 테스트")
    void memberUpdate() throws Exception {

        MemberStatusUpdateAdminRequestDto memberStatusUpdateAdminRequestDto =
            new MemberStatusUpdateAdminRequestDto();
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "차단회원");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "악성 민원");

        mvc.perform(put("/api/admin/members/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberStatusUpdateAdminRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}