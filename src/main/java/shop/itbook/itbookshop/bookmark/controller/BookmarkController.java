package shop.itbook.itbookshop.bookmark.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.bookmark.dto.response.BookmarkResponseDto;
import shop.itbook.itbookshop.bookmark.resultmessage.BookmarkResultMessageEnum;
import shop.itbook.itbookshop.bookmark.service.BookmarkService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;

/**
 * 즐겨찾기에 대한 REST API 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    /**
     * 즐겨찾기 상품을 등록하는 컨트롤러 입니다.
     *
     * @param bookmarkRequestDto 상품번호, 회원번호
     * @return 성공 true, 실패 false
     */
    @PostMapping
    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> bookmarkAddProduct(
        @Valid @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {

        SuccessfulResponseDto successfulResponseDto = new SuccessfulResponseDto();
        successfulResponseDto.setIsSuccessful(
            bookmarkService.addProductInBookmark(bookmarkRequestDto)
        );

        CommonResponseBody<SuccessfulResponseDto> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    BookmarkResultMessageEnum.BOOKMARK_ADD_PRODUCT.getSuccessMessage()
                ),
                successfulResponseDto
            );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }

    /**
     * 즐겨찾기의 해당 상품을 삭제하는 메서드 입니다.
     *
     * @param bookmarkRequestDto 회원번호, 상품번호
     * @return 공용응답객체
     */
    @DeleteMapping
    public ResponseEntity<CommonResponseBody<Void>> bookmarkDeleteProduct(
        @Valid @RequestBody BookmarkRequestDto bookmarkRequestDto
    ) {

        bookmarkService.deleteProductInBookmark(bookmarkRequestDto);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    BookmarkResultMessageEnum.BOOKMARK_DELETE_PRODUCT.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }

    /**
     * 즐겨찾기의 해당 회원의 모든 상품을 삭제하는 메서드 입니다.
     *
     * @param memberNo 회원번호
     * @return 공용 응답 객체
     */
    @DeleteMapping("{memberNo}")
    public ResponseEntity<CommonResponseBody<Void>> bookmarkDeleteAllProduct(
        @PathVariable(value = "memberNo") Long memberNo
    ) {

        bookmarkService.deleteAllProductInBookmark(memberNo);

        CommonResponseBody<Void> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    BookmarkResultMessageEnum.BOOKMARK_DELETE_ALL_PRODUCT.getSuccessMessage()
                ),
                null
            );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }

    /**
     * 즐겨찾기의 모든 상품 리스트 페이지네이션 메서드 입니다.
     *
     * @param pageable 페이지네이션을 위해 필요한 객체
     * @param memberNo 회원번호
     * @return 공용 페이지네이션 처리 객체
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<BookmarkResponseDto>>> bookmarkProductList(
        @PageableDefault Pageable pageable,
        @PathVariable(value = "memberNo") Long memberNo
    ) {

        Page<BookmarkResponseDto> allProductInBookmark =
            bookmarkService.getAllProductInBookmark(pageable, memberNo);

        CommonResponseBody<PageResponse<BookmarkResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    BookmarkResultMessageEnum.BOOKMARK_GET_PRODUCT.getSuccessMessage()
                ),
                new PageResponse<>(allProductInBookmark)
            );

        return ResponseEntity.ok().body(commonResponseBody);
    }
}
