package shop.itbook.itbookshop.productgroup.product.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.book.dto.response.BookDetailsResponseDto;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.category.dto.response.CategoryDetailsResponseDto;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductCategoryResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
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
    private final BookService bookService;
    private final ProductCategoryService productCategoryService;
    private final ProductSearchService productSearchService;

    /**
     * 모든 상품 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 상품 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping
    public ResponseEntity<CommonResponseBody<Page<ProductDetailsResponseDto>>> productList(
        Pageable pageable) {

        Pageable page = PageRequest.of(pageable.getPageNumber(), 5);
        Page<ProductDetailsResponseDto> productList = productService.findProductListAdmin(page);

        CommonResponseBody<Page<ProductDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), productList);

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
     * @return 상품 번호에 해당하는 상품의 카테고리 리스트를 response entity에 담아 반환합니다.
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

    /**
     * 상품 및 도서 등록을 요청하는 메서드입니다.
     *
     * @param requestDto 상품(도서) 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @param ebook      the ebook
     * @return 성공 시 성공 메세지와 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<ProductNoResponseDto>> productAdd(
        @RequestPart ProductBookRequestDto requestDto,
        @RequestPart MultipartFile thumbnails,
        @RequestPart(required = false) MultipartFile ebook) {

        ProductNoResponseDto productPk =
            new ProductNoResponseDto(productService.addProduct(requestDto, thumbnails, ebook));

        CommonResponseBody<ProductNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        productSearchService.addSearchProduct(
            productService.findProductEntity(productPk.getProductNo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * 상품 및 도서 수정을 요청하는 메서드입니다.
     *
     * @param productNo  수정할 상품 번호입니다.
     * @param requestDto 상품 수정을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @param ebook      the ebook
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> productModify(
        @PathVariable Long productNo,
        @RequestPart ProductBookRequestDto requestDto,
        @RequestPart MultipartFile thumbnails,
        @RequestPart(required = false) MultipartFile ebook) {

        productService.modifyProduct(productNo, requestDto, thumbnails, ebook);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        productSearchService.modifySearchProduct(productService.findProductEntity(productNo));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 상품 삭제를 요청하는 메서드입니다.
     *
     * @param productNo 삭제할 상품 번호입니다.
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @DeleteMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> productRemove(@PathVariable Long productNo) {
        productService.removeProduct(productNo);
        productSearchService.removeSearchProduct(productNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.DELETE_SUCCESS.getMessage()), null);

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
     * 도서 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 도서 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/books")
    public ResponseEntity<CommonResponseBody<List<BookDetailsResponseDto>>> bookList() {

        List<BookDetailsResponseDto> bookList = bookService.findBookList(false);

        CommonResponseBody<List<BookDetailsResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.ADD_SUCCESS.getMessage()), bookList);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 도서 상세 정보 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 도서의 상세 정보를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/books/{productNo}")
    public ResponseEntity<CommonResponseBody<BookDetailsResponseDto>> bookDetails(
        @PathVariable Long productNo) {

        BookDetailsResponseDto book = bookService.findBook(productNo);

        CommonResponseBody<BookDetailsResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.ADD_SUCCESS.getMessage()), book);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
