package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import java.util.List;
import java.util.Objects;
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
    public MemberResponseDto findMember(Long memberNo) {

        MemberResponseDto memberResponseDto = memberRepository.findMemberById(memberNo);

        if (Objects.isNull(memberResponseDto)) {
            throw new MemberNotFoundException("해당 memberNo가 존재하지 않습니다.");
        }

        return memberResponseDto;
    }

    @Override
    public List<MemberResponseDto> findMemberList() {

        return memberRepository.findAllBy();
    }

    @Override
    @Transactional
    public Long saveMember(MemberSaveRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntity(requestDto);
        return memberRepository.save(member).getMemberNo();
    }

    @Override
    @Transactional
    public Boolean updateMember(Long memberNo, MemberUpdateRequestDto requestDto) {

        if (Objects.isNull(memberRepository.findMemberById(memberNo).getId())) {
            return false;
        }

        //

        return true;
    }

    @Override
    @Transactional
    public void deleteMember(Long memberNo) {
        memberRepository.deleteById(memberNo);
    }
}
