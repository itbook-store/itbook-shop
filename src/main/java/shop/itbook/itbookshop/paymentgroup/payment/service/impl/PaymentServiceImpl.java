package shop.itbook.itbookshop.paymentgroup.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.service.CardService;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentCancelException;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidPaymentException;
import shop.itbook.itbookshop.paymentgroup.payment.repository.PaymentRepository;
import shop.itbook.itbookshop.paymentgroup.payment.service.PayService;
import shop.itbook.itbookshop.paymentgroup.payment.service.PaymentService;
import shop.itbook.itbookshop.paymentgroup.payment.transfer.PaymentTransfer;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.service.PaymentCancelService;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.service.PaymentStatusService;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * @author 이하늬
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final PayService payService;
    private final CardService cardService;
    private final PaymentCancelService paymentCancelService;
    private final PaymentStatusService paymentStatusService;
    private final OrderService orderService;

    @Override
    public String findPaymentKey(Long orderNo) {
        return paymentRepository.findPaymentKeyWithOrderNo(orderNo)
            .orElseThrow(InvalidOrderException::new);
    }

    @Override
    @Transactional
    public OrderResponseDto requestPayment(PaymentApproveRequestDto paymentApproveRequestDto,
                                           Long orderNo, HttpSession session) {

        PaymentResponseDto.PaymentDataResponseDto response;
        Payment payment;

        try {
            response = payService.requestApprovePayment(paymentApproveRequestDto);

            payment = PaymentTransfer.dtoToEntity(response);
            if (!Objects.isNull(response.getCard())) {
                Card card = cardService.addCard(response);
                payment.setCard(card);
            }

            PaymentStatus paymentStatus =
                paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.DONE);
            payment.setPaymentStatus(paymentStatus);

            Order order = orderService.processAfterOrderPaymentSuccess(orderNo);
            payment.setOrder(order);

            paymentRepository.save(payment);

            if (orderService.isSubscription(orderNo)) {
                orderService.addOrderSubscriptionAfterPayment(orderNo);
            }
        } catch (InvalidPaymentException e) {
            throw new InvalidPaymentException(e.getMessage());
        }
        return new OrderResponseDto(payment.getOrder().getOrderNo(), payment.getTotalAmount());
    }

    @Override
    @Transactional
    public OrderResponseDto cancelPayment(PaymentCanceledRequestDto paymentCanceledRequestDto)
        throws JsonProcessingException {

        String paymentKey = this.findPaymentKey(paymentCanceledRequestDto.getOrderNo());

        PaymentResponseDto.PaymentDataResponseDto response;
        Payment payment;
        try {
            response = payService.requestCanceledPayment(paymentCanceledRequestDto, paymentKey);

            // 결제 상태를 결제 취소로 수정
            payment = findPaymentByOrderNo(paymentCanceledRequestDto.getOrderNo());
            PaymentStatus paymentStatusEntity =
                paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.CANCELED);
            payment.setPaymentStatus(paymentStatusEntity);
            paymentRepository.save(payment);

            // 결제 취소 테이블에 취소 데이터 등록
            paymentCancelService.addPaymentCancel(payment, response);

        } catch (InvalidPaymentCancelException e) {
            throw new InvalidPaymentException(e.getMessage());
        }
        return new OrderResponseDto(payment.getOrder().getOrderNo(),
            payment.getTotalAmount());
    }

    private Payment findPaymentByOrderNo(Long orderNo) {
        return paymentRepository.findPaymentByOrder_OrderNo(orderNo)
            .orElseThrow(InvalidPaymentException::new);
    }

    public PaymentCardResponseDto findPaymentCardInfo(Long orderNo) {
        return paymentRepository.findPaymentCardByOrderNo(orderNo);
    }

}
