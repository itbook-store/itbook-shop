package shop.itbook.itbookshop.productgroup.productinquiryreply.controller.adminapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request.ProductInquiryReplyRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyNoResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.resultmessageenum.ProductInquiryReplyResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productinquiryreply.service.ProductInquiryReplyService;

/**
 * 상품문의 답글 어드민 api 컨트롤러 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/product-inquiries/reply")
@RequiredArgsConstructor
public class ProductInquiryReplyAdminController {

    private final ProductInquiryReplyService productInquiryReplyService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ProductInquiryReplyNoResponseDto>> productInquiryReplyAdd(
        @RequestBody ProductInquiryReplyRequestDto productInquiryReplyRequestDto) {

        ProductInquiryReplyNoResponseDto productInquiryReplyNoResponseDto =
            new ProductInquiryReplyNoResponseDto(
                productInquiryReplyService.addProductInquiryReply(productInquiryReplyRequestDto));

        CommonResponseBody<ProductInquiryReplyNoResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductInquiryReplyResultMessageEnum.PRODUCT_INQUIRY_REPLY_ADD_SUCCESS.getResultMessage()),
                productInquiryReplyNoResponseDto
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
