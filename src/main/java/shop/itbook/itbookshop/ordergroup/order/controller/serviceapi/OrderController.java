package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.deliverygroup.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderAddRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderAddResponseDto;
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
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 목록을 여러 정보와 함께 조회 합니다.
     * 프론트에서 요청받은 memberNo 를 바탕으로 해당 회원의 리스트를 반환합니다.
     *
     * @param pageable 보여줄 페이지 정보
     * @param memberNo 회원 번호
     * @return 주문 정보를 담은 Dto 리스트를 담은 페이지 객체
     * @author 정재원 *
     */
    @GetMapping("/list/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>>> orderListFind(
        @PageableDefault
        Pageable pageable, @PathVariable("memberNo") Long memberNo) {

        PageResponse<OrderListMemberViewResponseDto> pageResponse =
            new PageResponse<>(orderService.findOrderListOfMemberWithStatus(pageable, memberNo));

        CommonResponseBody<PageResponse<OrderListMemberViewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_LIST_OF_MEMBER_WITH_STATUS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), pageResponse
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * 주문 데이터를 추가합니다.
     *
     * @param memberNo           the member no
     * @param orderAddRequestDto the order add request dto
     * @return 추가한 결과를 담은 응답 객체
     * @author 정재원 *
     */
    @PostMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<OrderAddResponseDto>> orderAdd(
        @PathVariable("memberNo") Long memberNo,
        @RequestBody OrderAddRequestDto orderAddRequestDto) {

        CommonResponseBody<OrderAddResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_SHEET_SUCCESS_MESSAGE.getResultMessage()
                ), orderService.addOrderOfMember(orderAddRequestDto, memberNo)
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * Order pay completion response entity.
     *
     * @param orderNo the order no
     * @return the response entity
     * @author 정재원 *
     */
    @PostMapping("/pay-completion/{orderNo}")
    public ResponseEntity<CommonResponseBody<Void>> orderPayCompletion(
        @PathVariable("orderNo") Long orderNo) {

        orderService.completeOrderPay(orderNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_PAY_SUCCESS_MESSAGE.getResultMessage()
                ), null
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}