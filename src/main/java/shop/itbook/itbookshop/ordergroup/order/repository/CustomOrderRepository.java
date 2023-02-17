package shop.itbook.itbookshop.ordergroup.order.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 엔티티 관련 쿼리 dsl 을 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomOrderRepository {

    Page<OrderListMemberViewResponseDto> getOrderListOfMemberWithStatus(Pageable pageable,
                                                                        Long memberNo);

    String findOrderStatusByOrderNo(Long orderNo);

    OrderDestinationDto findOrderDestinationsByOrderNo(Long orderNo);

    Page<OrderListAdminViewResponseDto> getOrderListOfAdminWithStatus(Pageable pageable);

    List<Order> paymentCompleteSubscriptionProductStatusChangeWaitDelivery();

    Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListByAdmin(Pageable pageable);

    Page<OrderSubscriptionListDto> findAllSubscriptionOrderListByMember(Pageable pageable,
                                                                        Long memberNo);

    Order findOrderByDeliveryNo(Long deliveryNo);
}
