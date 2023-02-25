package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.nonmember;

import java.util.List;
import java.util.Objects;
import shop.itbook.itbookshop.deliverygroup.delivery.service.serviceapi.DeliveryService;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.exception.ProductStockIsZeroException;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.service.OrderProductService;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.service.OrderStatusHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * @author 최겸준
 * @since 1.0
 */
public class NonMemberGeneralOrderAfterPaymentSuccess
    extends AbstractNonMemberOrderAfterPaymentSuccess {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final DeliveryService deliveryService;
    private final OrderProductService orderProductService;

    public NonMemberGeneralOrderAfterPaymentSuccess(
        OrderStatusHistoryService orderStatusHistoryService,
        DeliveryService deliveryService,
        OrderProductService orderProductService,
        OrderStatusHistoryService orderStatusHistoryService1, DeliveryService deliveryService1,
        OrderProductService orderProductService1) {
        super(orderStatusHistoryService, deliveryService, orderProductService);
        this.orderStatusHistoryService = orderStatusHistoryService1;
        this.deliveryService = deliveryService1;
        this.orderProductService = orderProductService1;
    }

    @Override
    protected void processOrderAfterPaymentSuccessWithoutNonMemberProcessing(Order order) {
        this.changeOrderStatus(order);
        this.changeDeliveryStatus(order);
        this.checkAndSetStock(order);
    }


    protected void changeOrderStatus(Order order) {
        orderStatusHistoryService.addOrderStatusHistory(order, OrderStatusEnum.WAIT_DELIVERY);
    }

    protected void changeDeliveryStatus(Order order) {
        deliveryService.registerDelivery(order);
    }

    protected void checkAndSetStock(Order order) {

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
}
