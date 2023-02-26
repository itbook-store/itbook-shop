package shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.membertype.회원_유형에_대한_결제전_처리_인터페이스;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.ordertype.주문_유형에_대한_결제전_처리기_인터페이스;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class MemberGeneral주문결제전처리기 extends 주문결제전처리기 {


    @Qualifier("memberOrderBeforePaymentServiceImpl")
    private final 회원_유형에_대한_결제전_처리_인터페이스
        memberOrderBeforePaymentServiceImpl;

    @Qualifier("generalOrderBeforePaymentServiceImpl")
    private final 주문_유형에_대한_결제전_처리기_인터페이스
        generalOrderBeforePaymentServiceImpl;

    @Override
    protected 주문_유형에_대한_결제전_처리기_인터페이스 주문유형에대한결제전처리기생성() {
        return generalOrderBeforePaymentServiceImpl;
    }

    @Override
    protected 회원_유형에_대한_결제전_처리_인터페이스 회원_유형에_대한_결제전_처리기_생성() {
        return memberOrderBeforePaymentServiceImpl;
    }
}
