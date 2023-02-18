package shop.itbook.itbookshop.elastic.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.elastic.document.SearchProductBook;
import shop.itbook.itbookshop.elastic.service.ProductBookSearchService;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductSearchResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;

/**
 * @author 송다혜
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class ProductBookSearchController {
    private final ProductBookSearchService productBookSearchService;
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<CommonResponseBody<List<SearchProductBook>>>
    getProductSearchByName(@RequestParam String name) {

        CommonResponseBody<List<SearchProductBook>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductSearchResultMessageEnum.PRODUCT_SEARCH_SUCCESS.getMessage()),
                productBookSearchService.findByName(name));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
//
//    @DeleteMapping("/{productNo}")
//    public ResponseEntity<CommonResponseBody<Void>> productRemove(@PathVariable Long productNo) {
//        productBookSearchService.removeSearchProduct(productNo);
//
//        CommonResponseBody<Void> commonResponseBody =
//            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
//                ProductResultMessageEnum.DELETE_SUCCESS.getMessage()), null);
//
//        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
//    }

    @PostMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<ProductNoResponseDto>> productAdd(@PathVariable Long productNo) {

        Product product = productService.findProductEntity(productNo);
        ProductNoResponseDto productNoResponseDto =
            new ProductNoResponseDto(productBookSearchService.addSearchProduct(
                product));

        CommonResponseBody<ProductNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
