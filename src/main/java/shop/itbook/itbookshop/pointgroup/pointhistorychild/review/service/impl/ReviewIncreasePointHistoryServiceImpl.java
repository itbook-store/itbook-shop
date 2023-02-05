package shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository.OrderCancelIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.ReviewIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.repository.ReviewIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.ReviewIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewIncreasePointHistoryServiceImpl
    implements ReviewIncreasePointHistoryService {

    private final PointHistoryService pointHistoryService;
    private final ReviewIncreasePointHistoryRepository
        reviewIncreasePointHistoryRepository;

    @Override
    public ReviewIncreasePointHistory savePointHistoryAboutReviewIncrease(Member member,
                                                                          Review review,
                                                                          Long pointToApply) {
        PointHistory savedPointHistory =
            pointHistoryService.getSavedIncreasePointHistory(member, pointToApply,
                PointIncreaseDecreaseContentEnum.ORDER_CANCEL);

        ReviewIncreasePointHistory reviewIncreasePointHistory =
            new ReviewIncreasePointHistory(savedPointHistory.getPointHistoryNo(), review);

        return reviewIncreasePointHistoryRepository.save(reviewIncreasePointHistory);
    }
}
