package shop.itbook.itbookshop.ordergroup.ordermember.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;

/**
 * 회원 주문 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderMemberRepository extends JpaRepository<OrderMember, OrderMember.Pk> {
    /**
     * 주문 번호로 회원 주문 엔티티를 가져옵니다.
     *
     * @param orderNo 회원 엔티티를 가지고 있는 주문 번호
     * @return 해당 주문 번호를 가지고 있는 엔티티의 Optional 객체
     * @author 정재원 *
     */
    Optional<OrderMember> findByOrder_OrderNo(Long orderNo);
}
