package shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistory.service.PointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.OrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.repository.OrderCancelIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.OrderCancelIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.service.impl.OrderCancelIncreasePointHistoryServiceImpl;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.ReviewIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.repository.ReviewIncreasePointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.ReviewIncreasePointHistoryService;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author 최겸준
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(ReviewIncreasePointHistoryServiceImpl.class)
class ReviewIncreasePointHistoryServiceImplTest {

    @Autowired
    ReviewIncreasePointHistoryService reviewIncreasePointHistoryService;
    @MockBean
    PointHistoryService pointHistoryService;

    @MockBean
    ReviewIncreasePointHistoryRepository reviewIncreasePointHistoryRepository;


    ReviewIncreasePointHistory reviewIncreasePointHistory;

    Review review;

    PointHistory pointHistory;

    Member member;

    PointIncreaseDecreaseContentEnum orderCancelPointEnum;

    @BeforeEach
    void setUp() {
        orderCancelPointEnum = PointIncreaseDecreaseContentEnum.ORDER_CANCEL;

        member = MemberDummy.getMember1();
        member.setMemberNo(1L);
        pointHistory = PointHistoryDummy.getPointHistory();
        pointHistory.setPointHistoryNo(1L);
        pointHistory.setMember(member);

        OrderProduct orderProduct = OrderProductDummy.createOrderProduct(null, null);
        orderProduct.setOrderProductNo(1L);
        review = new Review(orderProduct, null, null, null, null, null);
        review.setOrderProductNo(orderProduct.getOrderProductNo());

        reviewIncreasePointHistory =
            new ReviewIncreasePointHistory(pointHistory.getPointHistoryNo(), review);
    }

    @DisplayName("포인트 히스토리를 저장하고 리뷰적립히스토리를 저장하는 서비스로직이 정상적으로 동작한다.")
    @Test
    void savePointHistoryAboutReviewIncrease() {

        // given
        given(pointHistoryService.getSavedIncreasePointHistory(any(Member.class), anyLong(),
            any(PointIncreaseDecreaseContentEnum.class)))
            .willReturn(pointHistory);

        given(reviewIncreasePointHistoryRepository.save(
            any(ReviewIncreasePointHistory.class)))
            .willReturn(reviewIncreasePointHistory);


        // when
        ReviewIncreasePointHistory actual =
            reviewIncreasePointHistoryService.savePointHistoryAboutReviewIncrease(member,
                review, 500L);

        // then
        assertThat(actual.getPointHistoryNo())
            .isEqualTo(reviewIncreasePointHistory.getPointHistoryNo());
        assertThat(actual.getReview().getOrderProductNo())
            .isEqualTo(reviewIncreasePointHistory.getReview().getOrderProductNo());
    }
}