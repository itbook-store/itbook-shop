package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberOauthLoginRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberPointSendRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSocialRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberBooleanResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberNoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberRecentlyPointResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.request.MemberDestinationRequestDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationNoResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.resultmessageenum.MemberDestinationResultMessageEnum;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.membergroup.memberdestination.transfer.MemberDestinationTransfer;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.GiftIncreaseDecreasePointHistoryNoResponseDto;

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

    private final MemberDestinationService memberDestinationService;

    private final PointHistoryCommonService pointHistoryCommonService;

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
     * 프론트 서버에서 요청한 유저의 정보를 멤버No로 테이블에서 찾고 이를 dto에 담아 반환합니다.
     *
     * @param memberNo 테이블에서 해당 멤버No로 멤버를 찾습니다.
     * @return 찾은 멤버의 필드 정보들을 dto에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<MemberResponseDto>> memberDetails(
        @PathVariable("memberNo") Long memberNo) {
        CommonResponseBody<MemberResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberService.findMember(memberNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 프론트 서버에서 넘어온 DTO를 받아 서비스 클래스에서 dirty checking으로 테이블의 해당 멤버 데이터를 수정합니다.
     *
     * @param memberNo   멤버No로 테이블에서 멤버를 찾습니다.
     * @param requestDto 수정할 정보가 담긴 DTO 입니다.
     * @return ResponseEntity에는 아무것도 담지않고 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberNo}/info")
    public ResponseEntity<CommonResponseBody<Void>> memberModify(
        @PathVariable("memberNo") Long memberNo,
        @Valid @RequestBody MemberUpdateRequestDto requestDto) {

        memberService.modifyMember(memberNo, requestDto);

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
     * @param memberNo   멤버No로 테이블에서 멤버를 찾습니다.
     * @param requestDto '탈퇴 회원'이라는 값이 담긴 DTO를 넘겨받습니다.
     * @return ResponseEntity에는 아무것도 담지않고 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberNo}/withdraw")
    public ResponseEntity<CommonResponseBody<Void>> memberDelete(
        @PathVariable("memberNo") Long memberNo,
        @Valid @RequestBody MemberStatusUpdateAdminRequestDto requestDto
    ) {

        memberService.withDrawMember(memberNo, requestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()
            ), null
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }


    /**
     * 유저가 소셜 회원가입할 때 유저의 정보를 등록하는 메서드를 실행하는 API 입니다.
     * 소셜 회원가입 폼에서 받아온 데이터를 테이블에 저장합니다.
     *
     * @param memberSocialRequestDto 소셜 회원가입 폼에서 입력한 유저 데이터가 들어있는 DTO 입니다.
     * @return 회원 번호를 반환합니다.
     * @author 노수연
     */
    @PutMapping("/sign-up/social")
    public ResponseEntity<CommonResponseBody<MemberNoResponseDto>> OauthRegister(
        @RequestBody MemberSocialRequestDto memberSocialRequestDto) {

        MemberNoResponseDto memberNoResponseDto =
            new MemberNoResponseDto(memberService.modifySocialMember(memberSocialRequestDto));

        CommonResponseBody<MemberNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            memberNoResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * 소셜 로그인시 실행되는 API 입니다.
     * Oauth 로그인 시 받아오는 이메일로 정보 입력이 필요한 테이블의 모든 필드에 저장시키고 인코딩된 이메일로는 비밀번호에 저장시킵니다.
     * 그런다음 홈으로 돌아간 유저는 메인페이지 대신 소셜 회원가입 폼 페이지를 볼 수 있는데 여기에 유저의 개인 정보를 입력하면
     * OauthRegister API를 통해 자신의 개인정보를 DB에 저장시킬 수 있습니다.
     *
     * @param requestDto Oauth 로그인 시 받아오는 이메일로 정보 입력이 필요한 테이블의 모든 필드에 저장시키고 인코딩된 이메일로는 비밀번호에 저장시킵니다.
     * @return 성공 여부 Boolean 값을 반환합니다.
     * @author 노수연
     */
    @PostMapping("/oauth/login/find")
    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> OauthLogin(
        @RequestBody MemberOauthLoginRequestDto requestDto) {

        SuccessfulResponseDto successfulResponseDto = new SuccessfulResponseDto();

        if (memberService.checkMemberOauthEmailExists(requestDto.getEmail())) {
            successfulResponseDto.setIsSuccessful(false);

            return ResponseEntity.ok()
                .body(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_EMAIL_EXISTS_TRUE_MESSAGE.getSuccessMessage()),
                    successfulResponseDto
                ));
        }

        if (!memberService.checkMemberOauthInfoExists(requestDto.getEmail())) {
            memberService.socialMemberAdd(requestDto.getEmail(), requestDto.getEncodedEmail());
        }

        successfulResponseDto.setIsSuccessful(true);

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_SOCIAL_LOGIN_MESSAGE.getSuccessMessage()
            ), successfulResponseDto
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

    @GetMapping("/{memberNo}/member-destinations")
    public ResponseEntity<CommonResponseBody<List<MemberDestinationResponseDto>>> memberDestinationList(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<List<MemberDestinationResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberDestinationResultMessageEnum.MEMBER_DESTINATION_FIND_MESSAGE.getSuccessMessage()),
                memberDestinationService.findMemberDestinationResponseDtoByMemberNo(memberNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @DeleteMapping("/memberDestinations/delete")
    public ResponseEntity<CommonResponseBody<Void>> memberDestinationDelete(
        @RequestBody List<MemberDestinationNoResponseDto> memberDestinationNoResponseDtoList) {

        memberDestinationService.deleteMemberDestination(memberDestinationNoResponseDtoList);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberDestinationResultMessageEnum.MEMBER_DESTINATION_DELETE_MESSAGE.getSuccessMessage()),
            null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }

    @PostMapping("/memberDestinations/add")
    public ResponseEntity<CommonResponseBody<MemberDestinationNoResponseDto>> memberDestinationAdd(
        @RequestBody MemberDestinationRequestDto memberDestinationRequestDto) {

        CommonResponseBody<MemberDestinationNoResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberDestinationResultMessageEnum.MEMBER_DESTINATION_SAVE_MESSAGE.getSuccessMessage()),
                new MemberDestinationNoResponseDto(
                    memberDestinationService.addMemberDestination(memberDestinationRequestDto))
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @PutMapping("/memberDestinations/{recipientDestinationNo}/modify")
    public ResponseEntity<CommonResponseBody<Void>> memberDestinationModify(
        @PathVariable("recipientDestinationNo") Long recipientDestinationNo,
        @RequestBody MemberDestinationRequestDto memberDestinationRequestDto) {

        memberDestinationService.modifyMemberDestination(recipientDestinationNo,
            memberDestinationRequestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberDestinationResultMessageEnum.MEMBER_DESTINATION_MODIFY_MESSAGE.getSuccessMessage()),
            null
        );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/memberDestinations/{recipientDestinationNo}/info")
    public ResponseEntity<CommonResponseBody<MemberDestinationResponseDto>> memberDestinationDetails(
        @PathVariable("recipientDestinationNo") Long recipientDestinationNo) {

        CommonResponseBody<MemberDestinationResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberDestinationResultMessageEnum.MEMBER_DESTINATION_FIND_MESSAGE.getSuccessMessage()),
                MemberDestinationTransfer.entityToDto(
                    memberDestinationService.findByRecipientDestinationNo(recipientDestinationNo))
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/{memberNo}/point/find")
    public ResponseEntity<CommonResponseBody<MemberRecentlyPointResponseDto>> memberRecentlyPointFind(
        @PathVariable("memberNo") Long memberNo) {

        Member member = memberService.findMemberByMemberNo(memberNo);

        CommonResponseBody<MemberRecentlyPointResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_POINT_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                new MemberRecentlyPointResponseDto(
                    pointHistoryCommonService.findRecentlyPoint(member))
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @PostMapping("/{senderMemberNo}/point-gift/send")
    public ResponseEntity<CommonResponseBody<GiftIncreaseDecreasePointHistoryNoResponseDto>> memberGiftPoint(
        @PathVariable("senderMemberNo") Long senderMemberNo,
        @RequestBody MemberPointSendRequestDto memberPointSendRequestDto) {

        CommonResponseBody<GiftIncreaseDecreasePointHistoryNoResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_POINT_GIFT_SUCCESS_MESSAGE.getSuccessMessage()),
                new GiftIncreaseDecreasePointHistoryNoResponseDto(
                    memberService.sendMemberToMemberGiftPoint(senderMemberNo,
                        memberPointSendRequestDto.getReceiveMemberNo(),
                        memberPointSendRequestDto.getGiftPoint()))
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
