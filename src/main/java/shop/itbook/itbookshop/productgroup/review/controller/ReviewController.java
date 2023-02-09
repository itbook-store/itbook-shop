package shop.itbook.itbookshop.productgroup.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewNoResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.resultMessageEnum.ReviewResultMessageEnum;
import shop.itbook.itbookshop.productgroup.review.service.ReviewService;

/**
 * 리뷰 Rest Controller 클래스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * url로 넘어온 멤버 번호로 찾은 리뷰 리스트를 페이징처리하여 반환하는 api 입니다.
     *
     * @param memberNo 멤버 번호로 리뷰를 찾습니다.
     * @param pageable 반환할때 리스트를 페이징처리합니다.
     * @return 페이징처리한 dto 리스트를 commonResponseBody에 담아 보냅니다.
     * @author 노수연
     */
    @GetMapping("/list/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ReviewResponseDto>>> reviewListByMemberNo(
        @PathVariable("memberNo") Long memberNo,
        @PageableDefault Pageable pageable) {

        Page<ReviewResponseDto> page =
            reviewService.findReviewListByMemberNo(pageable, memberNo);

        PageResponse<ReviewResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<ReviewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ReviewResultMessageEnum.REVIEW_LIST_GET_SUCCESS.getResultMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }


    /**
     * 리뷰 저장 요청을 처리하는 api 메서드 입니다.
     *
     * @param reviewRequestDto 리뷰 정보를 저장한 dto 입니다.
     * @return 저장한 리뷰 번호를 responseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ReviewNoResponseDto>> reviewAdd(
        @RequestPart ReviewRequestDto reviewRequestDto,
        @RequestPart MultipartFile images) {

        ReviewNoResponseDto reviewNoResponseDto =
            new ReviewNoResponseDto(reviewService.addReview(reviewRequestDto, images));

        CommonResponseBody<ReviewNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_ADD_SUCCESS.getResultMessage()),
            reviewNoResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


}
