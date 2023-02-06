package shop.itbook.itbookshop.bookmark.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.itbook.itbookshop.bookmark.dto.request.BookmarkRequestDto;
import shop.itbook.itbookshop.bookmark.resultmessage.BookmarkResultMessageEnum;
import shop.itbook.itbookshop.bookmark.service.BookmarkService;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.SuccessfulResponseDto;

/**
 * 즐겨찾기에 대한 REST API 입니다.
 *
 * @author 강명관
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<CommonResponseBody<SuccessfulResponseDto>> productAddInBookmark(
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
}
