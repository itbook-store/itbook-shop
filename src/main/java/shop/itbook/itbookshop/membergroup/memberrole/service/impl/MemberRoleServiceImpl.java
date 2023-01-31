package shop.itbook.itbookshop.membergroup.memberrole.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.entity.MemberRole;
import shop.itbook.itbookshop.membergroup.memberrole.repository.MemberRoleRepository;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;
import shop.itbook.itbookshop.role.entity.Role;

/**
 * 회원 권한에 대한 비지니스 로직을 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    @Override
    public List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo) {
        return memberRoleRepository.findMemberRoleWithMemberNo(memberNo);
    }

    @Override
    @Transactional
    public void addMemberRole(Member member, Role role) {
        MemberRole memberRole = new MemberRole(member, role);
        log.info("memberRole = {}", memberRole);

        memberRoleRepository.save(memberRole);
    }
}
