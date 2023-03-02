package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.nonmember;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.membertype.OrderBeforePaymentCancelServiceAboutMemberType;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class NonMemberOrderBeforePaymentCancelServiceImpl
    implements OrderBeforePaymentCancelServiceAboutMemberType {

    @Override
    @Transactional
    public void processAboutMemberType(Order order) {

    }
}
