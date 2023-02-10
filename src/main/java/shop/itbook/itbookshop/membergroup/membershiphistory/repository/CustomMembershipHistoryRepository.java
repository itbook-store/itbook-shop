package shop.itbook.itbookshop.membergroup.membershiphistory.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.membershiphistory.dto.response.MembershipHistoryResponseDto;

/**
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomMembershipHistoryRepository {

    List<MembershipHistoryResponseDto> findByMemberNo(Long memberNo);
}
