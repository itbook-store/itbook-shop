package shop.itbook.itbookshop.membergroup.membership.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipNoResponseDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(MembershipController.class)
class MembershipControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MembershipService membershipService;

    @MockBean
    MemberRepository memberRepository;

    Membership membership;
    @MockBean
    private MembershipRepository membershipRepository;

    @Test
    void membershipAdd() throws Exception {
        MembershipNoResponseDto membershipNoResponseDto = new MembershipNoResponseDto(1);

        MembershipRequestDto membershipRequestDto = new MembershipRequestDto();
        ReflectionTestUtils.setField(membershipRequestDto, "membershipGrade", "white");
        ReflectionTestUtils.setField(membershipRequestDto, "membershipStandardAmount", 100_000L);
        ReflectionTestUtils.setField(membershipRequestDto, "membershipPoint", 10_000L);

        given(membershipService.addMembership(any(MembershipRequestDto.class))).willReturn(1);

        mvc.perform(post("/api/admin/membership")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(membershipRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void membershipRemove() throws Exception {

        membershipService.removeMembership(1);

        mvc.perform(delete("/api/admin/membership/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void membershipModify() throws Exception {
        MembershipModifyRequestDto membershipModifyRequestDto = new MembershipModifyRequestDto();
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipGrade", "silver");
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipStandardAmount",
            200_000L);
        ReflectionTestUtils.setField(membershipModifyRequestDto, "membershipPoint", 20_000L);

        membershipService.modifyMembership(1, membershipModifyRequestDto);

        mvc.perform(put("/api/admin/membership/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(membershipModifyRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void membershipDetails() throws Exception {
        membership = MembershipDummy.getMembership();
        membershipRepository.save(membership);

        MembershipResponseDto membershipResponseDto =
            new MembershipResponseDto(1, "white", 100_000L, 10_000L);

        given(membershipService.findMembershipByMembershipGrade(any())).willReturn(membership);

        mvc.perform(get("/api/admin/membership/white")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void membershipDetailsByMembershipNo() throws Exception {
        MembershipResponseDto membershipResponseDto =
            new MembershipResponseDto(1, "white", 100_000L, 10_000L);

        given(membershipService.findMembership(anyInt())).willReturn(membershipResponseDto);

        mvc.perform(get("/api/admin/membership/membershipNo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void membershipList() throws Exception {
        MembershipResponseDto membershipResponseDto =
            new MembershipResponseDto(1, "white", 100_000L, 10_000L);

        given(membershipService.findMembershipList()).willReturn(List.of(membershipResponseDto));

        mvc.perform(get("/api/admin/membership")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
}