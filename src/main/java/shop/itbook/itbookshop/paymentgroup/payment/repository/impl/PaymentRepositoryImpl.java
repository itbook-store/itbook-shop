package shop.itbook.itbookshop.paymentgroup.payment.repository.impl;

import com.querydsl.jpa.JPQLQuery;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.payment.entity.QPayment;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepositoryCustom;

/**
 * ProductRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class PaymentRepositoryImpl extends QuerydslRepositorySupport
    implements PaymentRepositoryCustom {
    public PaymentRepositoryImpl() {
        super(Payment.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<String> findPaymentKeyWithOrderNo(Long orderNo) {
        QOrder order = QOrder.order;
        QPayment payment = QPayment.payment;

        JPQLQuery<String> paymentKeyQuery = from(payment)
            .innerJoin(payment.order, order)
            .select(payment.paymentKey)
            .where(order.orderNo.eq(orderNo));

        return Optional.ofNullable(paymentKeyQuery.fetchOne());
    }
}
