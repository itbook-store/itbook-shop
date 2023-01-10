package shop.itbook.itbookshop.membergroup.member.controller.adminapi;

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
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.resultmessageenum.CategoryResultMessageEnum;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberNoResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
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
     * @param memberNo 멤버 번호로 멤버를 조회합니다.
     * @return memberResponseDto를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<Member>> memberDetails(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<Member> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                "멤버 조회를 성공하였습니다."),
            memberAdminService.findMember(memberNo)
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
    public ResponseEntity<CommonResponseBody<List<MemberResponseDto>>> memberList() {

        CommonResponseBody<List<MemberResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                    "모든 멤버정보 조회에 성공하였습니다."),
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
    @PostMapping()
    public ResponseEntity<CommonResponseBody<MemberNoResponseDto>> memberAdd(
        @Valid @RequestBody MemberSaveRequestDto requestDto) {

        MemberNoResponseDto memberNoResponseDto =
            new MemberNoResponseDto(memberAdminService.addMember(requestDto));

        CommonResponseBody<MemberNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                "멤버 삽입에 성공하였습니다."),
            memberNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);

    }

    /**
     * 멤버 정보를 수정하는 메서드입니다.
     *
     * @param memberNo   멤버 번호로 해당 멤버의 정보를 수정합니다.
     * @param requestDto 멤버를 수정하기 위한 dto 입니다.
     * @return null을 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<Void>> memberUpdate(
        @PathVariable("memberNo") Long memberNo, @Valid @RequestBody
    MemberUpdateRequestDto requestDto) {

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(), "수정 완료하였습니다."),
            memberAdminService.updateMember(memberNo, requestDto)
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
    @DeleteMapping("/{memberNo}")
    //TODO 1. Object를 Void로 수정
    public ResponseEntity<CommonResponseBody<Object>> memberDelete(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<Object> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.OK.value(),
                "삭제 완료하였습니다."),
            memberAdminService.deleteMember(memberNo)
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);

    }

}
