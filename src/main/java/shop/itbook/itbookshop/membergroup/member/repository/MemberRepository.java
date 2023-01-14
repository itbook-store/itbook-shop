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

    /**
     * 회원 아이디를 통해 해당 회원이 존재 여부를 확인하는 메서드 입니다.
     *
     * @param memberId the member id
     * @return the boolean
     * @author 강명관
     */
    boolean existsByMemberId(String memberId);
}
