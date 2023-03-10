package shop.itbook.itbookshop.paymentgroup.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderService;
import shop.itbook.itbookshop.ordergroup.order.service.orderafterpaymentsuccess.OrderAfterPaymentSuccessEnum;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentcancel.OrderBeforePaymentCancelEnum;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;
import shop.itbook.itbookshop.paymentgroup.card.service.CardService;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;
import shop.itbook.itbookshop.paymentgroup.easypay.service.EasypayService;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentApproveRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.request.PaymentCanceledRequestDto;
import shop.itbook.itbookshop.paymentgroup.payment.dto.response.OrderResponseDto;
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
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.service.PaymentStatusService;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;

/**
 * The type Payment service.
 *
 * @author ????????? * @since 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final PayService tossPayService;
    private final CardService cardService;
    private final EasypayService easypayService;
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
                                           String orderType, Long orderNo, HttpSession session) {

        if (paymentApproveRequestDto.getAmount() < 100L) {
            throw new InvalidPaymentException("100??? ????????? ????????? ??????????????????.");
        }

        PaymentResponseDto.PaymentDataResponseDto response =
            tossPayService.requestApprovePayment(paymentApproveRequestDto);

        Payment payment = PaymentTransfer.dtoToEntity(response);

        if (Objects.nonNull(response.getCard())) {
            Card card = cardService.addCard(response);
            payment.setCard(card);
        }

        if (Objects.nonNull(response.getEasyPay())) {
            Easypay easypay = easypayService.addEasyPay(response);
            payment.setEasypay(easypay);
        }

        if (!Objects.equals(payment.getTotalAmount(), paymentApproveRequestDto.getAmount())) {
            throw new InvalidPaymentException("????????? ??????????????? ?????? ???????????????. ?????? ?????? ?????? ??????!");
        }

        PaymentStatus paymentStatus =
            paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.DONE);
        payment.setPaymentStatus(paymentStatus);

        OrderAfterPaymentSuccessEnum orderAfterPaymentSuccessEnum
            = OrderAfterPaymentSuccessEnum.stringToOrderFactoryEnum(orderType);

        Order order =
            orderService.processOrderAfterPaymentSuccess(
                orderAfterPaymentSuccessEnum, orderNo);
//        Order order = orderService.processAfterOrderPaymentSuccess(orderNo);
        payment.setOrder(order);

        try {
            paymentRepository.save(payment);
        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

//        if (orderService.isSubscription(orderNo)) {
//            orderService.addOrderSubscriptionAfterPayment(orderNo);
//        }

        return new OrderResponseDto(payment.getOrder().getOrderNo(), payment.getTotalAmount());
    }


    @Override
    @Transactional
    public OrderResponseDto cancelPayment(PaymentCanceledRequestDto paymentCanceledRequestDto,
                                          String orderType)
        throws JsonProcessingException {

        String paymentKey = this.findPaymentKey(paymentCanceledRequestDto.getOrderNo());

        PaymentResponseDto.PaymentDataResponseDto response;
        Payment payment;

        OrderBeforePaymentCancelEnum orderBeforePaymentCancelEnum
            = OrderBeforePaymentCancelEnum.stringToOrderFactoryEnum(orderType);

        // ?????? ?????? ??????
//        orderService.processBeforeOrderCancelPayment(
//            paymentCanceledRequestDto.getOrderNo());
        orderService.processOrderBeforePaymentCancel(
            paymentCanceledRequestDto.getOrderNo(), orderBeforePaymentCancelEnum);

        response = tossPayService.requestCanceledPayment(paymentCanceledRequestDto, paymentKey);

        // ?????? ????????? ?????? ????????? ??????
        payment = findPaymentByOrderNo(paymentCanceledRequestDto.getOrderNo());
        PaymentStatus paymentStatusEntity =
            paymentStatusService.findPaymentStatusEntity(PaymentStatusEnum.CANCELED);
        payment.setPaymentStatus(paymentStatusEntity);

        try {
            paymentRepository.save(payment);
            // ?????? ?????? ???????????? ?????? ????????? ??????
            paymentCancelService.addPaymentCancel(payment, response);

        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return new OrderResponseDto(payment.getOrder().getOrderNo(),
            payment.getTotalAmount());
    }

    private Payment findPaymentByOrderNo(Long orderNo) {
        return paymentRepository.findPaymentByOrder_OrderNo(orderNo)
            .orElseThrow(InvalidPaymentException::new);
    }

    /**
     * @param orderNo the order no
     * @return the payment card response dto
     * @author ?????????
     */
    public PaymentCardResponseDto findPaymentCardInfo(Long orderNo) {
        return paymentRepository.findPaymentCardByOrderNo(orderNo);
    }

}
