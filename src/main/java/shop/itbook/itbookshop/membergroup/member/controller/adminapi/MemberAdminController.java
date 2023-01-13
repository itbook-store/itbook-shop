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
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
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
     * @param id 멤버 아이디로 멤버를 조회합니다.
     * @return memberResponseDto를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponseBody<MemberResponseProjectionDto>> memberDetails(
        @PathVariable("id") String id) {

        CommonResponseBody<MemberResponseProjectionDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                    MemberResultMessageEnum.MEMBER_FIND_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.findMember(id)
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
    public ResponseEntity<CommonResponseBody<List<MemberResponseProjectionDto>>> memberList() {

        CommonResponseBody<List<MemberResponseProjectionDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                    MemberResultMessageEnum.MEMBER_LIST_SUCCESS_MESSAGE.getSuccessMessage()),
                memberAdminService.findMemberList());

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    /**
     * 멤버 정보를 저장하는 메서드입니다.
     *
     * @param requestDto 멤버를 저장하기 위한 dto 입니다.
     * @return 멤버 번호 dto를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    /*@PostMapping()
    public ResponseEntity<CommonResponseBody<MemberNoResponseDto>> memberAdd(
        @Valid @RequestBody MemberSaveRequestDto requestDto) {

        MemberNoResponseDto memberNoResponseDto =
            new MemberNoResponseDto(memberAdminService.addMember(requestDto));

        CommonResponseBody<MemberNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                MemberResultMessageEnum.MEMBER_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            memberNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }*/

    /**
     * 멤버 정보를 수정하는 메서드입니다.
     *
     * @param id         멤버 아이디로 해당 멤버의 정보를 수정합니다.
     * @param requestDto 멤버를 수정하기 위한 dto 입니다.
     * @return null을 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseBody<Void>> memberModify(
        @PathVariable("id") String id,
        @Valid @RequestBody
        MemberUpdateAdminRequestDto requestDto) {

        memberAdminService.modifyMember(id, requestDto);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                MemberResultMessageEnum.MEMBER_MODIFY_SUCCESS_MESSAGE.getSuccessMessage()),
            null
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }


    /**
     * 멤버 번호를 받아서 해당 멤버 정보를 삭제하는 메서드입니다.
     *
     * @param memberNo 멤버 번호로 해당 멤버를 삭제합니다.
     * @return null을 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    /*@DeleteMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<Void>> memberRemove(
        @PathVariable("memberNo") Long memberNo) {

        memberAdminService.removeMember(memberNo);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                MemberResultMessageEnum.MEMBER_REMOVE_SUCCESS_MESSAGE.getSuccessMessage()), null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);

    }*/

}
