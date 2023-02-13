package shop.itbook.itbookshop.productgroup.productinquiryreply.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.resultmessageenum.ProductInquiryReplyResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productinquiryreply.service.ProductInquiryReplyService;

/**
 * 상품문의 답글 rest api 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/product-inquiries/reply")
@RequiredArgsConstructor
public class ProductInquiryReplyController {

    private final ProductInquiryReplyService productInquiryReplyService;

    @GetMapping("/view/{productInquiryNo}")
    public ResponseEntity<CommonResponseBody<List<ProductInquiryReplyResponseDto>>> productInquiryReplyDetails(
        @PathVariable("productInquiryNo") Long productInquiryNo) {

        CommonResponseBody<List<ProductInquiryReplyResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductInquiryReplyResultMessageEnum.PROCUCT_INQUIRY_LIST_GET_SUCCESS.getResultMessage()),

                productInquiryReplyService.findProductInquiryReply(productInquiryNo));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
