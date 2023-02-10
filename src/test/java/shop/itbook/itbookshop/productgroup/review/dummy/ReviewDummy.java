package shop.itbook.itbookshop.productgroup.review.dummy;

import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author 노수연
 * @since 1.0
 */
public class ReviewDummy {

    public static Review getReview() {

        return Review.builder()
            .starPoint(5)
            .content("재미있는 책입니다.")
            .image("이미지url")
            .build();
    }
}
