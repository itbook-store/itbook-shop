package shop.itbook.itbookshop.membergroup.memberstatus.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * 멤버상태 JPA 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Primary
public interface MemberStatusRepository
    extends JpaRepository<MemberStatus, Integer>, CustomMemberStatusRepository {

    //List<MemberStatusResponseProjectionDto> findAllBy();
}
