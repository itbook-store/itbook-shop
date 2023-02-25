package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.general;

import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.memberapply.MemberApplicableWhenOrderBeforePaymentCancel;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
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
public class GeneralOrderBeforePaymentCancelMemberService
    extends AbstractGeneralOrderBeforePaymentCancel {
    private final OrderStatusHistoryService orderStatusHistoryService;

    private final MemberApplicableWhenOrderBeforePaymentCancel
        memberApplicableWhenOrderBeforePaymentCancel;

    public GeneralOrderBeforePaymentCancelMemberService(
        OrderStatusHistoryService orderStatusHistoryService,
        MemberApplicableWhenOrderBeforePaymentCancel memberApplicableWhenOrderBeforePaymentCancel) {
        super(orderStatusHistoryService);
        this.orderStatusHistoryService = orderStatusHistoryService;
        this.memberApplicableWhenOrderBeforePaymentCancel =
            memberApplicableWhenOrderBeforePaymentCancel;
    }

    @Override
    @Transactional
    public void processOrderBeforePaymentCancel(Order order) {

        super.checkOrderStatus(order.getOrderNo());

        this.rollbackAboutOrderSuccess(order);

        super.changeOrderStatusAboutOrderCancel(order);
    }

    private void rollbackAboutOrderSuccess(Order order) {

        List<OrderProduct> orderProductList =
            memberApplicableWhenOrderBeforePaymentCancel.rollbackAboutOrderSuccess(order);

        OrderStatusHistory orderStatusHistory =
            orderStatusHistoryService.findOrderStatusHistoryByOrderNo(order.getOrderNo());
        OrderStatus orderStatus = orderStatusHistory.getOrderStatus();
        OrderStatusEnum orderStatusEnum = orderStatus.getOrderStatusEnum();

        this.IncreaseStock(orderProductList, orderStatusEnum);
    }

    private void IncreaseStock(List<OrderProduct> orderProductList,
                               OrderStatusEnum orderStatusEnum) {
        if (Objects.equals(orderStatusEnum, OrderStatusEnum.WAIT_DELIVERY) ||
            Objects.equals(orderStatusEnum, OrderStatusEnum.PAYMENT_COMPLETE)) {

            for (OrderProduct orderProduct : orderProductList) {
                Product product = orderProduct.getProduct();
                int stock = product.getStock();
                product.setStock(stock + orderProduct.getCount());
            }
        }
    }
}
