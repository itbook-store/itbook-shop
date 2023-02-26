package shop.itbook.itbookshop.ordergroup.order.service.presentation.orderbeforepayment.membertype;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제_요청에_필요한_정보_클래스;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface OrderBeforePaymentServiceAboutMemberType {
    결제_요청에_필요한_정보_클래스 processAboutMemberType(
        결제전_처리전반에_필요한_정보_클래스 infoForProcessOrderBeforePayment);
}
