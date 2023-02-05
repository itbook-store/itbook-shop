package shop.itbook.itbookshop.ordergroup.order.service.impl;

import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;

/**
 * OrderAdminService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public void addOrder() {
        // TODO: 2023/02/04 쿠폰 이력 추가
        // TODO: 2023/02/04 포인트 이력 추가

        // TODO: 2023/02/04 주문 상태 변경
    }
}
