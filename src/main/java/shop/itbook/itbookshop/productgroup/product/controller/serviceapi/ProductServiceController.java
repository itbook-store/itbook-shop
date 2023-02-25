package shop.itbook.itbookshop.productgroup.product.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductServiceController {
    private final ProductService productService;
    private final ProductTypeService productTypeService;


    /**
     * 사용자가 모든 상품 조회를 요청하는 메서드입니다.
     *
     * @return 노출 여부가 true이고, 삭제되지 않은 상품만 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping
    public ResponseEntity productList(
        @PageableDefault Pageable pageable) {

        Page<ProductDetailsResponseDto> productList =
            productService.findProductListForUser(pageable);

        CommonResponseBody commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 번호 리스트로 상품 상세정보 리스트 조회를 요청하는 메서드입니다.
     *
     * @param productNoList 조회할 상품 번호 리스트입니다.
     * @return 상품 번호에 해당하는 상품의 카테고리 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/{productNoList}")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productList(
        @PageableDefault Pageable pageable, @PathVariable List<Long> productNoList) {

        Page<ProductDetailsResponseDto> productList =
            productService.findProductListByProductNoListForUser(pageable, productNoList);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 모든 상품유형 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 상품 유형 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/product-types")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductTypeResponseDto>>> productTypeList(
        @PageableDefault Pageable pageable) {

        Page<ProductTypeResponseDto> productTypeList =
            productTypeService.findProductTypeList(pageable);

        CommonResponseBody<PageResponse<ProductTypeResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse(productTypeList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
