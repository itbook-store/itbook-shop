package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;

/**
 * 관리자 권한을 가진 요청에 응답하는 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberAdminService memberAdminService;

    /**
     * 특정 멤버 번호의 멤버를 조회하는 메서드입니다.
     *
     * @param memberId 멤버 아이디로 멤버를 조회합니다.
     * @return memberResponseDto를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<MemberExceptPwdResponseDto>> memberDetails(
        @PathVariable("memberId") String memberId) {

        CommonResponseBody<MemberExceptPwdResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.findMember(memberId)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 모든 멤버들을 조회하는 메서드입니다.
     *
     * @return MemberResponseDto 리스트를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping()
    public ResponseEntity<CommonResponseBody<List<MemberExceptPwdResponseDto>>> memberList() {

        CommonResponseBody<List<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.findMemberList());

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    /**
     * 멤버 정보를 수정하는 메서드입니다.
     *
     * @param memberId   멤버 아이디로 해당 멤버의 정보를 수정합니다.
     * @param requestDto 멤버를 수정하기 위한 dto 입니다.
     * @return null을 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<CommonResponseBody<Void>> memberStatusModify(
        @PathVariable("memberId") String memberId,
        @Valid @RequestBody MemberStatusUpdateAdminRequestDto requestDto) {

        memberAdminService.modifyMember(memberId, requestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
            null
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
