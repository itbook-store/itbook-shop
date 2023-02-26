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
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderCrudService;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;
import shop.itbook.itbookshop.ordergroup.order.service.orderbeforepaymentenum.OrderBeforePaymentFactoryEnum;
import shop.itbook.itbookshop.paymentgroup.payment.exception.InvalidOrderException;

/**
 * 주문과 관련된 요청을 처리하는 컨트롤러 입니다.
 *
 * @author 정재원
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
     * 주문 목록을 여러 정보와 함께 조회 합니다.
     * 프론트에서 요청받은 memberNo 를 바탕으로 해당 회원의 리스트를 반환합니다.
     *
     * @param pageable 보여줄 페이지 정보
     * @param memberNo 회원 번호
     * @return 주문 정보를 담은 Dto 리스트를 담은 페이지 객체
     * @author 정재원
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
     * 결제 전, 주문 데이터 등록의 요청을 받아 처리합니다.
     *
     * @param memberNo           회원 번호(null 일 경우 비회원)
     * @param orderAddRequestDto 주문시 작성한 정보를 담은 Dto
     * @return 주문 추가 후 결제를 위한 정보를 담은 응답 객체
     * @author 정재원
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<OrderPaymentDto>> orderAddBeforePayment(
        @RequestParam(value = "memberNo", required = false) Long memberNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto, HttpServletRequest request) {

        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum;
        if (Objects.isNull(memberNo)) {
            orderBeforePaymentFactoryEnum = OrderBeforePaymentFactoryEnum.일반비회원주문;
        } else {
            orderBeforePaymentFactoryEnum = OrderBeforePaymentFactoryEnum.일반회원주문;
        }

        InfoForProcessOrderBeforePayment
            infoForProcessOrderBeforePayment = new InfoForProcessOrderBeforePayment(memberNo);
        infoForProcessOrderBeforePayment.setOrderAddRequestDto(orderAddRequestDto);

        Optional<Long> optMemberNo = Optional.empty();

        if (Objects.nonNull(memberNo)) {
            optMemberNo = Optional.of(memberNo);
        }

//        orderService.addOrderBeforePayment(orderBeforePayment,
//            new InfoForPrePaymentProcess(orderAddRequestDto, memberNo));

//        CommonResponseBody<OrderPaymentDto> commonResponseBody =
//            new CommonResponseBody<>(
//                new CommonResponseBody.CommonHeader(
//                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
//                ), orderService.addOrderBeforePayment(orderAddRequestDto, optMemberNo)
//            );

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.processOrderBeforePayment(
                infoForProcessOrderBeforePayment, orderBeforePaymentFactoryEnum)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 구독 주문의 요청을 받아 처리합니다.
     *
     * @param memberNo           회원 번호(null 일 경우 비회원)
     * @param orderAddRequestDto 주문시 작성한 정보를 담은 Dto
     * @return 주문 추가 후 결제를 위한 정보를 담은 응답 객체
     * @author 정재원
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

        OrderBeforePaymentFactoryEnum orderBeforePaymentFactoryEnum;
        if (Objects.isNull(memberNo)) {
            orderBeforePaymentFactoryEnum = OrderBeforePaymentFactoryEnum.구독비회원주문;
        } else {
            orderBeforePaymentFactoryEnum = OrderBeforePaymentFactoryEnum.구독회원주문;
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
                infoForProcessOrderBeforePayment, orderBeforePaymentFactoryEnum)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * Order details response entity.
     *
     * @param orderNo the order no
     * @return the response entity
     * @author 정재원
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
     * 주문 구독 상세 보기
     *
     * @param orderNo 구독 시작 주문 번호
     * @return 주문 구독 리스트
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
     * 주문 구매 확정뱐경 메서드 입니다.
     *
     * @param orderNo 주문번호
     * @return 공용응답객체 response entity
     * @author 강명관
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
     * 회원 구독 주문 목록 리스틑 반환 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @param memberNo 회원 번호
     * @return 페이징 처리된 해당 회원의 구독 주문 목록 DTO
     * @author 강명관
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