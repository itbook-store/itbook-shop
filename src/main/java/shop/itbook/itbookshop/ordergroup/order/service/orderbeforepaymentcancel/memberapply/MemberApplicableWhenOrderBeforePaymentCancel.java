package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.memberapply;

import java.util.List;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface MemberApplicableWhenOrderBeforePaymentCancel {

    List<OrderProduct> rollbackAboutOrderSuccess(Order order);
}
