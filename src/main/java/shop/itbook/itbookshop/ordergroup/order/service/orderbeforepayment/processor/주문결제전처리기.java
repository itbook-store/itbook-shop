package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import shop.itbook.itbookshop.ordergroup.order.dto.결제전_처리전반에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.dto.response.결제_요청에_필요한_정보_클래스;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.회원_유형에_대한_결제전_처리_인터페이스;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.주문_유형에_대한_결제전_처리기_인터페이스;

/**
 * @author 최겸준
 * @since 1.0
 */
public abstract class 주문결제전처리기 {

    // 결제전처리전반에필요한정보
    // 결제전
    // 결제요청에필요한정보클래스

    public 결제_요청에_필요한_정보_클래스 결제전_처리(
        결제전_처리전반에_필요한_정보_클래스 결제전_처리전반에_필요한_정보) {

        // 주문 유형
        주문_유형에_대한_결제전_처리기_인터페이스
            주문_유형에_대한_결제전_처리기 = 주문_유형에_대한_결제전_처리기_생성();

        주문_유형에_대한_결제전_처리기.주문_유형에_대한_결제전_처리_진행(
            결제전_처리전반에_필요한_정보);

        // 회원 유형
        회원_유형에_대한_결제전_처리_인터페이스
            회원_유형에_대한_결제전_처리기 = 회원_유형에_대한_결제전_처리기_생성();

        결제_요청에_필요한_정보_클래스 결제_요청에_필요한_정보 =
            회원_유형에_대한_결제전_처리기.회원_유형에_대한_결제전_처리_진행(
                결제전_처리전반에_필요한_정보);

        return 결제_요청에_필요한_정보;
    }

    protected abstract 주문_유형에_대한_결제전_처리기_인터페이스 주문_유형에_대한_결제전_처리기_생성();

    protected abstract 회원_유형에_대한_결제전_처리_인터페이스 회원_유형에_대한_결제전_처리기_생성();
}
