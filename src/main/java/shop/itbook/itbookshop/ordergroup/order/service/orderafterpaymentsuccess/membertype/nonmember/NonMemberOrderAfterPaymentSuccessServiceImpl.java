package shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.nonmember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.membertype.OrderAfterPaymentSuccessServiceAboutMemberType;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class NonMemberOrderAfterPaymentSuccessServiceImpl
    implements OrderAfterPaymentSuccessServiceAboutMemberType {

    @Override
    public Order processAboutMemberType(Order order) {
        return order;
    }
}
