package shop.itbook.itbookshop.productgroup.productinquiry.controller.serviceapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.request.ProductInquiryRequestDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryNoResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.resultmessageenum.ProductInquiryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;

/**
 * 상품문의 서비스 api 컨트롤러 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/product-inquiries")
@RequiredArgsConstructor
public class ProductInquiryController {

    private final ProductInquiryService productInquiryService;

    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ProductInquiryNoResponseDto>> productInquiryAdd(
        @RequestBody ProductInquiryRequestDto productInquiryRequestDto) {

        ProductInquiryNoResponseDto productInquiryNoResponseDto =
            new ProductInquiryNoResponseDto(
                productInquiryService.addProductInquiry(productInquiryRequestDto));

        CommonResponseBody<ProductInquiryNoResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductInquiryResultMessageEnum.PRODUCT_INQUIRY_ADD_SUCCESS.getResultMessage()),
                productInquiryNoResponseDto
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    @GetMapping("/writable/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryOrderProductResponseDto>>> productInquiryOrderProductList(
        @PathVariable("memberNo") Long memberNo, @PageableDefault Pageable pageable) {

        Page<ProductInquiryOrderProductResponseDto> productInquiryOrderProductList =
            productInquiryService.findProductInquiryOrderProductList(pageable, memberNo);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            ProductInquiryResultMessageEnum.PRODUCT_INQUIRY_ORDER_PRODUCT_LIST_GET_SUCCESS.getResultMessage()),
            new PageResponse<>(productInquiryOrderProductList)));
    }

    @GetMapping("/view/{productInquiryNo}")
    public ResponseEntity<CommonResponseBody<ProductInquiryResponseDto>> productInquiryDetails(
        @PathVariable("productInquiryNo") Long productInquiryNo) {

        CommonResponseBody<ProductInquiryResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductInquiryResultMessageEnum.PRODUCT_INQUIRY_GET_SUCCESS.getResultMessage()),
            productInquiryService.findProductInquiry(productInquiryNo));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
