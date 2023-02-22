package shop.itbook.itbookshop.ordergroup.order.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionAdminListDto;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.service.impl.OrderService;

/**
 * 관리자의 주문 엔티티와 관련된 요청을 받아 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/admin/orders")
public class OrderAdminController {

    private final OrderService orderService;

    /**
     * 관리자의 주문 목록 조회 요청을 받아 처리하는 합니다.
     *
     * @return 응답 객체
     * @author 정재원 *
     */
    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<PageResponse<OrderListAdminViewResponseDto>>> orderListAdmin(
        @PageableDefault Pageable pageable) {

        CommonResponseBody<PageResponse<OrderListAdminViewResponseDto>> response =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                OrderResultMessageEnum.ORDER_LIST_OF_ADMIN_FIND_SUCCESS_MESSAGE.getResultMessage()),
                new PageResponse<>(orderService.findOrderListAdmin(pageable)));

        return ResponseEntity.ok().body(response);
    }


    /**
     * 관리자페이지에서 구독 주문 목록 리스트 반환 메서드 입니다.
     *
     * @param pageable 페이징 객체
     * @return 페이징된 구독 주문 목록 DTO
     * @author 강명관
     */
    @GetMapping("/list/subscription")
    public ResponseEntity<CommonResponseBody<PageResponse<OrderSubscriptionAdminListDto>>> orderSubscriptionListByAdmin(
        @PageableDefault Pageable pageable
    ) {

        Page<OrderSubscriptionAdminListDto> allSubscriptionOrderListByAdmin =
            orderService.findAllSubscriptionOrderListByAdmin(pageable);

        CommonResponseBody<PageResponse<OrderSubscriptionAdminListDto>> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                OrderResultMessageEnum.ORDER_SUBSCRIPTION_LIST_OF_ADMIN_SUCCESS_MESSAGE.getResultMessage()
            ), new PageResponse<>(allSubscriptionOrderListByAdmin)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }


}
