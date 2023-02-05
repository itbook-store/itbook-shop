package shop.itbook.itbookshop.ordergroup.ordermember.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;

/**
 * 회원 주문 엔티티의 Repository 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
public interface OrderMemberRepository extends JpaRepository<OrderMember, Long> {
}
