package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전처리전반에필요한정보클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제요청에필요한정보클래스;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface 회원유형에대한결제전처리인터페이스 {
    결제요청에필요한정보클래스 회원유형에대한결제전처리진행(
        결제전처리전반에필요한정보클래스 infoForProcessOrderBeforePayment);
}
