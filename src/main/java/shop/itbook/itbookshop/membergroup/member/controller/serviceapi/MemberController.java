package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberOauthLoginRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberNoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;

/**
 * 사용자 권한을 가진 요청에 응답하는 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 프론트 서버에서 입력된 정보를 dto로 받아와 서비스 클래스로 넘겨 테이블에 멤버를 추가하고
     * 생성된 멤버 번호를 dto에 담아 반환하는 메서드입니다.
     *
     * @param requestDto 멤버번호와 멤버생성날짜 필드가 제외된 dto입니다.
     * @return 생성된 멤버번호를 dto에 담아 반환합니다.
     * @author 노수연
     */
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

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * 프론트 서버에서 요청한 유저의 정보를 멤버아이디로 테이블에서 찾고 이를 dto에 담아 반환합니다.
     *
     * @param memberId 테이블에서 해당 멤버아이디로 멤버를 찾습니다.
     * @return 찾은 멤버의 필드 정보들을 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberResponseDto>> memberDetails(
        @PathVariable("memberId") String memberId) {
        CommonResponseBody<MemberResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberService.findMember(memberId));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 프론트 서버에서 넘어온 DTO를 받아 서비스 클래스에서 dirty checking으로 테이블의 해당 멤버 데이터를 수정합니다.
     *
     * @param memberId   멤버아이디로 테이블에서 멤버를 찾습니다.
     * @param requestDto 수정할 정보가 담긴 DTO 입니다.
     * @return ResponseEntity에는 아무것도 담지않고 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberId}/info")
    public ResponseEntity<CommonResponseBody<Void>> memberModify(
        @PathVariable("memberId") String memberId,
        @Valid @RequestBody MemberUpdateRequestDto requestDto) {

        memberService.modifyMember(memberId, requestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()
            ), null
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 프론트 서버에서 유저가 회원 탈퇴를 하면 넘겨받은 멤버아이디로
     * 테이블에서 해당 멤버를 찾아 아이디를 제외한 유저의 개인정보들을 UUID로 수정한 뒤
     * 멤버 상태를 탈퇴 회원으로 바꿉니다.
     *
     * @param memberId   멤버아이디로 테이블에서 멤버를 찾습니다.
     * @param requestDto '탈퇴 회원'이라는 값이 담긴 DTO를 넘겨받습니다.
     * @return ResponseEntity에는 아무것도 담지않고 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberId}/withdraw")
    public ResponseEntity<CommonResponseBody<Void>> memberDelete(
        @PathVariable("memberId") String memberId,
        @Valid @RequestBody MemberStatusUpdateAdminRequestDto requestDto
    ) {

        memberService.withDrawMember(memberId, requestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()
            ), null
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/oauth/login/find")
    public ResponseEntity<CommonResponseBody<Boolean>> OauthLogin(
        @RequestBody MemberOauthLoginRequestDto requestDto) {

        if (memberService.checkMemberOauthEmailExists(requestDto.getEmail())) {
            return ResponseEntity.ok()
                .body(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_EMAIL_EXISTS_TRUE_MESSAGE.getSuccessMessage()
                ), false));
        }

        if (!memberService.checkMemberOauthInfoExists(requestDto.getEmail())) {
            memberService.socialMemberAdd(requestDto.getEmail(), requestDto.getEncodedEmail());
        }

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_SOCIAL_LOGIN_MESSAGE.getSuccessMessage()
            ), true
        ));

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

    /**
     * 프론트 서버에서 회원가입을 할 때 유저가 입력한 아이디가 테이블에 중복인지 체크하는 API 입니다.
     *
     * @param memberId 유저가 입력한 아이디 값을 파라미터로 받습니다.
     * @return boolean 값으로 중복인지 아닌지 확인할 수 있도록 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/sign-up-check/memberId/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> memberIdDuplicateCheck(
        @PathVariable("memberId") String memberId) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""), memberService.checkMemberIdDuplicate(memberId)
        ));
    }

    /**
     * 프론트 서버에서 회원가입을 할 때 유저가 입력한 닉네임이 테이블에 중복인지 체크하는 API 입니다.
     *
     * @param nickname 유저가 입력한 닉네임 값을 파라미터로 받습니다.
     * @return boolean 값으로 중복인지 아닌지 확인할 수 있도록 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/sign-up-check/nickname/{nickname}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> nicknameDuplicateCheck(
        @PathVariable("nickname") String nickname) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""),
            memberService.checkNickNameDuplicate(nickname)
        ));
    }

    /**
     * 프론트 서버에서 회원가입을 할 때 유저가 입력한 이메일이 테이블에 중복인지 체크하는 API 입니다.
     *
     * @param email 유저가 입력한 이메일 값을 파라미터로 받습니다.
     * @return boolean 값으로 중복인지 아닌지 확인할 수 있도록 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/sign-up-check/email/{email}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> emailDuplicateCheck(
        @PathVariable("email") String email) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""),
            memberService.checkEmailDuplicate(email)
        ));
    }

    /**
     * 프론트 서버에서 회원가입을 할 때 유저가 입력한 핸드폰 번호가 테이블에 중복인지 체크하는 API 입니다.
     *
     * @param phoneNumber 유저가 입력한 핸드폰번호 값을 파라미터로 받습니다.
     * @return boolean 값으로 중복인지 아닌지 확인할 수 있도록 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/sign-up-check/phoneNumber/{phoneNumber}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> phoneNumberCheck(
        @PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""),
            memberService.checkPhoneNumberDuplicate(phoneNumber)
        ));
    }

}
