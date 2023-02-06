package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.deliverygroup.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListViewResponseDto;
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
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<OrderListViewResponseDto>>> orderListFind(
        @PageableDefault
        Pageable pageable, @RequestParam Long memberNo) {

        PageResponse<OrderListViewResponseDto> pageResponse =
            new PageResponse<>(orderService.getOrderListOfMemberWithStatus(pageable, memberNo));

        CommonResponseBody<PageResponse<OrderListViewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_LIST_OF_MEMBER_WITH_STATUS_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), pageResponse
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    /**
     * Order add response entity.
     *
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<Void>> orderAdd() {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}