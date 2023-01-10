package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;

/**
 * 멤버 서비스 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;

    @Override
    public Member findMember(Long memberNo) {

        Optional<Member> member = memberRepository.findById(memberNo);

        member.orElseThrow(MemberNotFoundException::new);

        return member.get();
    }

    @Override
    public List<MemberResponseDto> findMemberList() {

        return memberRepository.findAllBy();
    }

    @Override
    @Transactional
    public Long addMember(MemberSaveRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntityInSave(requestDto);
        return memberRepository.save(member).getMemberNo();
    }

    @Override
    @Transactional
    public Void updateMember(Long memberNo, MemberUpdateRequestDto requestDto) {

        memberRepository.findById(memberNo).orElseThrow(MemberNotFoundException::new);

        Member member = memberRepository.findById(memberNo).get();

        member.update(requestDto);

        return null;
    }

    @Override
    @Transactional
    public Void deleteMember(Long memberNo) {
        memberRepository.deleteById(memberNo);
        return null;
    }
}
