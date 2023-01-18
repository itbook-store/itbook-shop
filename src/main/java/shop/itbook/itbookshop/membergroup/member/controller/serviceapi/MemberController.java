package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberNoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;

/**
 * 사용자 권한을 가진 요청에 응답하는 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/service/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponseBody<MemberNoResponseDto>> memberAdd(
        @Valid @RequestBody MemberRequestDto requestDto) {

        MemberNoResponseDto memberNoResponseDto =
            new MemberNoResponseDto(memberService.addMember(requestDto));

        CommonResponseBody<MemberNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            memberNoResponseDto
        );

        System.out.println(">>>>>>>" + memberNoResponseDto.getMemberNo());

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberResponseProjectionDto>> memberDetails(
        @PathVariable("memberId") String memberId) {
        CommonResponseBody<MemberResponseProjectionDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberService.findMember(memberId)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Auth 로그인 시 회원에 대한 정보를 반환받는 API 입니다.
     *
     * @param memberId the member id
     * @return the response entity
     * @author 강명관
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<MemberAuthResponseDto>> authLogin(
        @RequestParam(value = "memberId") String memberId) {

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
            memberService.findMemberAuthInfo(memberId)
        ));
    }

    @GetMapping("/sign-up/memberId/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> checkMemberIdDuplicate(
        @PathVariable("memberId") String memberId) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true,
                HttpStatus.OK.value(), ""), memberService.checkMemberIdDuplicate(memberId)
        ));
    }

    @GetMapping("/sign-up/nickname/{nickname}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> checkNicknameDuplicate(
        @PathVariable("nickname") String nickname) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(), ""),
            memberService.checkNickNameDuplicate(nickname)
        ));
    }

    @GetMapping("/sign-up/email/{email}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> checkEmailDuplicate(
        @PathVariable("email") String email) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(), ""),
            memberService.checkEmailDuplicate(email)
        ));
    }

    @GetMapping("/sign-up/phoneNumber/{phoneNumber}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> checkPhoneNumber(
        @PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(), ""),
            memberService.checkPhoneNumberDuplicate(phoneNumber)
        ));
    }

}
