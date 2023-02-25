package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.nonmember;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.NotStatusOfOrderCancel;
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
@Service
public class NonMemberGeneralOrderBeforePaymentCancel
    extends AbstractNonMemberOrderBeforePaymentCancel {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderProductService orderProductService;

    public NonMemberGeneralOrderBeforePaymentCancel(
        OrderStatusHistoryService orderStatusHistoryService,
        OrderProductService orderProductService) {
        this.orderStatusHistoryService = orderStatusHistoryService;
        this.orderProductService = orderProductService;
    }

    @Override
    protected void processOrderBeforePaymentCancelWithoutMemberProcessing(Order order) {
        this.checkOrderStatus(order.getOrderNo());

        this.increaseStock(order);

        this.changeOrderStatusAboutOrderCancel(order);
    }

    protected void checkOrderStatus(Long orderNo) {
        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(orderNo);
        OrderStatusEnum orderStatusEnum = orderStatusHistory.getOrderStatus().getOrderStatusEnum();
        if (!(orderStatusEnum.equals(OrderStatusEnum.PAYMENT_COMPLETE) ||
            orderStatusEnum.equals(OrderStatusEnum.WAIT_DELIVERY) ||
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

    protected void changeOrderStatusAboutOrderCancel(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.REFUND_COMPLETED);
    }


}
