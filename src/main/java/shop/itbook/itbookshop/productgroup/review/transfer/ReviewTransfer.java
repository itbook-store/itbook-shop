package shop.itbook.itbookshop.productgroup.review.transfer;

import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * 리뷰의 엔티티와 dto 간의 변환을 작성한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ReviewTransfer {

    public ReviewTransfer() {
    }


    /**
     * dto를 엔티티로 변환하는 메서드입니다.
     *
     * @param reviewRequestDto 엔티티로 변환할 리뷰 requestDto입니다.
     * @return 리뷰 엔티티를 반환합니다.
     * @author 노수연
     */
    public static Review dtoToEntity(ReviewRequestDto reviewRequestDto) {
        return Review.builder()
            .content(reviewRequestDto.getContent())
            .starPoint(reviewRequestDto.getStarPoint())
            .build();
    }


    /**
     * 엔티티를 dto로 변환하는 메서드입니다.
     *
     * @param review Dto로 변환할 엔티티입니다.
     * @return 변환된 Dto를 반환합니다.
     * @author 노수연
     */
    public static ReviewResponseDto entityToDto(Review review) {
        return ReviewResponseDto
            .builder()
            .orderProductNo(review.getOrderProductNo())
            .productNo(review.getProduct().getProductNo())
            .productName(review.getProduct().getName())
            .thumbnailUrl(review.getProduct().getThumbnailUrl())
            .memberNo(review.getMember().getMemberNo())
            .starPoint(review.getStarPoint())
            .content(review.getContent())
            .image(review.getImage())
            .build();
    }
}
