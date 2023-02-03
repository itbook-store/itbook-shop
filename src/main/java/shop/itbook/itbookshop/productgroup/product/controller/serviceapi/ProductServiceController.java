package shop.itbook.itbookshop.productgroup.product.controller.serviceapi;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductServiceController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;


    /**
     * 모든 상품 조회를 요청하는 메서드입니다.
     *
     * @return 노출 여부로의 필터링 여부에 따라 조회한 상품 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productList(
        @PageableDefault Pageable pageable) {

        Page<ProductDetailsResponseDto> productList =
            productService.findProductList(pageable, false);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 카테고리별로 상품 조회를 요청하는 메서드입니다.
     * 쿼리스트링으로 카테고리 번호가 파라미터로 들어올 시, 해당 카테고리 번호의 상품들을 조회합니다.
     *
     * @param categoryNo 조회할 카테고리 번호입니다.
     * @return 카테고리 번호에 해당하는 상품 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "categoryNo")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productList(
        @PageableDefault Pageable pageable, @RequestParam Integer categoryNo) {

        Page<ProductDetailsResponseDto> productList =
            productCategoryService.findProductList(pageable, categoryNo);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품유형별로 상품 조회를 요청하는 메서드입니다.
     * 쿼리스트링으로 상품유형 번호가 파라미터로 들어올 시, 해당 상품유형 번호의 상품들을 조회합니다.
     *
     * @param productTypeNo 조회할 상품유형 번호입니다.
     * @return 상품유형 번호에 해당하는 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "productTypeNo")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListFilteredByProductTypeNo(
        @PageableDefault Pageable pageable, @RequestParam Integer productTypeNo) {

        Page<ProductDetailsResponseDto> productList =
            productService.findProductListByProductTypeNo(pageable, productTypeNo, false);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품별로 카테고리 조회를 요청하는 메서드입니다.
     * 쿼리스트링으로 상품 번호가 파라미터로 들어올 시, 해당 상품의 카테고리들을 조회합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 상품 번호에 해당하는 상품의 카테고리 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "productNo")
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryDetailsResponseDto>>> productList(
        @PageableDefault Pageable pageable, @RequestParam Long productNo) {

        Page<CategoryDetailsResponseDto> categoryList =
            productCategoryService.findCategoryList(pageable, productNo);

        CommonResponseBody<PageResponse<CategoryDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(categoryList));

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
            productService.findProductListByProductNoList(pageable, productNoList);

        CommonResponseBody<PageResponse<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
