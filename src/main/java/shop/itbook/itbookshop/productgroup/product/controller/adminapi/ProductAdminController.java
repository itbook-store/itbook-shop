package shop.itbook.itbookshop.productgroup.product.controller.adminapi;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductModifyRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.salesstatus.ProductSalesStatusService;
import shop.itbook.itbookshop.productgroup.productcategory.service.ProductCategoryService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * 관리자의 요청을 받고 반환하는 상품 Controller 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;
    private final ProductSalesStatusService productSalesStatusService;
    private final ProductCategoryService productCategoryService;
    private final ProductSearchService productSearchService;

    /**
     * 모든 상품 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productList(
        @PageableDefault Pageable pageable) {

        Page<ProductDetailsResponseDto> productList =
            productService.findProductListForAdmin(pageable);

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
     * @return 카테고리 번호에 해당하는 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "categoryNo")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductDetailsResponseDto>>> productListFilteredByCategoryNo(
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
     * 상품별로 카테고리 조회를 요청하는 메서드입니다.
     * 쿼리스트링으로 상품 번호가 파라미터로 들어올 시, 해당 상품의 카테고리들을 조회합니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 상품 번호에 해당하는 상품의 카테고리 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "productNo")
    public ResponseEntity<CommonResponseBody<PageResponse<CategoryDetailsResponseDto>>> productListFilteredByProductNo(
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
     * 상품 및 도서 등록을 요청하는 메서드입니다.
     *
     * @param requestDto 상품(도서) 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @return 성공 시 성공 메세지와 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<ProductNoResponseDto>> productAdd(
        @RequestPart @Valid ProductAddRequestDto requestDto,
        @RequestPart MultipartFile thumbnails) {

        ProductNoResponseDto productPk =
            new ProductNoResponseDto(productService.addProduct(requestDto, thumbnails));

        CommonResponseBody<ProductNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        productSearchService.addSearchProduct(
            productService.findProductEntity(productPk.getProductNo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 상품 수정을 요청하는 메서드입니다.
     *
     * @param productNo  수정할 상품 번호입니다.
     * @param requestDto 상품 수정을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> productModify(
        @PathVariable Long productNo,
        @RequestPart @Valid ProductModifyRequestDto requestDto,
        @RequestPart(required = false) MultipartFile thumbnails) {

        productService.modifyProduct(productNo, requestDto, thumbnails);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        productSearchService.modifySearchProduct(productService.findProductEntity(productNo));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 boolean 필드 수정을 요청하는 메서드입니다.
     *
     * @param productNo 수정할 상품 번호입니다.
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping(value = "/modify/{productNo}", params = "fieldName")
    public ResponseEntity<CommonResponseBody<Void>> changeBooleanFields(
        @RequestParam String fieldName,
        @PathVariable Long productNo) {
        productService.changeBooleanField(productNo, fieldName);
        if (fieldName.equals("delete")) {
            productSearchService.removeSearchProduct(productNo);
        }

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 조회수 필드 수정을 요청하는 메서드입니다.
     *
     * @param productNo 조회수를 수정할 상품 번호입니다.
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping(value = "/modify-dailyhits/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> changeDailyHitsFields(
        @PathVariable Long productNo) {
        productService.changeDailyHits(productNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 조회를 요청하는 메서드입니다.
     *
     * @param productNo 조회할 상품 번호입니다.
     * @return 성공 시 성공 메세지와 조회한 상품의 정보를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<ProductDetailsResponseDto>> productDetails(
        @PathVariable Long productNo) {

        ProductDetailsResponseDto product = productService.findProduct(productNo);

        CommonResponseBody<ProductDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), product);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 판매량을 합산하여 상품판매순위 집계를 제공하는 메서드입니다.
     *
     * @param sortingCriteria 조회할 정렬 기준입니다.
     * @return 상품 번호에 해당하는 상품의 카테고리 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/sales-rank")
    public ResponseEntity<CommonResponseBody<PageResponse<ProductSalesRankResponseDto>>> productListRankedBySalesCounts(
        @PageableDefault Pageable pageable, @RequestParam String sortingCriteria) {

        Page<ProductSalesRankResponseDto> productList =
            productSalesStatusService.findSortingList(sortingCriteria, pageable);

        CommonResponseBody<PageResponse<ProductSalesRankResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()),
                new PageResponse<>(productList));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
