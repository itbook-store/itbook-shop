package shop.itbook.itbookshop.membergroup.memberrole.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;
import shop.itbook.itbookshop.membergroup.memberrole.repository.MemberRoleRepository;
import shop.itbook.itbookshop.membergroup.memberrole.service.MemberRoleService;

/**
 * 회원 권한에 대한 비지니스 로직을 담당하는 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberRoleServiceImpl implements MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    @Override
    public List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo) {
        return memberRoleRepository.findMemberRoleWithMemberNo(memberNo);
    }
}
