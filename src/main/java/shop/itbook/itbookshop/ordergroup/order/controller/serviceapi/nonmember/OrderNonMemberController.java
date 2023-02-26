package shop.itbook.itbookshop.ordergroup.order.controller.serviceapi.nonmember;

import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderSubscriptionDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.resultemessageenum.OrderResultMessageEnum;
import shop.itbook.itbookshop.ordergroup.order.service.base.nonmember.OrderNonMemberService;

/**
 * @author 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Validated
@Slf4j
public class OrderNonMemberController {

    private final OrderNonMemberService orderNonMemberService;

    /**
     * 비회원 주문 조회 요청을 처리합니다.
     *
     * @param orderNo 주문 조회할 주문 번호
     * @return 비회원이 주문한 건의 상세 정보 Dto 를 담은 응답 객체
     */
    // TODO: 2023/02/10 비회원 주문조회
    @GetMapping("/details/{orderNo}/non-member")
    public ResponseEntity<CommonResponseBody<OrderDetailsResponseDto>> orderNonMemberDetails(
        @PathVariable("orderNo") Long orderNo,
        @RequestParam @Length(min = 1, max = 36) @NotBlank String orderCode) {
        // TODO : 테스트 끝나면 윗줄 min 36 바꿔야함

        OrderDetailsResponseDto orderDetailsResponseDto =
            orderNonMemberService.findNonMemberOrderDetails(orderNo, orderCode);

        CommonResponseBody<OrderDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), orderDetailsResponseDto
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }

    @GetMapping("/details/{orderNo}/non-member/subscription")
    public ResponseEntity<CommonResponseBody<List<OrderSubscriptionDetailsResponseDto>>> orderNonMemberDetailsSubscription(
        @PathVariable("orderNo") Long orderNo,
        @RequestParam @Length(min = 1, max = 36) @NotBlank String orderCode) {
        // TODO : 테스트 끝나면 윗줄 min 36 바꿔야함

        List<OrderSubscriptionDetailsResponseDto> orderSubscriptionDetailsResponseDtoList =
            orderNonMemberService.findNonMemberSubscriptionOrderDetails(orderNo, orderCode);

        CommonResponseBody<List<OrderSubscriptionDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    OrderResultMessageEnum.ORDER_DETAILS_OF_NON_MEMBER_FIND_SUCCESS_MESSAGE.getResultMessage()
                ), orderSubscriptionDetailsResponseDtoList
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
