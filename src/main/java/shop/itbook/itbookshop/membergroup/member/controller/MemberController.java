package shop.itbook.itbookshop.membergroup.member.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.membergroup.member.domain.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.domain.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.service.MemberService;

/**
 * 멤버 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDto> inquireMember(
        @PathVariable("memberNo") Long memberNo) {

        MemberResponseDto member = memberService.getMember(memberNo);

        return ResponseEntity.status(HttpStatus.OK).body(member);

    }

    @GetMapping()
    public ResponseEntity<List<MemberResponseDto>> inquireMembers() {

        List<MemberResponseDto> members = memberService.getMembers();

        return ResponseEntity.status(HttpStatus.OK).body(members);

    }

    @PostMapping()
    public ResponseEntity<Long> saveMember(@RequestBody MemberSaveRequestDto requestDto) {

        Long pk = memberService.saveMember(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(pk);

    }

    @DeleteMapping("/{memberNo}")
    public ResponseEntity<Object> deleteMember(@PathVariable("memberNo") Long memberNo) {

        memberService.deleteMember(memberNo);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

}
