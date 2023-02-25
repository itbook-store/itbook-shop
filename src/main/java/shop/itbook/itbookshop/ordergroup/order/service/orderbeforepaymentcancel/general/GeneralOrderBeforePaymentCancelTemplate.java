package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.OrderStatusHistory;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class GeneralOrderBeforePaymentCancelTemplate implements OrderBeforePaymentCancel {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderProductService orderProductService;

    @Override
    public void cancel(Order order) {

        checkOrderStatus(order.getOrderNo());
        startUsageProcessing(order);
        changeOrderStatusAboutOrderCancel(order);
    }

    @Override
    public void checkOrderStatus(Long orderNo) {
        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();
        if (!(orderStatusEnum.equals(OrderStatusEnum.PAYMENT_COMPLETE) ||
            orderStatusEnum.equals(OrderStatusEnum.WAIT_DELIVERY) ||
            orderStatusEnum.equals(OrderStatusEnum.DELIVERY_COMPLETED))) {
            throw new NotStatusOfOrderCancel();
        }
    }

    @Override
    public void changeOrderStatusAboutOrderCancel(Order order) {

        // 배송 대기, 환불 완료 상태일 경우 재고 증가.
        List<OrderProduct> orderProductList =
            orderProductService.findOrderProductsEntityByOrderNo(order.getOrderNo());

        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(order.getOrderNo());
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();

        orderProductList.forEach(orderProduct -> {
            if (Objects.equals(orderStatusEnum, OrderStatusEnum.WAIT_DELIVERY) ||
                Objects.equals(orderStatusEnum, OrderStatusEnum.PAYMENT_COMPLETE)) {
                Product product = orderProduct.getProduct();
                int stock = product.getStock();
                product.setStock(stock + orderProduct.getCount());
            }
        });

        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.REFUND_COMPLETED);
    }

    protected abstract void startUsageProcessing(Order order);
}
