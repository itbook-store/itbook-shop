package shop.itbook.itbookshop.productgroup.productinquiry.controller.adminapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.ProductInquiryRepository;
import shop.itbook.itbookshop.productgroup.productinquiry.resultmessageenum.ProductInquiryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productinquiry.service.ProductInquiryService;

/**
 * 상품문의 어드민 api 컨트롤러입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/product-inquiries")
@RequiredArgsConstructor
public class ProductInquiryAdminController {
    private final ProductInquiryRepository productInquiryRepository;

    private final ProductInquiryService productInquiryService;


    /**
     * 상품문의 리스트를 페이징처리하여 반환하는 api 입니다.
     *
     * @param pageable 반환할때 리스트를 페이징처리합니다.
     * @return 페이징처리한 dto 리스트를 commonResponseBody에 담아 보냅니다.
     * @author 노수연
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<ProductInquiryResponseDto>>> productInquiryList(
        @PageableDefault Pageable pageable) {

        Page<ProductInquiryResponseDto> productInquiryList =
            productInquiryService.findProductInquiryList(pageable);

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            ProductInquiryResultMessageEnum.PRODUCT_INQUIRY_LIST_GET_SUCCESS.getResultMessage()),
            new PageResponse<>(productInquiryList)));
    }


    /**
     * 전체 상품문의 카운트 수와 답변된 상품문의 카운트 수를 dto로 반환하는 api 입니다.
     *
     * @return dto를 commonResponseBody에 담아 보냅니다.
     * @author 노수연
     */
    @GetMapping("/count")
    public ResponseEntity<CommonResponseBody<ProductInquiryCountResponseDto>> productInquiryCount() {

        return ResponseEntity.ok(new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
            ProductInquiryResultMessageEnum.PRODUCT_INQUIRY_COUNT_GET_SUCCESS.getResultMessage()),
            productInquiryService.countProductInquiry()));
    }
}
