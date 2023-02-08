package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum.OrderSheetMessageEnum;
import shop.itbook.itbookshop.ordergroup.ordersheet.transfer.OrderSheetTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * 주문서 작성에 관한 요청을 담당하고 처리하는 컨트롤러
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
        @RequestParam("productNo") List<Long> productNoList,
        @RequestParam("productCnt") List<Integer> productCntList,
        @RequestParam("memberNo") String memberNo) {

        productService.checkSellProductList(productNoList, productCntList);

        List<ProductDetailsResponseDto> orderSheetProductResponseDtoList = new ArrayList<>();

        productNoList.stream().map(productNo -> )

        orderSheetProductResponseDtoList.add(product);

        List<MemberDestinationResponseDto> memberDestinationResponseDtoList =
            memberDestinationService.findMemberDestinationResponseDtoByMemberNo(memberNo);

        CommonResponseBody<OrderSheetResponseDto> commonResponseBody =
            new CommonResponseBody(new CommonResponseBody.CommonHeader(
                OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage()),
                OrderSheetTransfer.createOrderSheetResponseDto(orderSheetProductResponseDtoList,
                    memberDestinationResponseDtoList));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
