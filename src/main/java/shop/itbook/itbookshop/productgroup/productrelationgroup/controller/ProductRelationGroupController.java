package shop.itbook.itbookshop.productgroup.productrelationgroup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.request.ProductRelationRequestDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.service.ProductRelationGroupService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ProductRelationGroupController {
    private final ProductRelationGroupService productRelationGroupService;

    /**
     * <사용자> 연관상품 조회를 요청하는 기능을 담당합니다.
     * path variable로 기준이 되는 상품 번호가 파라미터로 들어올 시, 해당 상품의 연관상품들을 조회합니다.
     *
     * @param basedProductNo 조회할 상품 번호입니다.
     * @return 연관 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/products/relation/{basedProductNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListFilteredByBasedProductNoForUsers(
        @PageableDefault Pageable pageable, @PathVariable Long basedProductNo) {

        Page<ProductDetailsResponseDto> productList =
            productRelationGroupService.findProductRelationForUser(pageable, basedProductNo);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    /**
     * <관리자> 연관상품 조회를 요청하는 기능을 담당합니다.
     * 삭제된 연관상품도 조회 가능합니다.
     * path variable로 기준이 되는 상품 번호가 파라미터로 들어올 시, 해당 상품의 연관상품들을 조회합니다.
     *
     * @param basedProductNo 조회할 상품 번호입니다.
     * @return 연관 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/admin/products/relation/{basedProductNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListFilteredByBasedProductNoForAdmin(
        @PageableDefault Pageable pageable, @PathVariable Long basedProductNo) {

        Page<ProductDetailsResponseDto> productList =
            productRelationGroupService.findProductRelationForAdmin(pageable, basedProductNo);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * <관리자> 모든 연관상품의 상위 연관상품을 조회를 요청하는 기능을 담당합니다.
     * 삭제된 연관상품도 조회 가능합니다.
     *
     * @return 연관 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/admin/products/relation")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductRelationResponseDto>>> allRelationProductListForAdmin(
        @PageableDefault Pageable pageable) {

        Page<ProductRelationResponseDto> productList =
            productRelationGroupService.findAllMainProductRelationForAdmin(pageable);

        CommonResponseBody<PageResponse<ProductRelationResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * <관리자> 연관상품 등록을 위해 자신을 제외한 상품들을 요청하는 기능을 담당합니다.
     * path variable로 기준이 되는 상품 번호가 파라미터로 들어올 시, 해당 상품을 제외한 나머지 상품들을 조회합니다.
     *
     * @param basedProductNo 조회할 상품 번호입니다.
     * @return 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/admin/products/relation/add-candidates/{basedProductNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListExceptBasedProduct(
        @PageableDefault Pageable pageable, @PathVariable Long basedProductNo) {

        Page<ProductDetailsResponseDto> productList =
            productRelationGroupService.findProductExceptBasedProductForAdmin(pageable,
                basedProductNo);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * <관리자> 연관상품 수정을 하는 기능을 담당합니다.
     * path variable로 기준이 되는 상품 번호가 파라미터로 들어올 시, 해당 상품에 대한 연관 상품을 수정합니다.
     *
     * @param basedProductNo 수정할 상품 번호입니다.
     * @return 결과 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping("/api/admin/products/relation/{basedProductNo}/edit")
    public ResponseEntity<CommonResponseBody<Void>> productListExceptBasedProduct(
        @RequestBody ProductRelationRequestDto relationList, @PathVariable Long basedProductNo) {

        productRelationGroupService.modifyProductRelation(basedProductNo, relationList);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_RELATION_PRODUCT_SUCCESS.getMessage()),
                null);

        return ResponseEntity.status(HttpStatus.OK.value()).body(commonResponseBody);
    }

}
