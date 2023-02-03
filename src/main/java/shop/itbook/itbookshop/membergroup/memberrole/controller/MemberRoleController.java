package shop.itbook.itbookshop.membergroup.memberrole.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleAllResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.resutmessageenum.MemberRoleResultMessageEnum;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.service.MembershipService;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.service.RoleService;

/**
 * 멤버의 권한 찾기, 등록, 삭제하는 API들을 작성한 컨트롤러 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/member-roles")
@RequiredArgsConstructor
public class MemberRoleController {

    private final MemberRoleService memberRoleService;

    private final MemberService memberService;

    private final MembershipService membershipService;

    private final MemberStatusAdminService memberStatusAdminService;

    private final RoleService roleService;

    @PostMapping("/{memberId}/{roleName}/add")
    public ResponseEntity<CommonResponseBody<Void>> memberRoleAdd(
        @PathVariable("memberId") String memberId, @PathVariable("roleName") String roleName) {

        MemberResponseDto memberResponseDto = memberService.findMember(memberId);
        Member member = MemberTransfer.dtoToEntityByResponseDto(memberResponseDto);

        Membership membership =
            membershipService.findMembershipByMembershipGrade(
                memberResponseDto.getMembershipGrade());

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(
                memberResponseDto.getMemberStatusName()));

        member.setMemberNo(memberResponseDto.getMemberNo());
        member.setMembership(membership);
        member.setMemberStatus(memberStatus);

        Role role = roleService.findRole(roleName);

        memberRoleService.addMemberRole(member, role);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberRoleResultMessageEnum.MEMBER_ROLE_DELETE_MESSAGE.getSuccessMessage()), null);

        return ResponseEntity.status(HttpStatus.OK)
            .body(commonResponseBody);
    }

    @DeleteMapping("/{memberNo}/{roleNo}/delete")
    public ResponseEntity<CommonResponseBody<Void>> memberRoleRemove(
        @PathVariable("memberNo") Long memberNo, @PathVariable("roleNo") Integer roleNo) {

        memberRoleService.deleteMemberRole(memberNo, roleNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberRoleResultMessageEnum.MEMBER_ROLE_DELETE_MESSAGE.getSuccessMessage()), null);

        return ResponseEntity.status(HttpStatus.OK)
            .body(commonResponseBody);
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<List<MemberRoleAllResponseDto>>> memberRoleDetails(
        @PathVariable("memberNo") Long memberNo) {

        CommonResponseBody<List<MemberRoleAllResponseDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                MemberRoleResultMessageEnum.MEMBER_ROLE_DELETE_MESSAGE.getSuccessMessage()),
                memberRoleService.findMemberRoleAllInfoWithMemberNo(memberNo));

        return ResponseEntity.status(HttpStatus.OK)
            .body(commonResponseBody);
    }
}
