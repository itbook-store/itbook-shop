package shop.itbook.itbookshop.membergroup.memberdestination.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;

/**
 * 회원 배송지 엔티티의 쿼리 dsl 을 처리하기 위한 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomMemberDestinationRepository {

    List<MemberDestinationResponseDto> getMemberDestinationResponseDtoByMemberNo(Long memberNo);
}
