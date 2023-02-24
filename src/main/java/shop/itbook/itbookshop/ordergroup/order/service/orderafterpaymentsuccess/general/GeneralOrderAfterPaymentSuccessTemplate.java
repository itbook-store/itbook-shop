package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.general;

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
public abstract class GeneralOrderAfterPaymentSuccessTemplate implements OrderAfterPaymentSuccess {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final DeliveryService deliveryService;
    private final OrderProductService orderProductService;

    @Override
    public Order success(Order order) {

        this.changeOrderStatus(order);
        this.changeDeliveryStatus(order);
        this.checkAndSetStock(order);
        this.startUsageProcessing(order);
        return order;
    }

    @Override
    public void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAIT_DELIVERY);
    }

    private void changeDeliveryStatus(Order order) {
        deliveryService.registerDelivery(order);
    }

    private void checkAndSetStock(Order order) {

        List<OrderProduct> orderProductsByOrderNo =
            orderProductService.findOrderProductsEntityByOrderNo(order.getOrderNo());

        orderProductsByOrderNo.stream()
            .forEach(orderProduct -> {
                Product product = orderProduct.getProduct();
                Integer productCnt = orderProduct.getCount();
                this.decreaseStock(product, productCnt);
            });
    }

    private void decreaseStock(Product product, Integer productCnt) {
        Integer stock = product.getStock();
        if (Objects.equals(stock, 0) || productCnt > stock) {
            throw new ProductStockIsZeroException();
        }

        product.setStock(stock - productCnt);
    }

    protected abstract void startUsageProcessing(Order order);
}
