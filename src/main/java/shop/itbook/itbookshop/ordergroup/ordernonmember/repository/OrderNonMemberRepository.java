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
public interface OrderNonMemberRepository extends JpaRepository<OrderNonMember, OrderNonMember.Pk> {

    /**
     * 주문 번호로 비회원 주문 엔티티를 찾습니다.
     *
     * @param orderNo 검색할 주문 번호
     * @return 찾은 비회원 주문 엔티티의 Optional 객체
     * @author 정재원 *
     */
    Optional<OrderNonMember> findByOrder_OrderNo(Long orderNo);
}
