package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.nonmember;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.ProductStockIsZeroException;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccess;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class AbstractNonMemberOrderAfterPaymentSuccess
    implements OrderAfterPaymentSuccess {

    @Override
    public Order processOrderAfterPaymentSuccess(Order order) {

        this.processOrderAfterPaymentSuccessWithoutNonMemberProcessing(order);
        return order;
    }

    protected abstract void processOrderAfterPaymentSuccessWithoutNonMemberProcessing(Order order);
}
