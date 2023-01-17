package shop.itbook.itbookshop.book.controller.adminapi;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.book.dto.response.FindBookListResponseDto;
import shop.itbook.itbookshop.book.service.adminapi.BookAdminService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.dto.request.AddProductBookRequestDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.AddProductResponseDto;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;
import shop.itbook.itbookshop.productgroup.product.service.adminapi.ProductAdminService;

/**
 * 관리자의 요청을 받고 반환하는 도서 Controller 클래스입니다.
 *
 * @author 이하늬 * @since 1.0
 * @since 1.0
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
public class BookAdminController {

    private final BookAdminService bookService;
    private final ProductAdminService productService;

    public static final Boolean SUCCESS_RESULT = Boolean.TRUE;


    /**
     * 도서 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 도서 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/admin/products/books")
    public ResponseEntity<CommonResponseBody<List<FindBookListResponseDto>>> productList() {

        List<FindBookListResponseDto> bookList = bookService.findBookList();

        CommonResponseBody<List<FindBookListResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(SUCCESS_RESULT, HttpStatus.CREATED.value(),
                    ProductResultMessageEnum.ADD_SUCCESS.getMessage()), bookList);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 도서 등록을 요청하는 메서드입니다.
     *
     * @param requestDto 상품 등록을 위한 정보를 바인딩 받는 dto 객체입니다.
     * @return 등록한 상품의 상품 번호를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @PostMapping("/api/admin/products/books")
    public ResponseEntity<CommonResponseBody<AddProductResponseDto>> BookAdd(
        @Valid @RequestBody AddProductBookRequestDto requestDto) {

        AddProductResponseDto productPk =
            new AddProductResponseDto(bookService.addBook(requestDto));

        CommonResponseBody<AddProductResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(SUCCESS_RESULT, HttpStatus.CREATED.value(),
                ProductResultMessageEnum.ADD_SUCCESS.getMessage()), productPk);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }
}
