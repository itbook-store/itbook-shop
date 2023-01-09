package shop.itbook.itbookshop.membergroup.membership.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.membership.dto.request.MembershipRequestDTO;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipNoReponseDTO;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.resultmessageenum.MembershipResultMessageEnum;
import shop.itbook.itbookshop.membergroup.membership.service.adminapi.MembershipAdminService;

/**
 * 회원 등급에 대한 관리자 api 컨트롤러 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/membership")
@RequiredArgsConstructor
public class MembershipAdminController {

    private final MembershipAdminService membershipAdminService;

    /**
     * 관리자가 회원 등급을 등록하기 위한 REST Controller 입니다.
     *
     * @param membershipRequestDTO the membership request dto
     * @return the response entity
     * @author gwanii *
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<MembershipNoReponseDTO>> membershipAdd(
        @RequestBody @Valid MembershipRequestDTO membershipRequestDTO) {


        MembershipNoReponseDTO membershipNoReponseDTO =
            new MembershipNoReponseDTO(membershipAdminService.addMembership(membershipRequestDTO));

        CommonResponseBody<MembershipNoReponseDTO> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(true, HttpStatus.CREATED.value(),
                MembershipResultMessageEnum.MEMBERSHIP_CREATE_SUCCESS.getMessage()),
            membershipNoReponseDTO
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


}
