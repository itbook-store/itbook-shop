package shop.itbook.itbookshop.ordergroup.order.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderListAdminViewResponseDto;
import shop.itbook.itbookshop.ordergroup.order.service.OrderService;

/**
 * 관리자의 주문 엔티티와 관련된 요청을 받아 처리합니다.
 *
 * @author 정재원
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
public class OrderAdminController {

    private final OrderService orderService;

    public ResponseEntity<CommonResponseBody<PageResponse<OrderListAdminViewResponseDto>>> orderListAdmin() {

        // todo won : 주문 리스트 조회 서비스 호출 추가.

        return ResponseEntity.ok().body(null);
    }
}
