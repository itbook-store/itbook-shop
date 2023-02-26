package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제_요청에_필요한_정보_클래스;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface 회원_유형에_대한_결제전_처리_인터페이스 {
    결제_요청에_필요한_정보_클래스 회원_유형에_대한_결제전_처리_진행(
        결제전_처리전반에_필요한_정보_클래스 infoForProcessOrderBeforePayment);
}
