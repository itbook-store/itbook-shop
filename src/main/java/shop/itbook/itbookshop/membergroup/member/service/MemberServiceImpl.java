package shop.itbook.itbookshop.membergroup.member.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.domain.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.domain.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.domain.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;

/**
 * 멤버 서비스 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MemberResponseDto getMember(Long memberNo) {
        return memberRepository.findMemberById(memberNo);
    }

    @Override
    public List<MemberResponseDto> getMembers() {
        return memberRepository.findAllBy();
    }

    @Override
    @Transactional
    public Long saveMember(MemberSaveRequestDto requestDto) {
        return memberRepository.save(requestDto.dtoToEntity()).getMemberNo();
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
        memberRepository.deleteMemberById(memberNo);
    }
}
