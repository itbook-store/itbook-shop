package shop.itbook.itbookshop.ordergroup.ordersheet.controller;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.membergroup.memberdestination.dto.response.MemberDestinationResponseDto;
import shop.itbook.itbookshop.membergroup.memberdestination.repository.MemberDestinationRepository;
import shop.itbook.itbookshop.membergroup.memberdestination.service.MemberDestinationService;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetProductResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.dto.response.OrderSheetResponseDto;
import shop.itbook.itbookshop.ordergroup.ordersheet.resultmessageenum.OrderSheetMessageEnum;
import shop.itbook.itbookshop.ordergroup.ordersheet.transfer.OrderSheetTransfer;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;

/**
 * 주문서 작성에 관한 요청을 담당하고 처리하는 컨트롤러
 *
 * @author 정재원
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order-sheet")
public class OrderSheetController {
    private final ProductAdminService productAdminService;
    private final MemberDestinationService memberDestinationService;

    /**
     * 회원의 개별 주문서 작성 처리 로직을 담당하는 컨트롤러입니다.
     *
     * @param productNo 주문하려는 제품의 번호
     * @param memberNo  회원의 번호
     * @return 주문서 작성을 위한 정보가 담긴 Dto
     * @author 정재원 *
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<OrderSheetResponseDto>> orderWrite(
        @RequestParam("ProductNo") Long productNo, @RequestParam("memberNo") Long memberNo) {

        List<OrderSheetProductResponseDto> orderSheetProductResponseDtoList = new ArrayList<>();

        Product product = productAdminService.findProduct(productNo);

        orderSheetProductResponseDtoList.add(
            OrderSheetTransfer.productEntityToOrderSheetResponseDto(product));

        List<MemberDestinationResponseDto> memberDestinationResponseDtoList =
            memberDestinationService.findMemberDestinationResponseDtoByMemberNo(memberNo);

        CommonResponseBody commonResponseBody =
            new CommonResponseBody(new CommonResponseBody.CommonHeader(
                OrderSheetMessageEnum.ORDER_SHEET_FIND_INFO_SUCCESS_MESSAGE.getSuccessMessage()),
                OrderSheetTransfer.createOrderSheetResponseDto(orderSheetProductResponseDtoList,
                    memberDestinationResponseDtoList));

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
