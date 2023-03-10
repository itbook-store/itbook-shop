package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.GiftIncreaseDecreasePointHistoryNoResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;

/**
 * ????????? ????????? ?????? ????????? ???????????? ?????????????????????.
 *
 * @author ?????????
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberDestinationService memberDestinationService;

    private final PointHistoryCommonService pointHistoryCommonService;

    /**
     * ????????? ???????????? ????????? ????????? dto??? ????????? ????????? ???????????? ?????? ???????????? ????????? ????????????
     * ????????? ?????? ????????? dto??? ?????? ???????????? ??????????????????.
     *
     * @param requestDto ??????????????? ?????????????????? ????????? ????????? dto?????????.
     * @return ????????? ??????????????? dto??? ?????? ???????????????.
     * @author ?????????
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
     * ????????? ???????????? ????????? ????????? ????????? ??????No??? ??????????????? ?????? ?????? dto??? ?????? ???????????????.
     *
     * @param memberNo ??????????????? ?????? ??????No??? ????????? ????????????.
     * @return ?????? ????????? ?????? ???????????? dto??? ?????? ???????????????.
     * @author ?????????
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
     * ????????? ???????????? ????????? ????????? ????????? ?????? ???????????? ??????????????? ?????? ?????? dto??? ?????? ???????????????.
     *
     * @param memberId ??????????????? ?????? ?????????????????? ????????? ????????????.
     * @return ?????? ????????? ?????? ???????????? dto??? ?????? ???????????????.
     * @author ?????????
     */
    @GetMapping("/memberId/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberResponseDto>> memberDetailsByMemberId(
        @PathVariable("memberId") String memberId) {

        CommonResponseBody<MemberResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberService.findMemberByMemberId(memberId));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * ????????? ???????????? ????????? DTO??? ?????? ????????? ??????????????? dirty checking?????? ???????????? ?????? ?????? ???????????? ???????????????.
     *
     * @param memberNo   ??????No??? ??????????????? ????????? ????????????.
     * @param requestDto ????????? ????????? ?????? DTO ?????????.
     * @return ResponseEntity?????? ???????????? ???????????? ???????????????.
     * @author ?????????
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
     * ????????? ???????????? ????????? ?????? ????????? ?????? ???????????? ??????????????????
     * ??????????????? ?????? ????????? ?????? ???????????? ????????? ????????? ?????????????????? UUID??? ????????? ???
     * ?????? ????????? ?????? ???????????? ????????????.
     *
     * @param memberNo   ??????No??? ??????????????? ????????? ????????????.
     * @param requestDto '?????? ??????'????????? ?????? ?????? DTO??? ??????????????????.
     * @return ResponseEntity?????? ???????????? ???????????? ???????????????.
     * @author ?????????
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
     * ????????? ?????? ??????????????? ??? ????????? ????????? ???????????? ???????????? ???????????? API ?????????.
     * ?????? ???????????? ????????? ????????? ???????????? ???????????? ???????????????.
     *
     * @param memberSocialRequestDto ?????? ???????????? ????????? ????????? ?????? ???????????? ???????????? DTO ?????????.
     * @return ?????? ????????? ???????????????.
     * @author ?????????
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
     * ?????? ???????????? ???????????? API ?????????.
     * Oauth ????????? ??? ???????????? ???????????? ?????? ????????? ????????? ???????????? ?????? ????????? ??????????????? ???????????? ??????????????? ??????????????? ??????????????????.
     * ???????????? ????????? ????????? ????????? ??????????????? ?????? ?????? ???????????? ??? ???????????? ??? ??? ????????? ????????? ????????? ?????? ????????? ????????????
     * OauthRegister API??? ?????? ????????? ??????????????? DB??? ???????????? ??? ????????????.
     *
     * @param requestDto Oauth ????????? ??? ???????????? ???????????? ?????? ????????? ????????? ???????????? ?????? ????????? ??????????????? ???????????? ??????????????? ??????????????? ??????????????????.
     * @return ?????? ?????? Boolean ?????? ???????????????.
     * @author ?????????
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
     * Auth ????????? ??? ????????? ?????? ????????? ???????????? API ?????????.
     *
     * @param memberId the member id
     * @return the response entity
     * @author ?????????
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
     * ????????? ???????????? ??????????????? ??? ??? ????????? ????????? ???????????? ???????????? ???????????? ???????????? API ?????????.
     *
     * @param memberId ????????? ????????? ????????? ?????? ??????????????? ????????????.
     * @return boolean ????????? ???????????? ????????? ????????? ??? ????????? dto??? ?????? ???????????????.
     * @author ?????????
     */
    @GetMapping("/sign-up-check/memberId/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> memberIdDuplicateCheck(
        @PathVariable("memberId") String memberId) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""), memberService.checkMemberIdDuplicate(memberId)
        ));
    }

    /**
     * ????????? ???????????? ??????????????? ??? ??? ????????? ????????? ???????????? ???????????? ???????????? ???????????? API ?????????.
     *
     * @param nickname ????????? ????????? ????????? ?????? ??????????????? ????????????.
     * @return boolean ????????? ???????????? ????????? ????????? ??? ????????? dto??? ?????? ???????????????.
     * @author ?????????
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
     * ????????? ???????????? ??????????????? ??? ??? ????????? ????????? ???????????? ???????????? ???????????? ???????????? API ?????????.
     *
     * @param email ????????? ????????? ????????? ?????? ??????????????? ????????????.
     * @return boolean ????????? ???????????? ????????? ????????? ??? ????????? dto??? ?????? ???????????????.
     * @author ?????????
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
     * ????????? ???????????? ??????????????? ??? ??? ????????? ????????? ????????? ????????? ???????????? ???????????? ???????????? API ?????????.
     *
     * @param phoneNumber ????????? ????????? ??????????????? ?????? ??????????????? ????????????.
     * @return boolean ????????? ???????????? ????????? ????????? ??? ????????? dto??? ?????? ???????????????.
     * @author ?????????
     */
    @GetMapping("/sign-up-check/phoneNumber/{phoneNumber}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> phoneNumberCheck(
        @PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""),
            memberService.checkPhoneNumberDuplicate(phoneNumber)
        ));
    }


    /**
     * ????????? ???????????? ???????????? ?????? ????????? ??? ????????? ????????? ???????????? ????????? ??????????????? ???????????? API ?????????.
     *
     * @param name ????????? ????????? ?????? ?????? ??????????????? ????????????.
     * @return boolean ????????? ???????????? ????????? ????????? ??? ????????? dto??? ?????? ???????????????.
     * @author ?????????
     */
    @GetMapping("/register-check/memberId/{memberId}/name/{name}")
    public ResponseEntity<CommonResponseBody<MemberBooleanResponseDto>> nameCheck(
        @PathVariable("memberId") String memberId,
        @PathVariable("name") String name) {

        return ResponseEntity.ok().body(new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(""),
            memberService.checkNameDuplicate(memberId, name)
        ));
    }


    /**
     * @param memberNo the member no
     * @return the response entity
     * @author
     */
    @GetMapping("/{memberNo}/member-destinations")
    public ResponseEntity<CommonResponseBody<List<MemberDestinationResponseDto>>> memberDestinationList(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<List<MemberDestinationResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberDestinationResultMessageEnum.MEMBER_DESTINATION_FIND_MESSAGE.getSuccessMessage()),
                memberDestinationService.findMemberDestinationResponseDtoByMemberNo(memberNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Member destination delete response entity.
     *
     * @param memberDestinationNoResponseDtoList the member destination no response dto list
     * @return the response entity
     * @author
     */
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

    /**
     * Member destination add response entity.
     *
     * @param memberDestinationRequestDto the member destination request dto
     * @return the response entity
     * @author
     */
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

    /**
     * Member destination modify response entity.
     *
     * @param recipientDestinationNo      the recipient destination no
     * @param memberDestinationRequestDto the member destination request dto
     * @return the response entity
     * @author
     */
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

    /**
     * Member destination details response entity.
     *
     * @param recipientDestinationNo the recipient destination no
     * @return the response entity
     * @author
     */
    @GetMapping("/{memberNo}/memberDestinations/{recipientDestinationNo}/info")
    public ResponseEntity<CommonResponseBody<MemberDestinationResponseDto>> memberDestinationDetails(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("recipientDestinationNo") Long recipientDestinationNo) {

        CommonResponseBody<MemberDestinationResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberDestinationResultMessageEnum.MEMBER_DESTINATION_FIND_MESSAGE.getSuccessMessage()),
                MemberDestinationTransfer.entityToDto(
                    memberDestinationService.findByRecipientDestinationNoAndMemberNo(memberNo,
                        recipientDestinationNo))
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Member recently point find response entity.
     *
     * @param memberNo the member no
     * @return the response entity
     * @author
     */
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

    /**
     * Member gift point response entity.
     *
     * @param senderMemberNo            the sender member no
     * @param memberPointSendRequestDto the member point send request dto
     * @return the response entity
     * @author
     */
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
