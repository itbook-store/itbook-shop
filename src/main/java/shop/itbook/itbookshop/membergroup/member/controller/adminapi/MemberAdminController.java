package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberStatusUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountByMembershipResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberCountResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdBlockResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;
import shop.itbook.itbookshop.membergroup.member.resultmessageenum.MemberResultMessageEnum;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;

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

    private final MemberRoleService memberRoleService;

    /**
     * 특정 멤버 번호의 멤버를 조회하는 메서드입니다.
     *
     * @param memberId 멤버 아이디로 멤버를 조회합니다.
     * @return MemberExceptPwdResponseDto를 ResponseEntity에 담아 반환합니다.
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
     * @return MemberExceptPwdResponseDto 리스트를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping()
    public ResponseEntity<CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>>> memberList(
        @PageableDefault
        Pageable pageable) {

        Page<MemberExceptPwdResponseDto> page = memberAdminService.findMemberList(pageable);

        PageResponse<MemberExceptPwdResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    @GetMapping("/normal")
    public ResponseEntity<CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>>> normalMemberList(
        @PageableDefault Pageable pageable) {
        Page<MemberExceptPwdResponseDto> page = memberAdminService.findNormalMemberList(pageable);

        PageResponse<MemberExceptPwdResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @GetMapping("/block")
    public ResponseEntity<CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>>> blockMemberList(
        @PageableDefault Pageable pageable) {
        Page<MemberExceptPwdResponseDto> page = memberAdminService.findBlockMemberList(pageable);

        PageResponse<MemberExceptPwdResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @GetMapping("/withdraw")
    public ResponseEntity<CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>>> withdrawMemberList(
        @PageableDefault Pageable pageable) {
        Page<MemberExceptPwdResponseDto> page = memberAdminService.findWithdrawMemberList(pageable);

        PageResponse<MemberExceptPwdResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 멤버상태 값을 수정하는 메서드입니다.
     * 관리자는 개별 멤버별로 차단회원, 정상회원 상태 값을 변경할 수 있습니다.
     *
     * @param memberId   멤버 아이디로 해당 멤버의 상태정보를 수정합니다.
     * @param requestDto 멤버상태 값이 담긴 dto 입니다. 차단회원, 정상회원 2개의 상태가 담길 수 있습니다.
     * @return ResponseEntity에 아무것도 담지 않고 반환합니다.
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


    /**
     * 관리자가 관리자 페이지에서 검색하였을 때
     * 검색 기준과 검색 단어 별로 회원 테이블에서 데이터들을 리스트로 찾아와
     * ResponseEntity에 담아 반환합니다.
     *
     * @param searchRequirement 검색 기준입니다. 아이디, 닉네임, 이름, 전화번호 기준으로 검색할 수 있습니다.
     * @param searchWord        검색 단어입니다.
     * @return MemberExceptPwdResponseDto 리스트를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/search/{memberStatusName}/{searchRequirement}/{searchWord}")
    public ResponseEntity<CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>>> memberListBySearch(
        @PathVariable("memberStatusName") String memberStatusName,
        @PathVariable("searchRequirement") String searchRequirement,
        @PathVariable("searchWord") String searchWord,
        @PageableDefault Pageable pageable
    ) {

        Page<MemberExceptPwdResponseDto> page = null;

        if (searchRequirement.equals("memberId")) {
            page =
                memberAdminService.findMemberListByMemberId(searchWord, memberStatusName, pageable);
        }

        if (searchRequirement.equals("nickname")) {
            page =
                memberAdminService.findMemberListByNickname(searchWord, memberStatusName, pageable);
        }

        if (searchRequirement.equals("name")) {
            page = memberAdminService.findMemberListByName(searchWord, memberStatusName, pageable);
        }

        if (searchRequirement.equals("phoneNumber")) {
            page = memberAdminService.findMemberListByPhoneNumber(searchWord, memberStatusName,
                pageable);
        }

        if (searchRequirement.equals("everything")) {
            page = memberAdminService.findMemberListBySearchWord(searchWord, memberStatusName,
                pageable);
        }

        PageResponse<MemberExceptPwdResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<MemberExceptPwdResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @GetMapping("/{memberId}/block")
    public ResponseEntity<CommonResponseBody<MemberExceptPwdBlockResponseDto>> blockMemberDetails(
        @PathVariable("memberId") String memberId) {

        CommonResponseBody<MemberExceptPwdBlockResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.findBlockMember(memberId));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    @GetMapping("/memberStatus/count")
    public ResponseEntity<CommonResponseBody<MemberCountResponseDto>> memberCountByMemberStatus() {

        CommonResponseBody<MemberCountResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MemberResultMessageEnum.MEMBER_COUNT_SUCCESS_MESSAGE.getSuccessMessage()),
            memberAdminService.memberCountByMemberStatus());

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @GetMapping("/membership/count")
    public ResponseEntity<CommonResponseBody<MemberCountByMembershipResponseDto>> memberCountByMembership() {

        CommonResponseBody<MemberCountByMembershipResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MemberResultMessageEnum.MEMBER_COUNT_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.memberCountByMembership());

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
