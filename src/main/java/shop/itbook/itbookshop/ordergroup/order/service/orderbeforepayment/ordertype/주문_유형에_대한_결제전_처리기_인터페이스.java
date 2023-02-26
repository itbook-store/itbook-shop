package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전처리전반에필요한정보클래스;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface 주문유형에대한결제전처리기인터페이스 {
    void 주문유형에대한결제전처리진행(
        결제전처리전반에필요한정보클래스 infoForProcessOrderBeforePayment);
}
