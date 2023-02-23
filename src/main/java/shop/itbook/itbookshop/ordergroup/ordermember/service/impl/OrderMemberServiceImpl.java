package shop.itbook.itbookshop.ordergroup.ordermember.service.impl;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.OrderMember;
import shop.itbook.itbookshop.ordergroup.ordermember.repository.OrderMemberRepository;
import shop.itbook.itbookshop.ordergroup.ordermember.service.OrderMemberService;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderMemberServiceImpl implements OrderMemberService {
    private final OrderMemberRepository orderMemberRepository;

    @Override
    public Optional<OrderMember> findOptionalOrderMember(Long orderNo) {
        return orderMemberRepository.findById(orderNo);
    }
}
