package shop.itbook.itbookshop.book.controller.adminapi;

import lombok.RequiredArgsConstructor;
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
import shop.itbook.itbookshop.book.dto.request.BookModifyRequestDto;
import shop.itbook.itbookshop.book.dto.response.BookBooleanResponseDto;
import shop.itbook.itbookshop.book.service.AladinApiService;
import shop.itbook.itbookshop.book.service.BookService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.Item;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.service.elastic.ProductSearchService;

/**
 * @author 이하늬
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/products/books")
public class BookAdminController {

    private final AladinApiService aladinApiService;
    private final BookService bookService;
    private final ProductService productService;
    private final ProductSearchService productSearchService;

    /**
     * 알라딘 API를 이용해 ISBN으로 도서 부가정보를 검색하는 기능을 담당합니다.
     *
     * @param isbn 도서 부가 정보를 검색할 isbn입니다.
     * @return 성공 시 성공 메세지와 조회한 상품 정보를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping(params = "isbn")
    public ResponseEntity<CommonResponseBody<Item>> bookSearch(
        @RequestParam String isbn) {

        Item bookDetails = aladinApiService.getBookDetails(isbn);

        CommonResponseBody<Item> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), bookDetails);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 도서 등록 시 입력한 isbn으로 알라딘 API에 조회 요청하여 입력한 도서가 존재하는지 체크 하는 기능을 담당합니다.
     *
     * @param isbn 사용자가 등록 요청한 isbn입니다.
     * @return 알라딘 API로 조회하여 존재 여부를 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/check-exist-aladin")
    public ResponseEntity<CommonResponseBody<BookBooleanResponseDto>> checkIsbnExistInAladin(
        @RequestParam("isbn") String isbn) {

        BookBooleanResponseDto exists = bookService.isExistsInAladinByIsbn(isbn);

        CommonResponseBody<BookBooleanResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), exists);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 도서 등록 시 Isbn 중복 체크 하는 기능을 담당합니다.
     *
     * @param isbn 사용자가 등록 요청한 isbn입니다.
     * @return 중복 여부를 검사하여 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/check-exist-db")
    public ResponseEntity<CommonResponseBody<BookBooleanResponseDto>> checkIsbnDuplicatedInDB(
        @RequestParam("isbn") String isbn) {

        BookBooleanResponseDto exists = bookService.isExistsInDBByIsbn(isbn);

        CommonResponseBody<BookBooleanResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.GET_SUCCESS.getMessage()), exists);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * 도서 등록을 요청하는 메서드입니다.
     *
     * @param requestDto 도서 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @param ebook      the ebook
     * @return 성공 시 성공 메세지와 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<ProductNoResponseDto>> BookAdd(
        @RequestPart ProductBookRequestDto requestDto,
        @RequestPart MultipartFile thumbnails,
        @RequestPart(required = false) MultipartFile ebook) {

        ProductNoResponseDto productPk =
            new ProductNoResponseDto(bookService.addBook(requestDto, thumbnails, ebook));

        CommonResponseBody<ProductNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        productSearchService.addSearchProduct(
            productService.findProductEntity(productPk.getProductNo()));

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * 도서 수정을 요청하는 메서드입니다.
     *
     * @param productNo  수정할 도서 번호입니다.
     * @param requestDto 도서 수정을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @param thumbnails the thumbnails
     * @param ebook      the ebook
     * @return 성공 시 성공 메세지를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PutMapping("/{productNo}")
    public ResponseEntity<CommonResponseBody<Void>> productModify(
        @PathVariable Long productNo,
        @RequestPart BookModifyRequestDto requestDto,
        @RequestPart(required = false) MultipartFile thumbnails,
        @RequestPart(required = false) MultipartFile ebook) {

        bookService.modifyBook(productNo, requestDto, thumbnails, ebook);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ProductResultMessageEnum.MODIFY_SUCCESS.getMessage()), null);

        productSearchService.modifySearchProduct(productService.findProductEntity(productNo));

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }
}
