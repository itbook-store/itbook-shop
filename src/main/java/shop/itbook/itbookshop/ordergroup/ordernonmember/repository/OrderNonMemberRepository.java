package shop.itbook.itbookshop.ordergroup.ordernonmember.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.ordernonmember.entity.OrderNonMember;

/**
 * 비회원 주문 정보 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderNonMemberRepository extends JpaRepository<OrderNonMember, Long> {
}
