package shop.itbook.itbookshop.paymentgroup.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.service.CardService;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderNoResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentCardResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.PaymentResponseDto;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;
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
    public OrderNoResponseDto requestPayment(PaymentApproveRequestDto paymentApproveRequestDto,
                                             Long orderNo) {

        PaymentResponseDto.PaymentDataResponseDto response =
            payService.requestApprovePayment(paymentApproveRequestDto);

        Payment payment = PaymentTransfer.dtoToEntity(response);
        if (!Objects.isNull(response.getCard())) {
            Card card = cardService.addCard(response);
            payment.setCard(card);
        }

        PaymentStatus paymentStatus =
            paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.DONE);
        payment.setPaymentStatus(paymentStatus);

        Order order = orderService.completeOrderPay(orderNo);
        payment.setOrder(order);

        Payment savePayment = paymentRepository.save(payment);

        return new OrderNoResponseDto(savePayment.getOrder().getOrderNo());
    }

    @Override
    @Transactional
    public OrderNoResponseDto cancelPayment(PaymentCanceledRequestDto paymentCanceledRequestDto)
        throws JsonProcessingException {

        String paymentKey = this.findPaymentKey(paymentCanceledRequestDto.getOrderNo());

        PaymentResponseDto.PaymentDataResponseDto response =
            payService.requestCanceledPayment(paymentCanceledRequestDto, paymentKey);

        // 결제 상태를 결제 취소로 수정
        Payment payment = findPaymentByOrderNo(paymentCanceledRequestDto.getOrderNo());
        PaymentStatus paymentStatusEntity =
            paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.CANCELED);
        payment.setPaymentStatus(paymentStatusEntity);
        Payment savePayment = paymentRepository.save(payment);

        // 결제 취소 테이블에 취소 데이터 등록
        paymentCancelService.addPaymentCancel(payment, response);

        return new OrderNoResponseDto(savePayment.getOrder().getOrderNo());
    }

    private Payment findPaymentByOrderNo(Long orderNo) {
        return paymentRepository.findPaymentByOrder_OrderNo(orderNo)
            .orElseThrow(InvalidPaymentException::new);
    }

    public PaymentCardResponseDto findPaymentCardInfo(Long orderNo) {
        return paymentRepository.findPaymentCardByOrderNo(orderNo);
    }

}
