package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatusenum.MemberStatusEnum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAdminController.class)
class MemberAdminControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    private MemberAdminController memberAdminController;

    @MockBean
    MemberAdminService memberAdminService;

    @Autowired
    ObjectMapper objectMapper;

    Membership membership;
    MemberStatus memberStatus;
    Member member1;

    @BeforeEach
    void setup() {
        membership =
            Membership.builder().membershipGrade("white").membershipStandardAmount(100_000L)
                .membershipPoint(10_000L).build();

        memberStatus = MemberStatus.builder().memberStatusEnum(MemberStatusEnum.NORMAL).build();

        member1 =
            Member.builder().membership(membership).memberStatus(memberStatus).id("user1")
                .nickname("유저1").name("홍길동").isMan(true).birth(
                    LocalDateTime.of(2000, 1, 1, 0, 0, 0)).password("1234").phoneNumber("010-0000-0000")
                .email("user1@test1.com").memberCreatedAt(LocalDateTime.now()).build();
    }

    @Test
    @DisplayName("특정 멤버 조회 테스트")
    void memberDetails() throws Exception {

        given(memberAdminService.findMember(any())).willReturn(member1);

        mvc.perform(get("/api/admin/members/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("전체 멤버 조회 테스트")
    void memberList() {

    }

    @Test
    @DisplayName("멤버 저장 테스트")
    void memberSave() {

    }

    @Test
    @DisplayName("멤버 수정 테스트")
    void memberUpdate() {

    }

    @Test
    @DisplayName("멤버 삭제 테스트")
    void memberDelete() {
    }
}