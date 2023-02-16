package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderPaymentDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListMemberViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;

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
            new PageResponse<>(orderService.findOrderListOfMemberWithStatus(pageable, memberNo));

        CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_LIST_OF_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), pageResponse
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 비회원 주문 조회 요청을 처리합니다.
     *
     * @param pageable the pageable
     * @param orderNo  주문 조회할 주문 번호
     * @return 비회원이 주문한 건의 상세 정보 Dto 를 담은 응답 객체
     */
// TODO: 2023/02/10 비회원 주문조회
    public ResponseEntity<CommonResponseBody<Void>> orderNonMemberList(
        @PageableDefault Pageable pageable, @PathVariable("orderNo") Long orderNo
    ) {

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_LIST_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), null
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
        @RequestBody OrderAddRequestDto orderAddRequestDto) {

        Optional<Long> optMemberNo = Optional.empty();

        if (Objects.nonNull(memberNo)) {
            optMemberNo = Optional.of(memberNo);
        }

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.addOrderBeforePayment(orderAddRequestDto, optMemberNo)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 주문의 결제 재진행을 요청을 처리합니다.
     *
     * @param orderNo            주문 번호
     * @param orderAddRequestDto 주문시 작성한 정보를 담은 Dto
     * @return 주문 추가 후 결제를 위한 정보를 담은 응답 객체
     * @author 정재원 *
     */
    @PostMapping("/re-order/{orderNo}")
    public ResponseEntity<CommonResponseBody<OrderPaymentDto>> reOrder(
        @PathVariable("orderNo") Long orderNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto) {

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.reOrderBeforePayment(orderAddRequestDto, orderNo)
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
    public ResponseEntity<CommonResponseBody<OrderPaymentDto>> orderSubscriptionBeforePayment(
        @RequestParam(value = "memberNo", required = false) Long memberNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto) {

        Optional<Long> optMemberNo = Optional.empty();

        if (Objects.nonNull(memberNo)) {
            optMemberNo = Optional.of(memberNo);
        }

        CommonResponseBody<OrderPaymentDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_ADD_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.addOrderSubscriptionBeforePayment(orderAddRequestDto, optMemberNo)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 구독 주문 완료 후 결제 정보 저장 요청을 받아 처리합니다.
     *
     * @param orderNo 주문 번호(null 일 경우 비회원)
     * @return 응답 객체
     * @author 정재원
     */
    @PostMapping("/subscription/completion")
    public ResponseEntity<CommonResponseBody<Void>> orderSubscriptionAfterPayment(
        @RequestParam("orderNo") Long orderNo) {

        orderService.addOrderSubscriptionAfterPayment(orderNo);

        return ResponseEntity.ok().build();
    }

    /**
     * 결제 전 주문 취소 요청을 처리합니다.
     *
     * @param orderNo 취소 처리할 주문 번호
     * @return 성공시 ok 응답 객체
     * @author 정재원 *
     */
    @PostMapping("/cancel/{orderNo}")
    public ResponseEntity<CommonResponseBody<Void>> orderCancelBeforePayment(
        @PathVariable("orderNo") Long orderNo) {

        orderService.processBeforeOrderCancelPayment(orderNo);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                OrderResultMessageEnum.ORDER_SHEET_SUCCESS_MESSAGE.getResultMessage()
            ), null
        );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Order details response entity.
     *
     * @param orderNo the order no
     * @return the response entity
     * @author 정재원 *
     */
    @GetMapping("/details/{orderNo}")
    public ResponseEntity<CommonResponseBody<OrderDetailsResponseDto>> orderDetails(
        @PathVariable("orderNo") Long orderNo) {

        CommonResponseBody<OrderDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.findOrderDetails(orderNo)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 주문 구매 확정뱐경 메서드 입니다.
     *
     * @param orderNo 주문번호
     * @return 공용응답객체
     * @author 강명관
     */
    @PostMapping("/purchase-complete/{orderNo}")
    public ResponseEntity<CommonResponseBody<Void>> orderStatusChangePurchaseComplete(
        @PathVariable(value = "orderNo") Long orderNo
    ) {
        orderService.orderPurchaseComplete(orderNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), null
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}