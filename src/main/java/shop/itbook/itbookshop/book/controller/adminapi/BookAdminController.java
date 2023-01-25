package shop.itbook.itbookshop.book.controller.adminapi;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.book.dto.response.FindBookResponseDto;
import shop.itbook.itbookshop.book.service.adminapi.BookService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.productgroup.product.resultmessageenum.ProductResultMessageEnum;

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

    private final BookService bookService;

    /**
     * 도서 조회를 요청하는 메서드입니다.
     *
     * @return 조회한 도서 리스트를 response entity에 담아 반환합니다.
     * @author 이하늬
     */
    @GetMapping("/api/admin/products/books")
    public ResponseEntity<CommonResponseBody<List<FindBookResponseDto>>> bookList() {

        List<FindBookResponseDto> bookList = bookService.findBookList();

        CommonResponseBody<List<FindBookResponseDto>> commonResponseBody =
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
    @GetMapping("/api/admin/products/books/{id}")
    public ResponseEntity<CommonResponseBody<FindBookResponseDto>> bookDetails(
        @PathVariable Long id) {

        FindBookResponseDto book = bookService.findBook(id);

        CommonResponseBody<FindBookResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ProductResultMessageEnum.ADD_SUCCESS.getMessage()), book);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
