package shop.itbook.itbookshop.productgroup.product.controller.serviceapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
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
    private final BookService bookService;
    private final ProductCategoryService productCategoryService;


    /**
     * 모든 상품 조회를 요청하는 메서드입니다.
     *
     * @return 노출 여부로의 필터링 여부에 따라 조회한 상품 리스트를 response dto에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping()
    public ResponseEntity<CommonResponseBody<List<ProductDetailsResponseDto>>> productList(
        @RequestParam Boolean isFiltered) {

        List<ProductDetailsResponseDto> productList = productService.findProductList(isFiltered);

        CommonResponseBody<List<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), productList);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 모든 도서 조회를 요청하는 메서드입니다.
     *
     * @return 노출 여부로의 필터링 여부에 따라 조회한 도서 리스트를 response dto에 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/books")
    public ResponseEntity<CommonResponseBody<List<BookDetailsResponseDto>>> bookList(
        @RequestParam Boolean isFiltered) {

        List<BookDetailsResponseDto> bookList = bookService.findBookList(isFiltered);

        CommonResponseBody<List<BookDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.ADD_SUCCESS.getMessage()), bookList);

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
    public ResponseEntity<CommonResponseBody<List<ProductDetailsResponseDto>>> productList(
        @RequestParam Integer categoryNo) {

        List<ProductDetailsResponseDto> productList =
            productCategoryService.findProductList(categoryNo);

        CommonResponseBody<List<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()), productList);

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
    public ResponseEntity<CommonResponseBody<List<CategoryDetailsResponseDto>>> productList(
        @RequestParam Long productNo) {

        List<CategoryDetailsResponseDto> categoryList =
            productCategoryService.findCategoryList(productNo);

        CommonResponseBody<List<CategoryDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductCategoryResultMessageEnum.GET_SUCCESS.getMessage()), categoryList);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
