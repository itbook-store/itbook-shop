package shop.itbook.itbookshop.membergroup.member.service.serviceapi.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberSaveRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberUpdateRequestDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberAuthResponseDto;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.exception.MemberNotFoundException;
import shop.itbook.itbookshop.membergroup.member.repository.CustomMemberRepository;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberServiceService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.role.entity.Role;
import shop.itbook.itbookshop.role.roleenum.RoleEnum;

/**
 * @author 노수연
 * @since 1.0
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberServiceServiceImpl implements MemberServiceService {

    private final CustomMemberRepository customMemberRepository;
    private final MemberRepository memberRepository;

    @Override
    public MemberResponseProjectionDto findMember(String id) {
        Optional<MemberResponseProjectionDto> member = customMemberRepository.querydslFindById(id);

        if (member.isEmpty()) {
            throw new MemberNotFoundException();
        }

        return member.get();
    }

    @Override
    public Long addMember(MemberSaveRequestDto requestDto) {
        Member member = MemberTransfer.dtoToEntityInSave(requestDto);
        return memberRepository.save(member).getMemberNo();
    }

    @Override
    public void modifyMember(String id, MemberUpdateRequestDto requestDto) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemberAuthResponseDto findMemberAuthInfo(String memberId) {

        if (!memberRepository.existsByMemberId(memberId)) {
            throw new MemberNotFoundException();
        }

        return memberRepository.findAuthInfoByMemberId(memberId);
    }
}
