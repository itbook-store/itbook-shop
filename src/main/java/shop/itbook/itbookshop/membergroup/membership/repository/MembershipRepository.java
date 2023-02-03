package shop.itbook.itbookshop.membergroup.membership.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.membership.dto.response.MembershipResponseDto;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;

/**
 * @author 강명관
 * @since 1.0
 */
public interface MembershipRepository
    extends JpaRepository<Membership, Integer> {

    Optional<Membership> findByMembershipGrade(String membershipGrade);

    List<MembershipResponseDto> findAllBy();
}
