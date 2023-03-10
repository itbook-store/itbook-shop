package shop.itbook.itbookshop.membergroup.membership.controller;

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
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipModifyRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipNoResponseDto;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.resultmessageenum.MembershipResultMessageEnum;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.membership.transfer.MembershipTransfer;

/**
 * 회원 등급에 대한 관리자 api 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/membership")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    /**
     * 관리자가 회원 등급을 등록하기 위한 REST Controller 입니다.
     *
     * @param membershipRequestDto the membership request dto
     * @return the response entity
     * @author 강명관 *
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<MembershipNoResponseDto>> membershipAdd(
        @RequestBody @Valid MembershipRequestDto membershipRequestDto) {


        MembershipNoResponseDto membershipNoResponseDto =
            new MembershipNoResponseDto(membershipService.addMembership(membershipRequestDto));

        CommonResponseBody<MembershipNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                MembershipResultMessageEnum.MEMBERSHIP_CREATE_SUCCESS.getMessage()),
            membershipNoResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 관리자가 회원 등급을 삭제하기 위한 메서드 입니다.
     *
     * @param memberNo the member no
     * @return the response entity
     * @author 강명관 *
     */
    @DeleteMapping("/{membershipNo}")
    public ResponseEntity<CommonResponseBody<Void>> membershipRemove(
        @PathVariable(value = "membershipNo") Integer memberNo) {

        membershipService.removeMembership(memberNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipResultMessageEnum.MEMBERSHIP_DELETE_SUCCESS.getMessage()),
                null
            ));
    }

    /**
     * 관리자가 회원 등급 수정을 위한 메서드 입니다.
     *
     * @param membershipNo               the membership no
     * @param membershipModifyRequestDto the membership modify request dto
     * @return the response entity
     * @author 강명관 *
     */
    @PutMapping("/{membershipNo}")
    public ResponseEntity<CommonResponseBody<Void>> membershipModify(
        @PathVariable(value = "membershipNo") Integer membershipNo,
        @RequestBody @Valid MembershipModifyRequestDto membershipModifyRequestDto
    ) {

        membershipService.modifyMembership(membershipNo, membershipModifyRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipResultMessageEnum.MEMBERSHIP_MODIFY_SUCCESS.getMessage()),
                null));
    }

    @GetMapping("/{membershipName}")
    public ResponseEntity<CommonResponseBody<MembershipResponseDto>> membershipDetails(
        @PathVariable("membershipName") String membershipName) {

        CommonResponseBody<MembershipResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MembershipResultMessageEnum.MEMBERSHIP_FIND_SUCCESS.getMessage()),
                MembershipTransfer.entityToDto(
                    membershipService.findMembershipByMembershipGrade(membershipName)));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/membershipNo/{membershipNo}")
    public ResponseEntity<CommonResponseBody<MembershipResponseDto>> membershipDetailsByMembershipNo(
        @PathVariable("membershipNo") Integer membershipNo) {

        CommonResponseBody<MembershipResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MembershipResultMessageEnum.MEMBERSHIP_FIND_SUCCESS.getMessage()),
                membershipService.findMembership(membershipNo));

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping()
    public ResponseEntity<CommonResponseBody<List<MembershipResponseDto>>> membershipList() {

        CommonResponseBody<List<MembershipResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    MembershipResultMessageEnum.MEMBERSHIP_LIST_SUCCESS.getMessage()),
                membershipService.findMembershipList());

        return ResponseEntity.ok().body(commonResponseBody);
    }

}
