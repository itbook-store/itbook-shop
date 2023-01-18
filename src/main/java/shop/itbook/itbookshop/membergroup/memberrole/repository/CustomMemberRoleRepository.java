package shop.itbook.itbookshop.membergroup.memberrole.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;

/**
 * 회원 권한 테이블의 QueryDsl 을 사용하기 위한 CustomRepository 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@NoRepositoryBean
public interface CustomMemberRoleRepository {

    List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo);
}
