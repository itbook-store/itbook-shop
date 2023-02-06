package shop.itbook.itbookshop.productgroup.product.controller.elastic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductSearchResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * 상품을 검색하는 컨트롤러입니다.
 *
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/api/products/search")
@RequiredArgsConstructor
public class ProductElasticController {

    private final ProductSearchService productSearchService;
    private final ProductService productService;
    /**
     * 상품의 이름으로 상품 리스트를 가져오는 메소드입니다.
     *
     * @param name the name
     * @return the product
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<ProductSearchResponseDto>>>
        getProductSearchByName(@PageableDefault Pageable pageable, @RequestParam String name) {

        Page<ProductSearchResponseDto>
            product = productSearchService.searchProductByTitle(pageable, name);

        CommonResponseBody<PageResponse<ProductSearchResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductSearchResultMessageEnum.PRODUCT_SEARCH_SUCCESS.getMessage()),
                new PageResponse<>(product));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @DeleteMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> productRemove(@PathVariable Long productNo) {
        productSearchService.removeSearchProduct(productNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.DELETE_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @PostMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<ProductNoResponseDto>> productAdd(@PathVariable Long productNo) {

        ProductNoResponseDto productNoResponseDto =
            new ProductNoResponseDto(productSearchService.addSearchProduct(
                productService.findProductEntity(productNo)));

        CommonResponseBody<ProductNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productNoResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
