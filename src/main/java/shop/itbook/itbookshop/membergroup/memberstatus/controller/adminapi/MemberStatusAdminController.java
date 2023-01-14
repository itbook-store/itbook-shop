package shop.itbook.itbookshop.membergroup.memberstatus.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseDto;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;

/**
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class MemberStatusAdminController {

    private final MemberStatusAdminService memberStatusAdminService;

    @GetMapping("/test")
    public MemberStatusResponseDto test() {
        return memberStatusAdminService.findMemberStatusWithMemberStatusNo(329);
    }

}
