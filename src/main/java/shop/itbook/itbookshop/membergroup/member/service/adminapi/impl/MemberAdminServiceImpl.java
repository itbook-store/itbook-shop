package shop.itbook.itbookshop.membergroup.member.service.adminapi.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateAdminRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.adminapi.MemberAdminService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.service.adminapi.MemberStatusAdminService;
import shop.itbook.itbookshop.membergroup.memberstatus.transfer.MemberStatusTransfer;

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
    //private final CustomMemberRepository customMemberRepository;
    private final MemberStatusAdminService memberStatusAdminService;

    @Override
    public MemberResponseProjectionDto findMember(String id) {

        Optional<MemberResponseProjectionDto> member =
            memberRepository.querydslFindById(id);

        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }

        return member.get();
    }

    @Override
    public List<MemberResponseProjectionDto> findMemberList() {

        return memberRepository.querydslFindAll();
    }

    @Override
    @Transactional
    public Long addMember(MemberSaveRequestDto requestDto) {

        Member member = MemberTransfer.dtoToEntityInSave(requestDto);
        return memberRepository.save(member).getMemberNo();
    }

    @Override
    @Transactional
    public void modifyMember(String id, MemberUpdateAdminRequestDto requestDto) {

        Optional<Member> member = memberRepository.querydslFindByIdToMember(id);

        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }

        Member modifiedMember = member.get();

        MemberStatus memberStatus = MemberStatusTransfer.dtoToEntity(
            memberStatusAdminService.findMemberStatus(requestDto.getMemberStatusName()));

        modifiedMember.setMemberStatus(memberStatus);

    }

    @Override
    @Transactional
    public void removeMember(Long memberNo) {
        memberRepository.deleteById(memberNo);
    }
}
