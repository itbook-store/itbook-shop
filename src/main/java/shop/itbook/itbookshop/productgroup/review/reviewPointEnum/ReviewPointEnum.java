package shop.itbook.itbookshop.productgroup.review.reviewPointEnum;

import lombok.Getter;

/**
 * @author 노수연
 * @since 1.0
 */
@Getter
public enum ReviewPointEnum {
    REVIEW_POINT("100");

    private final String reviewPoint;

    ReviewPointEnum(String reviewPoint) {
        this.reviewPoint = reviewPoint;
    }
}
