package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberNoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
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
     * 특정 아이디의 멤버를 조회하는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the response entity
     * @author 노수연
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDto> memberDetails(
        @PathVariable("memberNo") Long memberNo) {

        MemberResponseDto member = memberAdminService.findMember(memberNo);

        return ResponseEntity.status(HttpStatus.OK).body(member);

    }

    /**
     * 모든 멤버들을 조회하는 메서드입니다.
     *
     * @return the response entity
     * @author 노수연
     */
    @GetMapping()
    public ResponseEntity<List<MemberResponseDto>> memberList() {

        List<MemberResponseDto> members = memberAdminService.findMemberList();

        return ResponseEntity.status(HttpStatus.OK).body(members);

    }

    /**
     * 멤버 정보를 저장하는 메서드입니다.
     *
     * @param requestDto the request dto
     * @return the response entity
     * @author 노수연
     */
    @PostMapping()
    public ResponseEntity<CommonResponseBody<MemberNoResponseDto>> saveMember(
        @RequestBody MemberSaveRequestDto requestDto) {

        MemberNoResponseDto memberNoResponseDto =
            new MemberNoResponseDto(memberAdminService.saveMember(requestDto));

        CommonResponseBody<MemberNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                CategoryResultMessageEnum.CATEGORY_SAVE_SUCCESS_MESSAGE.getSuccessMessage()),
            memberNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);

    }

    /**
     * 멤버 번호를 받아서 해당 멤버 정보를 삭제하는 메서드입니다.
     *
     * @param memberNo the member no
     * @return the response entity
     * @author
     */
    @DeleteMapping("/{memberNo}")
    public ResponseEntity<Object> deleteMember(@PathVariable("memberNo") Long memberNo) {

        memberAdminService.deleteMember(memberNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

}
