package shop.itbook.itbookshop.productgroup.review.controller;

import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.common.response.CommonResponseBody;
import shop.itbook.itbookshop.common.response.PageResponse;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewNoResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;
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
     * @param images           the images
     * @return 저장한 리뷰 번호를 responseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PostMapping("/add")
    public ResponseEntity<CommonResponseBody<ReviewNoResponseDto>> reviewAdd(
        @Valid @RequestPart ReviewRequestDto reviewRequestDto,
        @RequestPart(required = false) MultipartFile images) {

        ReviewNoResponseDto reviewNoResponseDto =
            new ReviewNoResponseDto(reviewService.addReview(reviewRequestDto, images));

        CommonResponseBody<ReviewNoResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_ADD_SUCCESS.getResultMessage()),
            reviewNoResponseDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponseBody);
    }


    /**
     * pk인 orderProductNo로 테이블에서 리뷰 데이터를 찾아와
     * ResponseEntity에 담아 반환합니다.
     *
     * @param orderProductNo 테이블에서 찾는 기준이 될 번호입니다.
     * @return 찾은 리뷰 데이터를 dto에 담고 ResponseEntity로 감싸 반환합니다.
     * @author 노수연
     */
    @GetMapping("/{orderProductNo}")
    public ResponseEntity<CommonResponseBody<ReviewResponseDto>> reviewDetails(
        @PathVariable("orderProductNo") Long orderProductNo) {

        CommonResponseBody<ReviewResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_GET_SUCCESS.getResultMessage()),
            reviewService.findReviewById(orderProductNo)
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    @GetMapping("/modify-form/{memberNo}/{orderProductNo}")
    public ResponseEntity<CommonResponseBody<ReviewResponseDto>> reviewDetailsForModify(
        @PathVariable("memberNo") Long memberNo,
        @PathVariable("orderProductNo") Long orderProductNo) {

        CommonResponseBody<ReviewResponseDto> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_GET_SUCCESS.getResultMessage()),
            reviewService.findReviewByIdAndMemberNo(memberNo, orderProductNo)
        );

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);

    }

    /**
     * orderProductNo로 테이블에서 데이터를 찾아 삭제하는
     * api 입니다.
     *
     * @param orderProductNo 데이터를 삭제할 번호입니다.
     * @return Void를 ResponseEntity에 담아 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{orderProductNo}/delete")
    public ResponseEntity<CommonResponseBody<Void>> reviewDelete(
        @PathVariable("orderProductNo") Long orderProductNo) {

        reviewService.deleteReview(orderProductNo);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_DELETE_SUCCESS.getResultMessage()),
            null);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponseBody);
    }


    /**
     * orderProductNo를 기반으로 테이블에서 데이터를 찾아
     * 수정할 정보가 담긴 dto의 데이터로 수정합니다.
     *
     * @param orderProductNo   수정할 데이터의 번호입니다.
     * @param reviewRequestDto 수정할 정보가 담긴 dto 입니다.
     * @param images           수정할 이미지가 담긴 파일입니다.
     * @return Void를 ResponseEntity에 감싸 반환합니다.
     * @author 노수연
     */
    @PutMapping("/{orderProductNo}/modify")
    public ResponseEntity<CommonResponseBody<Void>> reviewModify(
        @PathVariable("orderProductNo") Long orderProductNo,
        @RequestPart ReviewRequestDto reviewRequestDto,
        @RequestPart(required = false) MultipartFile images) {

        reviewService.modifyReview(orderProductNo, reviewRequestDto, images);

        CommonResponseBody<Void> commonResponseBody = new CommonResponseBody<>(
            new CommonResponseBody.CommonHeader(
                ReviewResultMessageEnum.REVIEW_MODIFY_SUCCESS.getResultMessage()),
            null);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

    /**
     * url로 넘어온 상품 번호로 찾은 리뷰 리스트를 페이징처리하여 반환하는 api 입니다.
     *
     * @param productNo 상품 번호로 리뷰를 찾습니다.
     * @param pageable  반환할때 리스트를 페이징처리합니다.
     * @return 페이징처리한 dto 리스트를 commonResponseBody에 담아 보냅니다.
     * @author 노수연
     */
    @GetMapping("/list/product/{productNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<ReviewResponseDto>>> reviewListByProductNo(
        @PathVariable("productNo") Long productNo,
        @PageableDefault Pageable pageable) {

        Page<ReviewResponseDto> page =
            reviewService.findReviewListByProductNo(pageable, productNo);

        PageResponse<ReviewResponseDto> pageResponse = new PageResponse<>(page);

        CommonResponseBody<PageResponse<ReviewResponseDto>> commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ReviewResultMessageEnum.REVIEW_LIST_GET_SUCCESS.getResultMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }


    /**
     * pathVariable로 멤버 번호를 받아 아직 리뷰가 쓰여지지 않은 주문 상품 리스트를 가져와 반환하는 api 입니다.
     *
     * @param memberNo 멤버 번호로 찾습니다.
     * @param pageable 반환할때 리스트를 페이징처리합니다.
     * @return 페이징처리한 dto 리스트를 commonResponseBody에 담아 보냅니다.
     * @author 노수연
     */
    @GetMapping("/unwritten-list/member/{memberNo}")
    public ResponseEntity<CommonResponseBody<PageResponse<UnwrittenReviewOrderProductResponseDto>>> unwrittenReviewOrderProductList(
        @PathVariable("memberNo") Long memberNo,
        @PageableDefault Pageable pageable) {

        Page<UnwrittenReviewOrderProductResponseDto> page =
            reviewService.findUnwrittenReviewOrderProductList(pageable, memberNo);

        PageResponse<UnwrittenReviewOrderProductResponseDto> pageResponse =
            new PageResponse<>(page);

        CommonResponseBody<PageResponse<UnwrittenReviewOrderProductResponseDto>>
            commonResponseBody =
            new CommonResponseBody<>(
                new CommonResponseBody.CommonHeader(
                    ReviewResultMessageEnum.UNWRITTEN_REVIEW_ORDER_PRODUCT_LIST_GET_SUCCESS.getResultMessage()),
                pageResponse);

        return ResponseEntity.status(HttpStatus.OK).body(commonResponseBody);
    }

}
