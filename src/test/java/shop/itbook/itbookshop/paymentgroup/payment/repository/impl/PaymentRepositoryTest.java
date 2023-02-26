package shop.itbook.itbookshop.paymentgroup.payment.repository.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.paymentgroup.PaymentDummy;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.repository.CardRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.repository.EasypayRepository;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepository;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.repository.PaymentCancelRepository;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.repository.PaymentStatusRepository;

/**
 * @author 이하늬
 * @since 1.0
 */
@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    EasypayRepository easypayRepository;

    @Autowired
    PaymentStatusRepository paymentStatusRepository;

    @Autowired
    PaymentCancelRepository paymentCancelRepository;
    Payment payment;
    PaymentCancel paymentCancel;
    Card card;
    Order order;

    @BeforeEach
    void setUp() {
        payment = PaymentDummy.getPayment();
        order = OrderDummy.getOrder();
        card = PaymentDummy.getCard();
        Easypay easypay = PaymentDummy.getEasypay();
        PaymentStatus paymentStatus = PaymentDummy.getPaymentStatus();
        paymentCancel = PaymentDummy.getPaymentCancel();

        Order savedOrder = orderRepository.save(order);
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);
        Card savedCard = cardRepository.save(card);
        Easypay savedEasypay = easypayRepository.save(easypay);

        payment.setOrder(savedOrder);
        payment.setCard(savedCard);
        payment.setEasypay(savedEasypay);
        payment.setPaymentStatus(savedPaymentStatus);

        Payment savedPayment = paymentRepository.save(payment);

        paymentCancel.setPayment(savedPayment);
        paymentCancel.setPaymentNo(savedPayment.getPaymentNo());
        paymentCancelRepository.save(paymentCancel);


        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findPaymentKeyWithOrderNo() {
        Optional<String> paymentKeyWithOrderNo =
            paymentRepository.findPaymentKeyWithOrderNo(payment.getOrder().getOrderNo());

        assertThat(paymentKeyWithOrderNo.get()).isEqualTo(payment.getPaymentKey());

    }

    @Test
    void findPaymentCardByOrderNo() {
        PaymentCardResponseDto paymentCardByOrderNo =
            paymentRepository.findPaymentCardByOrderNo(order.getOrderNo());

        assertThat(paymentCardByOrderNo.getCardSerialNo()).isEqualTo(card.getCardSerialNo());

    }

    @Test
    void findPaymentByOrder_OrderNo() {
        Optional<Payment> paymentByOrder_orderNo =
            paymentRepository.findPaymentByOrder_OrderNo(payment.getOrder().getOrderNo());

        assertThat(paymentByOrder_orderNo.get().getPaymentKey()).isEqualTo(payment.getPaymentKey());
    }
}