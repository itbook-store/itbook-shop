package shop.itbook.itbookshop.ordergroup.order.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotAllowedPurchaseComplete;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderNotFoundException;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dto.OrderProductDetailResponseDto;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * OrderAdminService 인터페이스의 기본 구현체 입니다.
 *
 * @author 정재원
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderCrudServiceImpl implements OrderCrudService {

    private final OrderRepository orderRepository;
    private final OrderProductService orderProductService;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final ProductService productService;

    /**
     * The Origin url.
     */
    @Value("${payment.origin.url}")
    public String ORIGIN_URL;

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderDetailsResponseDto findOrderDetails(Long orderNo) {

        OrderDetailsResponseDto orderDetail = orderRepository.findOrderDetail(orderNo);

        setSellingPriceOfDetailsDto(orderDetail);

        return orderDetail;
    }


    public static void setSellingPriceOfDetailsDto(
        OrderDetailsResponseDto orderDetailsResponseDto) {
        long sellingAmount = 0L;

        for (OrderProductDetailResponseDto orderProductDetailResponseDto : orderDetailsResponseDto.getOrderProductDetailResponseDtoList()) {
            Long fixedPrice = orderProductDetailResponseDto.getFixedPrice();

            long sellingPrice = (fixedPrice - getDiscountedPrice(fixedPrice,
                orderProductDetailResponseDto.getDiscountPercent())) *
                orderProductDetailResponseDto.getCount();

            orderProductDetailResponseDto.setSellingPrice(sellingPrice);
            sellingAmount += sellingPrice;
        }

        orderDetailsResponseDto.setSellingAmount(sellingAmount);
    }

    private static long getDiscountedPrice(Long priceToApply, Double discountPercent) {
        return (long) (priceToApply * (discountPercent / 100));
    }

    @Override
    public Order findOrderEntity(Long orderNo) {
        return orderRepository.findById(orderNo).orElseThrow(OrderNotFoundException::new);
    }

    @Override
    public Optional<Order> findOrderByDeliveryNo(Long deliveryNo) {
        return Optional.of(orderRepository.findOrderByDeliveryNo(deliveryNo));
    }

    @Override
    public Page<OrderListMemberViewResponseDto> findOrderListOfMemberWithStatus(Pageable pageable,
                                                                                Long memberNo) {

        return orderRepository.getOrderListOfMemberWithStatus(pageable, memberNo);
    }

    @Override
    public Page<OrderListAdminViewResponseDto> findOrderListAdmin(Pageable pageable) {

        return orderRepository.getOrderListOfAdminWithStatus(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void orderPurchaseComplete(Long orderNo) {

        OrderStatusHistory orderStatusHistoryByOrderNo =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);

        if (!orderStatusHistoryByOrderNo.getOrderStatus().getOrderStatusEnum()
            .equals(OrderStatusEnum.DELIVERY_COMPLETED)) {
            throw new NotAllowedPurchaseComplete();
        }

        Order order = findOrderEntity(orderNo);

        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.PURCHASE_COMPLETE);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void addOrderStatusHistorySubscriptionProductDeliveryWait() {

        List<Order> orders =
            orderRepository.paymentCompleteSubscriptionProductStatusChangeWaitDelivery();

        orders.forEach(order -> orderStatusHistoryService.addOrderStatusHistory(order,
            OrderStatusEnum.WAIT_DELIVERY));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OrderSubscriptionAdminListDto> findAllSubscriptionOrderListByAdmin(
        Pageable pageable) {
        return orderRepository.findAllSubscriptionOrderListByAdmin(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<OrderSubscriptionListDto> findAllSubscriptionOrderListByMember(Pageable pageable,
                                                                               Long memberNo) {
        return orderRepository.findAllSubscriptionOrderListByMember(pageable, memberNo);
    }

    @Override
    public List<OrderSubscriptionDetailsResponseDto> findOrderSubscriptionDetailsResponseDto(
        Long orderNo) {
        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsResponseDtoList =
            orderRepository.findOrderSubscriptionDetailsResponseDto(orderNo);


        OrderSubscriptionDetailsResponseDto orderSubscription =
            orderSubscriptionDetailsResponseDtoList.get(0);

        Long fixedPrice = orderSubscription.getFixedPrice();
        Long sellingPrice =
            fixedPrice - getDiscountedPrice(fixedPrice, orderSubscription.getDiscountPercent());
        Long sellingAmount = sellingPrice * orderSubscription.getCount();

        orderSubscription.setSellingAmount(sellingAmount);
        return orderSubscriptionDetailsResponseDtoList;
    }

    @Override
    @Transactional
    public void deleteOrderAndRollBackStock(Long orderNo) {

        List<OrderProduct> orderProductList =
            orderProductService.findOrderProductsEntityByOrderNo(orderNo);

        if (orderProductList.isEmpty()) {
            if (!orderRepository.existsById(orderNo)) {
                throw new OrderNotFoundException();
            }
        }

        for (OrderProduct orderProduct : orderProductList) {
            Product product =
                productService.findProductEntity(orderProduct.getProduct().getProductNo());

            if (product.getIsSubscription()) {
                break;
            }

            Integer stock = product.getStock();
            product.setStock(stock + orderProduct.getCount());
        }

        orderRepository.deleteById(orderNo);
    }
}