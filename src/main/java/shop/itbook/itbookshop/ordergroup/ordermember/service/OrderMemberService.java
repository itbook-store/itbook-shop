package shop.itbook.itbookshop.ordergroup.ordermember.service;

import java.util.Optional;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;

/**
 * @author 이하늬
 * @since 1.0
 */
public interface OrderMemberService {
    Optional<OrderMember> findOptionalOrderMember(Long orderNo);
}
