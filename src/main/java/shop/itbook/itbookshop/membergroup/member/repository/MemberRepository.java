package shop.itbook.itbookshop.membergroup.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.membergroup.member.domain.MemberResponseDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;

/**
 * 멤버 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    MemberResponseDto findMemberById(Long id);

    List<MemberResponseDto> findAllBy();

    void deleteMemberById(Long id);
}
