package shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service;

import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.ReviewIncreasePointHistory;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author 최겸준
 * @since 1.0
 */
public interface ReviewIncreasePointHistoryService {
    ReviewIncreasePointHistory savePointHistoryAboutReviewIncrease(Member member,
                                                                   Review review,
                                                                   Long pointToApply);
}