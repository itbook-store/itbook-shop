package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.processor;

import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.ordertype.OrderAfterPaymentSuccessServiceAboutOrderType;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class OrderAfterPaymentSuccessProcessor {

    public Order processOrderAfterPaymentSuccess(Order order) {

        OrderAfterPaymentSuccessServiceAboutMemberType
            orderAfterPaymentSuccessServiceAboutMemberType =
            this.createOrderAfterPaymentSuccessAboutMemberTypeService();

        orderAfterPaymentSuccessServiceAboutMemberType.processAboutMemberType(order);

        OrderAfterPaymentSuccessServiceAboutOrderType
            orderAfterPaymentSuccessServiceAboutOrderType =
            this.createOrderAfterPaymentSuccessAboutOrderTypeService();
        orderAfterPaymentSuccessServiceAboutOrderType.processAboutOrderType(order);

        return order;
    }

    protected abstract OrderAfterPaymentSuccessServiceAboutMemberType createOrderAfterPaymentSuccessAboutMemberTypeService();

    protected abstract OrderAfterPaymentSuccessServiceAboutOrderType createOrderAfterPaymentSuccessAboutOrderTypeService();

}
