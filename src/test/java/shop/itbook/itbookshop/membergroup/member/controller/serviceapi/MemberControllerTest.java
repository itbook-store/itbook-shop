package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    MemberService memberService;

    @MockBean
    MemberDestinationService memberDestinationService;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    PointHistoryCommonService pointHistoryService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void memberAdd() throws Exception {
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        ReflectionTestUtils.setField(memberRequestDto, "membershipName", "일반");
        ReflectionTestUtils.setField(memberRequestDto, "memberStatusName", "정상회원");
        ReflectionTestUtils.setField(memberRequestDto, "memberId", "user1000");
        ReflectionTestUtils.setField(memberRequestDto, "nickname", "감자");
        ReflectionTestUtils.setField(memberRequestDto, "name", "신짱구");
        ReflectionTestUtils.setField(memberRequestDto, "isMan", true);
        ReflectionTestUtils.setField(memberRequestDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(memberRequestDto, "password", "Abcd@1234");
        ReflectionTestUtils.setField(memberRequestDto, "phoneNumber", "01099949999");
        ReflectionTestUtils.setField(memberRequestDto, "email", "user1000@test.com");
        ReflectionTestUtils.setField(memberRequestDto, "isSocial", false);

        Long testNo = 1L;

        given(memberService.addMember(any(MemberRequestDto.class))).willReturn(testNo);

        mvc.perform(post("/api/members/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.memberNo", equalTo(1)));
    }

    @Test
    void memberDetails() throws Exception {

        MemberResponseDto memberResponseDto =
            new MemberResponseDto(1L, "user1000", "white", "정상회원", "딸기", "유저1000", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
                LocalDateTime.now(), false);

        given(memberService.findMember(any()))
            .willReturn(memberResponseDto);

        mvc.perform(get("/api/members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.memberNo", equalTo(1)))
            .andExpect(jsonPath("$.result.memberId", equalTo(memberResponseDto.getMemberId())))
            .andExpect(jsonPath("$.result.membershipGrade",
                equalTo(memberResponseDto.getMembershipGrade())))
            .andExpect(jsonPath("$.result.memberStatusName",
                equalTo(memberResponseDto.getMemberStatusName())))
            .andExpect(jsonPath("$.result.nickname", equalTo(memberResponseDto.getNickname())))
            .andExpect(jsonPath("$.result.name", equalTo(memberResponseDto.getName())))
            .andExpect(jsonPath("$.result.isMan", equalTo(memberResponseDto.getIsMan())));
    }

    @Test
    void memberModify() throws Exception {

        MemberUpdateRequestDto memberUpdateRequestDto = new MemberUpdateRequestDto();

        ReflectionTestUtils.setField(memberUpdateRequestDto, "nickname", "바나나");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "name", "신짱구");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "email", "banana@test.com");

        mvc.perform(put("/api/members/1/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberUpdateRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void memberDelete() throws Exception {
        MemberStatusUpdateAdminRequestDto memberStatusUpdateAdminRequestDto =
            new MemberStatusUpdateAdminRequestDto();

        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "탈퇴회원");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "탈퇴처리한 회원");

        mvc.perform(put("/api/members/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberStatusUpdateAdminRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void authLogin() {
    }

    @Test
    void memberIdDuplicateCheck() throws Exception {

        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);

        given(memberService.checkMemberIdDuplicate("user1000")).willReturn(
            memberBooleanResponseDto);

        mvc.perform(get("/api/members/sign-up-check/memberId/user1000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isExists", equalTo(true)));

    }

    @Test
    void nicknameDuplicateCheck() throws Exception {

        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);

        given(memberService.checkNickNameDuplicate("유저1000")).willReturn(memberBooleanResponseDto);

        mvc.perform(get("/api/members/sign-up-check/nickname/유저1000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isExists", equalTo(true)));
    }

    @Test
    void emailDuplicateCheck() throws Exception {

        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);

        given(memberService.checkEmailDuplicate("user1000@test.com")).willReturn(
            memberBooleanResponseDto);

        mvc.perform(get("/api/members/sign-up-check/email/user1000@test.com")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isExists", equalTo(true)));
    }

    @Test
    void phoneNumberCheck() throws Exception {

        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);

        given(memberService.checkPhoneNumberDuplicate("010-0000-0000")).willReturn(
            memberBooleanResponseDto);

        mvc.perform(get("/api/members/sign-up-check/phoneNumber/010-0000-0000")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.result.isExists", equalTo(true)));
    }
}