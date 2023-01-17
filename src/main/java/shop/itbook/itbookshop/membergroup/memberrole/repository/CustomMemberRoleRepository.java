package shop.itbook.itbookshop.membergroup.memberrole.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.memberrole.dto.response.MemberRoleResponseDto;

/**
 * @author 강명관
 * @since 1.0
 */

@NoRepositoryBean
public interface CustomMemberRoleRepository {

    List<MemberRoleResponseDto> findMemberRoleWithMemberNo(Long memberNo);
}
