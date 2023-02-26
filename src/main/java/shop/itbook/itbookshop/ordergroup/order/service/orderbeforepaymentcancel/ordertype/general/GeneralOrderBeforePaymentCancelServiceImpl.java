package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.general;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.ordertype.OrderBeforePaymentCancelServiceAboutOrderType;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class GeneralOrderBeforePaymentCancelServiceImpl implements
    OrderBeforePaymentCancelServiceAboutOrderType {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderProductService orderProductService;

    @Override
    @Transactional
    public void processAboutOrderType(Order order) {

        this.checkOrderStatus(order.getOrderNo());
        this.increaseStock(order);
        this.changeOrderStatusAboutOrderCancel(order);
    }

    private void checkOrderStatus(Long orderNo) {
        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();

        if (!(orderStatusEnum.equals(OrderStatusEnum.WAIT_DELIVERY) ||
            orderStatusEnum.equals(OrderStatusEnum.DELIVERY_COMPLETED))) {
            throw new NotStatusOfOrderCancel();
        }
    }

    private void increaseStock(Order order) {
        Long orderNo = order.getOrderNo();

        List<OrderProduct> orderProductList =
            orderProductService.findOrderProductsEntityByOrderNo(orderNo);

        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(order.getOrderNo());

        OrderStatus orderStatus = orderStatusHistory.getOrderStatus();
        OrderStatusEnum orderStatusEnum = orderStatus.getOrderStatusEnum();

        if (Objects.equals(orderStatusEnum, OrderStatusEnum.WAIT_DELIVERY) ||
            Objects.equals(orderStatusEnum, OrderStatusEnum.PAYMENT_COMPLETE)) {

            for (OrderProduct orderProduct : orderProductList) {
                Product product = orderProduct.getProduct();
                int stock = product.getStock();
                product.setStock(stock + orderProduct.getCount());
            }
        }
    }

    private void changeOrderStatusAboutOrderCancel(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.REFUND_COMPLETED);
    }
}
