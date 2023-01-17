package shop.itbook.itbookshop.membergroup.memberrole.service;

import java.util.List;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;

/**
 * 회원 권한에 대한 비지니스 로직을 담당하는 인터페이스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
public interface MemberRoleService {

    List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo);
}
