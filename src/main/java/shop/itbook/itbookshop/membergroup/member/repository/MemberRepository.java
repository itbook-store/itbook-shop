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

    /**
     * 닉네임으로 테이블에 회원이 존재하는지 확인하는 메서드입니다.
     *
     * @param nickname 닉네임으로 테이블에 회원 데이터가 있는지 확인합니다.
     * @return 존재하면 true 없으면 false를 반환합니다.
     * @author 노수연
     */
    boolean existsByNickname(String nickname);

    /**
     * @param email the email
     * @return 존재하면 true 없으면 false를 반환합니다.
     * @author 노수연
     */
    boolean existsByEmail(String email);

    /**
     * @param phoneNumber the phone number
     * @return 존재하면 true 없으면 false를 반환합니다.
     * @author 노수연
     */
    boolean existsByPhoneNumber(String phoneNumber);
}
