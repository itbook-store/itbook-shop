package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.InfoForProcessOrderBeforePayment;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionListDto;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.base.OrderService;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepayment.OrderBeforePaymentEnum;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;

/**
 * ????????? ????????? ????????? ???????????? ???????????? ?????????.
 *
 * @author ?????????
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderCrudService orderCrudService;

    /**
     * ?????? ????????? ?????? ????????? ?????? ?????? ?????????.
     * ??????????????? ???????????? memberNo ??? ???????????? ?????? ????????? ???????????? ???????????????.
     *
     * @param pageable ????????? ????????? ??????
     * @param memberNo ?????? ??????
     * @return ?????? ????????? ?????? Dto ???????????? ?????? ????????? ??????
     * @author ?????????
     */
    @GetMapping("/list/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>>> orderMemberListFind(
        @PageableDefault
        Pageable pageable, @PathVariable("memberNo") Long memberNo) {

        PageResponse<OrderListMemberViewResponseDto> pageResponse =
            new PageResponse<>(
                orderCrudService.findOrderListOfMemberWithStatus(pageable, memberNo));

        CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_LIST_OF_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), pageResponse
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }


    /**
     * ?????? ???, ?????? ????????? ????????? ????????? ?????? ???????????????.
     *
     * @param memberNo           ?????? ??????(null ??? ?????? ?????????)
     * @param orderAddRequestDto ????????? ????????? ????????? ?????? Dto
     * @return ?????? ?????? ??? ????????? ?????? ????????? ?????? ?????? ??????
     * @author ?????????
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<OrderPaymentDto>> orderAddBeforePayment(
        @RequestParam(value = "memberNo", required = false) Long memberNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto, HttpServletRequest request) {

        OrderBeforePaymentEnum orderBeforePaymentEnum;
        if (Objects.isNull(memberNo)) {
            orderBeforePaymentEnum = OrderBeforePaymentEnum.?????????????????????;
        } else {
            orderBeforePaymentEnum = OrderBeforePaymentEnum.??????????????????;
        }

        InfoForProcessOrderBeforePayment
            infoForProcessOrderBeforePayment = new InfoForProcessOrderBeforePayment(memberNo);
        infoForProcessOrderBeforePayment.setOrderAddRequestDto(orderAddRequestDto);
        

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.processOrderBeforePayment(
                infoForProcessOrderBeforePayment, orderBeforePaymentEnum)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * ?????? ????????? ????????? ?????? ???????????????.
     *
     * @param memberNo           ?????? ??????(null ??? ?????? ?????????)
     * @param orderAddRequestDto ????????? ????????? ????????? ?????? Dto
     * @return ?????? ?????? ??? ????????? ?????? ????????? ?????? ?????? ??????
     * @author ?????????
     */
    @PostMapping("/subscription")
    public ResponseEntity<CommonResponseBody<OrderPaymentDto>> subscriptionOrderBeforePayment(
        @RequestParam(value = "memberNo", required = false) Long memberNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto) {

        if (Objects.isNull(orderAddRequestDto.getIsSubscription())) {
            throw new InvalidOrderException();
        }

        InfoForProcessOrderBeforePayment
            infoForProcessOrderBeforePayment = new InfoForProcessOrderBeforePayment(memberNo);
        infoForProcessOrderBeforePayment.setOrderAddRequestDto(orderAddRequestDto);

        OrderBeforePaymentEnum orderBeforePaymentEnum;
        if (Objects.isNull(memberNo)) {
            orderBeforePaymentEnum = OrderBeforePaymentEnum.?????????????????????;
        } else {
            orderBeforePaymentEnum = OrderBeforePaymentEnum.??????????????????;
        }

//        CommonResponseBody<OrderPaymentDto> commonResponseBody =
//            new CommonResponseBody<>(
//                new CommonResponseBody.CommonHeader(
//                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
//                ), orderService.addOrderBeforePayment(orderAddRequestDto, Optional.of(memberNo))
//            );

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.processOrderBeforePayment(
                infoForProcessOrderBeforePayment, orderBeforePaymentEnum)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * Order details response entity.
     *
     * @param orderNo the order no
     * @return the response entity
     * @author ?????????
     */
    @GetMapping("/details/{orderNo}")
    public ResponseEntity<CommonResponseBody<OrderDetailsResponseDto>> orderDetails(
        @PathVariable("orderNo") Long orderNo) {

        CommonResponseBody<OrderDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), orderCrudService.findOrderDetails(orderNo)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * ?????? ?????? ?????? ??????
     *
     * @param orderNo ?????? ?????? ?????? ??????
     * @return ?????? ?????? ?????????
     */
    @GetMapping("/details-sub/{orderNo}")
    public ResponseEntity<CommonResponseBody<List<OrderSubscriptionDetailsResponseDto>>> orderSubscriptionDetails(
        @PathVariable("orderNo") Long orderNo) {

        CommonResponseBody<List<OrderSubscriptionDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_SUBSCRIPTION_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), orderCrudService.findOrderSubscriptionDetailsResponseDto(orderNo)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * ?????? ?????? ???????????? ????????? ?????????.
     *
     * @param orderNo ????????????
     * @return ?????????????????? response entity
     * @author ?????????
     */
    @PostMapping("/purchase-complete/{orderNo}")
    public ResponseEntity<CommonResponseBody<Void>> orderStatusChangePurchaseComplete(
        @PathVariable(value = "orderNo") Long orderNo
    ) {
        orderCrudService.orderPurchaseComplete(orderNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), null
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * ?????? ?????? ?????? ?????? ????????? ?????? ????????? ?????????.
     *
     * @param pageable ????????? ??????
     * @param memberNo ?????? ??????
     * @return ????????? ????????? ?????? ????????? ?????? ?????? ?????? DTO
     * @author ?????????
     */
    @GetMapping("/list/subscription/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<OrderSubscriptionListDto>>> orderSubscriptionListByMember(
        @PageableDefault Pageable pageable,
        @PathVariable(value = "memberNo") Long memberNo
    ) {

        Page<OrderSubscriptionListDto> allSubscriptionOrderListByMember =
            orderCrudService.findAllSubscriptionOrderListByMember(pageable, memberNo);

        CommonResponseBody<PageResponse<OrderSubscriptionListDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                OrderResultMessageEnum.ORDER_SUBSCRIPTION_LIST_OF_ADMIN_SUCCESS_MESSAGE.getResultMessage()
            ), new PageResponse<>(allSubscriptionOrderListByMember)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @DeleteMapping("/{orderNo}/with-stock-rollback")
    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> orderDeleteAndStockRollBack(
        @PathVariable Long orderNo) {

        orderCrudService.deleteOrderAndRollBackStock(orderNo);

        CommonResponseBody<SuccessfulResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                OrderResultMessageEnum.ORDER_DELETE_AND_STOCK_ROLL_BACK_SUCCESS_MESSAGE.getResultMessage()),
            new SuccessfulResponseDto(Boolean.TRUE));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}