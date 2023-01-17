package shop.itbook.itbookshop.productgroup.product.controller.elastic;

import static shop.itbook.itbookshop.productgroup.product.controller.adminapi.ProductAdminController.SUCCESS_RESULT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSearchResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author 송다혜
 * @since 1.0
 */
@Controller
@RequestMapping("/api/admin/products/search")
@RequiredArgsConstructor
public class ProductElasticController {
    private final ProductSearchService productSearchService;

    @GetMapping
    public ResponseEntity<CommonResponseBody<List<ProductSearchResponseDto>>> getProduct(
        @RequestParam String name) {

        List<ProductSearchResponseDto> product = productSearchService.searchProductByTitle(name);

        CommonResponseBody<List<ProductSearchResponseDto>> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(SUCCESS_RESULT, HttpStatus.CREATED.value(),
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), product);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
