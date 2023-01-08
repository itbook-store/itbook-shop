package shop.itbook.itbookshop.membergroup.member.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.membergroup.memberservice.entity.MemberService;

/**
 * 사용자 권한을 가진 요청에 응답하는 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class MemberServiceController {

    private final MemberService memberService;
}
