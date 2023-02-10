package shop.itbook.itbookshop.membergroup.membership.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * 멤버십 레포지토리입니다.
 *
 * @author 강명관
 * @author 노수연
 * @since 1.0
 */
public interface MembershipRepository
    extends JpaRepository<Membership, Integer> {

    Optional<Membership> findByMembershipGrade(String membershipGrade);

    List<MembershipResponseDto> findAllBy();
}
