package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberServiceService;

/**
 * 사용자 권한을 가진 요청에 응답하는 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/service/members")
@RequiredArgsConstructor
public class MemberServiceController {

    /*@PostMapping()
    public ResponseEntity<CommonResponseBody>*/

    private final MemberServiceService memberServiceService;

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
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
            memberServiceService.findMemberAuthInfo(memberId)
        ));


    }
}
