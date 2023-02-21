package shop.itbook.itbookshop.membergroup.memberrole.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleAllResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.repository.MemberRoleRepository;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.role.dummy.RoleDummy;
import shop.itbook.itbookshop.role.dto.RoleResponseDto;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.repository.RoleRepository;
import shop.itbook.itbookshop.role.service.RoleService;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(MemberRoleController.class)
class MemberRoleControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberRoleService memberRoleService;

    @MockBean
    MemberService memberService;

    @MockBean
    MembershipService membershipService;

    @MockBean
    MemberStatusAdminService memberStatusAdminService;

    @MockBean
    RoleService roleService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    MemberRoleRepository memberRoleRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    Membership membership;
    MemberStatus normalMemberStatus;

    Member member;

    MemberResponseDto memberResponseDto;

    Role role;

    RoleResponseDto roleResponseDto;

    @MockBean
    private RoleRepository roleRepository;


    @BeforeEach
    void setUp() {
        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        normalMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus);

        member = MemberDummy.getMember1();
        member.setMembership(membership);
        member.setMemberStatus(normalMemberStatus);
        memberRepository.save(member);

        memberResponseDto =
            new MemberResponseDto(1L, "user1000", "white", "정상회원", "딸기", "유저1000", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
                LocalDateTime.now(), false, false);

        role = RoleDummy.getRole();
        roleRepository.save(role);
        roleResponseDto = new RoleResponseDto(1, "ADMIN");
    }

    @Test
    void memberRoleAdd() throws Exception {
        given(memberRepository.findByMemberNoAllInfo(1L)).willReturn(
            Optional.of(memberResponseDto));
        MemberResponseDto testMember = memberService.findMember(1L);


        given(roleRepository.findByRoleName(anyString())).willReturn(Optional.of(roleResponseDto));

        given(roleService.findRole("ADMIN")).willReturn(role);

        Role testRole = roleService.findRole("ADMIN");

        memberRoleService.addMemberRole(member, testRole);

        mvc.perform(post("/api/member-roles/1/USER/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void memberRoleRemove() throws Exception {
        memberRoleService.deleteMemberRole(1L, 1);

        mvc.perform(delete("/api/member-roles/1/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void memberRoleDetails() throws Exception {
        MemberRoleAllResponseDto memberRoleAllResponseDto = new MemberRoleAllResponseDto();
        ReflectionTestUtils.setField(memberRoleAllResponseDto, "roleNo", 1);
        ReflectionTestUtils.setField(memberRoleAllResponseDto, "role", "USER");

        given(memberRoleService.findMemberRoleAllInfoWithMemberNo(member.getMemberNo())).willReturn(
            List.of(memberRoleAllResponseDto));

        mvc.perform(get("/api/member-roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}