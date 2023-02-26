package shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.ordertype;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePaymentServiceAboutOrderType {
    void processAboutOrderType(
        결제전_처리전반에_필요한_정보_클래스 infoForProcessOrderBeforePayment);
}
