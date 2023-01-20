package shop.itbook.itbookshop.productgroup.product.controller.elastic;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductSearchResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;
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

    @GetMapping
    public ResponseEntity<CommonResponseBody<List<ProductSearchResponseDto>>> getProduct(
        @RequestParam String name) {

        List<ProductSearchResponseDto> product = productSearchService.searchProductByTitle(name);

        CommonResponseBody<List<ProductSearchResponseDto>> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductSearchResultMessageEnum.PRODUCT_SEARCH_SUCCESS.getMessage()), product);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
