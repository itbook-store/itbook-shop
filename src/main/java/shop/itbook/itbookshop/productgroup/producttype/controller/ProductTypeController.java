package shop.itbook.itbookshop.productgroup.producttype.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.producttype.service.ProductTypeService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/products")
public class ProductTypeController {
    private final ProductTypeService productTypeService;

    /**
     * 상품유형별로 상품 조회를 요청하는 기능을 담당합니다.
     * 쿼리스트링으로 상품유형 번호가 파라미터로 들어올 시, 해당 상품유형 번호의 상품들을 조회합니다.
     *
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 상품유형 번호에 해당하는 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "productTypeNo")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListFilteredByProductTypeNo(
        @PageableDefault Pageable pageable, @RequestParam Integer productTypeNo, @RequestParam(required = false) Long memberNo) {

        Page<ProductDetailsResponseDto> productList =
            productTypeService.findProductListByProductTypeNo(pageable, productTypeNo, memberNo);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
