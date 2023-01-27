package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.memberdestination.entity.MemberDestination;
import shop.itbook.itbookshop.ordergroup.order.dto.request.OrderProductRequestDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderWriteResponseDto;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

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
    private final ProductRepository productRepository;
    private final MemberDestination memberDestination;

    @GetMapping("/write")
    public ResponseEntity<CommonResponseBody<OrderWriteResponseDto>> orderWrite(@RequestBody
                                                                                OrderProductRequestDto orderProductRequestDto) {

        return null;
    }
}
