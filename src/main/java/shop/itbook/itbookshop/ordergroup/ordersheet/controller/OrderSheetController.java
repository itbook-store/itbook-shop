package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum.OrderSheetMessageEnum;
import shop.itbook.itbookshop.ordergroup.ordersheet.transfer.OrderSheetTransfer;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.find.commonapi.PointHistoryCommonService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * 주문서 작성에 관한 요청을 담당하고 처리하는 컨트롤러.
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-sheet")
@Slf4j
public class OrderSheetController {
    private final ProductService productService;
    private final MemberDestinationService memberDestinationService;
    private final PointHistoryCommonService pointHistoryCommonService;
    private final MemberService memberService;

    /**
     * 회원의 주문서 작성을 처리하는 컨트롤러입니다.
     *
     * @param productNoList  주문하려는 제품의 번호 리스트
     * @param productCntList 주문하려는 제품의 개수 리스트
     * @param memberNo       회원의 번호
     * @return 주문서 작성을 위한 정보가 담긴 Dto
     * @author 정재원 *
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<OrderSheetResponseDto>> orderSheet(
        @RequestParam("productNoList") List<Long> productNoList,
        @RequestParam("productCntList") List<Integer> productCntList,
        @RequestParam(value = "memberNo", required = false) Long memberNo) {

        productService.checkSellProductList(productNoList, productCntList);

        List<ProductDetailsResponseDto> productDetailsResponseDtoList =
            productNoList.stream().map(productService::findProduct).collect(
                Collectors.toList());

        List<MemberDestinationResponseDto> memberDestinationResponseDtoList = new ArrayList<>();
        long memberPoint = 0;

        if (Objects.nonNull(memberNo)) {
            memberDestinationResponseDtoList =
                memberDestinationService.findMemberDestinationResponseDtoByMemberNo(memberNo);
            memberPoint = pointHistoryCommonService.findRecentlyPoint(
                memberService.findMemberByMemberNo(memberNo));
        }

        OrderSheetResponseDto orderSheetResponseDto =
            OrderSheetTransfer.createOrderSheetResponseDto(productDetailsResponseDtoList,
                memberDestinationResponseDtoList, memberPoint);

        CommonResponseBody<OrderSheetResponseDto> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage()),
                orderSheetResponseDto
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
