package shop.itbook.itbookshop.membergroup.memberdestination.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;

/**
 * 회원의 배송지 정보 엔티티의 Repository
 *
 * @author 정재원
 * @since 1.0
 */
public interface MemberDestinationRepository
    extends JpaRepository<MemberDestination, Long>, CustomMemberDestinationRepository {
}
