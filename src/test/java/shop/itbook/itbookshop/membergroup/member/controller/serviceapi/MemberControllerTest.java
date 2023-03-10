package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;

/**
 * @author ?????????
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
    @MockBean
    private MemberDestinationRepository memberDestinationRepository;

    @Test
    void memberAdd() throws Exception {
        MemberRequestDto memberRequestDto = new MemberRequestDto();
        ReflectionTestUtils.setField(memberRequestDto, "membershipName", "??????");
        ReflectionTestUtils.setField(memberRequestDto, "memberStatusName", "????????????");
        ReflectionTestUtils.setField(memberRequestDto, "memberId", "user1000");
        ReflectionTestUtils.setField(memberRequestDto, "nickname", "??????");
        ReflectionTestUtils.setField(memberRequestDto, "name", "?????????");
        ReflectionTestUtils.setField(memberRequestDto, "isMan", true);
        ReflectionTestUtils.setField(memberRequestDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(memberRequestDto, "password", "Abcd@1234");
        ReflectionTestUtils.setField(memberRequestDto, "phoneNumber", "01099949999");
        ReflectionTestUtils.setField(memberRequestDto, "email", "user1000@test.com");
        ReflectionTestUtils.setField(memberRequestDto, "isSocial", false);
        ReflectionTestUtils.setField(memberRequestDto, "isWriter", false);

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
            new MemberResponseDto(1L, "user1000", "white", "????????????", "??????", "??????1000", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
                LocalDateTime.now(), false, false);

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
    void memberDetailsByMemberId() throws Exception {
        MemberResponseDto memberResponseDto =
            new MemberResponseDto(1L, "user1000", "white", "????????????", "??????", "??????1000", true,
                LocalDateTime.of(2000, 1, 1, 0, 0, 0), "1234", "010-0000-0000", "user1000@test.com",
                LocalDateTime.now(), false, false);

        given(memberService.findMemberByMemberId(any()))
            .willReturn(memberResponseDto);

        mvc.perform(get("/api/members/memberId/1")
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

        ReflectionTestUtils.setField(memberUpdateRequestDto, "nickname", "?????????");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "name", "?????????");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "password", "Abcd@1234");
        ReflectionTestUtils.setField(memberUpdateRequestDto, "phoneNumber", "01099999999");
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

        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "memberStatusName", "????????????");
        ReflectionTestUtils.setField(memberStatusUpdateAdminRequestDto, "statusChangedReason",
            "??????????????? ??????");

        mvc.perform(put("/api/members/1/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberStatusUpdateAdminRequestDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void OauthRegister() throws Exception {
        MemberSocialRequestDto memberSocialRequestDto = new MemberSocialRequestDto();

        ReflectionTestUtils.setField(memberSocialRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(memberSocialRequestDto, "memberId", "user@gmail.com");
        ReflectionTestUtils.setField(memberSocialRequestDto, "nickname", "??????5");
        ReflectionTestUtils.setField(memberSocialRequestDto, "name", "??????");
        ReflectionTestUtils.setField(memberSocialRequestDto, "isMan", false);
        ReflectionTestUtils.setField(memberSocialRequestDto, "birth",
            "20000504000000");
        ReflectionTestUtils.setField(memberSocialRequestDto, "phoneNumber", "01022933823");
        ReflectionTestUtils.setField(memberSocialRequestDto, "email", "user@gmail.com");
        ReflectionTestUtils.setField(memberSocialRequestDto, "isSocial", true);
        ReflectionTestUtils.setField(memberSocialRequestDto, "isWriter", false);

        mvc.perform(put("/api/members/sign-up/social")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberSocialRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void OauthLoginExistsEmail() throws Exception {

        SuccessfulResponseDto successfulResponseDto = new SuccessfulResponseDto();

        given(memberRepository.existsByEmailAndIsSocial(any())).willReturn(false);

        successfulResponseDto.setIsSuccessful(
            memberService.checkMemberOauthEmailExists("user@gmail.com"));

        mvc.perform(post("/api/members/oauth/login/find")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(successfulResponseDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void OauthLoginNotExistsEmail() throws Exception {

        SuccessfulResponseDto successfulResponseDto = new SuccessfulResponseDto();

        given(memberRepository.existsByEmailAndIsSocial(any())).willReturn(true);

        given(memberService.socialMemberAdd(any(), anyString())).willReturn(1L);

        successfulResponseDto.setIsSuccessful(
            memberService.checkMemberOauthEmailExists("user@gmail.com"));

        mvc.perform(post("/api/members/oauth/login/find")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(successfulResponseDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
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

        given(memberService.checkNickNameDuplicate("??????1000")).willReturn(memberBooleanResponseDto);

        mvc.perform(get("/api/members/sign-up-check/nickname/??????1000")
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

    @DisplayName("?????? ????????? ???????????? ?????????")
    @Test
    void authLogin() throws Exception {
        // given
        String memberId = "test";

        given(memberService.findMemberAuthInfo(memberId)).willReturn(any());

        // when, then
        mvc.perform(get("/api/members?memberId=" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.header.resultMessage", equalTo("?????? ????????? ??????????????? ?????????????????????.")));
    }

    @Test
    void nameCheck() throws Exception {
        MemberBooleanResponseDto memberBooleanResponseDto = new MemberBooleanResponseDto();
        ReflectionTestUtils.setField(memberBooleanResponseDto, "isExists", true);

        given(memberRepository.existsByNameAndFindNameWithMemberId(any(), anyString())).willReturn(
            true);

        given(memberService.checkNameDuplicate("user1", "??????")).willReturn(memberBooleanResponseDto);

        mvc.perform(get("/api/members/register-check/memberId/1/name/??????")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void memberDestinationList() throws Exception {

        MemberDestinationResponseDto memberDestinationResponseDto =
            new MemberDestinationResponseDto();
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientDestinationNo", 1L);
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientName", "??????");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientPhoneNumber",
            "01029292811");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "postcode", 62222);
        ReflectionTestUtils.setField(memberDestinationResponseDto, "roadNameAddress", "?????? ?????????");
        ReflectionTestUtils.setField(memberDestinationResponseDto, "recipientAddressDetails", "1???");

        given(memberDestinationRepository.findAllByMember_MemberNoOrderByRecipientDestinationNoDesc(
            anyLong())).willReturn(
            List.of(new MemberDestination()));

        given(memberDestinationService.findMemberDestinationResponseDtoByMemberNo(1L)).willReturn(
            List.of(memberDestinationResponseDto));

        mvc.perform(get("/api/members/1/member-destinations")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }


}