package shop.itbook.itbookshop.membergroup.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 멤버 JPA 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */

public interface MemberRepository extends JpaRepository<Member, Long>, CustomMemberRepository {

}
