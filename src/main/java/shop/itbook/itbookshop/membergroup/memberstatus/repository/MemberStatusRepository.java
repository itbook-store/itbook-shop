package shop.itbook.itbookshop.membergroup.memberstatus.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dto.response.MemberStatusResponseProjectionDto;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;

/**
 * 멤버상태 JPA 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberStatusRepository extends JpaRepository<MemberStatus, Integer> {

    List<MemberStatusResponseProjectionDto> findAllBy();
}
