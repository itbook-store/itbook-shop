package shop.itbook.itbookshop.ordergroup.order.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDestinationDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;

/**
 * 주문 엔티티 관련 쿼리 dsl 을 처리합니다.
 *
 * @author 정재원, 최겸준, 강명관
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

    Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListOfAdmin(Pageable pageable);

    Page<OrderSubscriptionListDto> findAllSubscriptionOrderListOfMember(Pageable pageable,
                                                                        Long memberNo);

    Order findOrderByDeliveryNo(Long deliveryNo);

    /**
     * 일반 상품 주문에 대한 상세 조회를 위한 쿼리 입니다.
     *
     * @param orderNo 주문번호
     * @return 주문상세 DTO
     */
    OrderDetailsResponseDto findOrderDetail(Long orderNo);

    /**
     * 가장 최신상태의 주문 엔티티를 가져오는 쿼리 입니다.
     *
     * @param orderNo 주문 번호
     * @return 가장 최신상태의 주문 엔티티 (Optional)
     * @author 강명관
     */
    Optional<Order> findOrderOfLatestStatus(Long orderNo);

    List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsResponseDto(
        Long orderNo);

    OrderDetailsResponseDto findOrderDetailOfNonMember(Long orderNo);

    List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsOfNonMember(
        Long orderNo);
}
