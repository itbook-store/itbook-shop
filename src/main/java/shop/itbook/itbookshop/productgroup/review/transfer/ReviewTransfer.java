package shop.itbook.itbookshop.productgroup.review.transfer;

import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
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

    public static Review dtoToEntity(ReviewRequestDto reviewRequestDto) {
        return Review.builder()
            .content(reviewRequestDto.getContent())
            .starPoint(reviewRequestDto.getStarPoint())
            .build();
    }
}
