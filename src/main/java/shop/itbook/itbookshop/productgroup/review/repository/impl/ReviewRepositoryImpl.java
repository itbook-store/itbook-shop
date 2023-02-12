package shop.itbook.itbookshop.productgroup.review.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.review.entity.QReview;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.repository.CustomReviewRepository;

/**
 * 커스텀 리뷰 repository를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ReviewRepositoryImpl extends QuerydslRepositorySupport
    implements CustomReviewRepository {

    public ReviewRepositoryImpl() {
        super(Review.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewResponseDto> findReviewListByMemberNo(Pageable pageable,
                                                            Long memberNo) {

        QReview qReview = QReview.review;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QMember qMember = QMember.member;

        JPQLQuery<ReviewResponseDto> reviewListQuery =
            from(qReview)
                .innerJoin(qReview.orderProduct, qOrderProduct)
                .innerJoin(qReview.product, qProduct)
                .innerJoin(qReview.member, qMember)
                .select(Projections.constructor(ReviewResponseDto.class,
                    qReview.orderProductNo, qReview.product.productNo, qReview.product.name,
                    qReview.member.memberNo,
                    qReview.starPoint, qReview.content, qReview.image))
                .where(qReview.member.memberNo.eq(memberNo).and(qReview.starPoint.ne(-1)))
                .orderBy(qReview.orderProductNo.desc());

        List<ReviewResponseDto> reviewList = reviewListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(reviewList, pageable,
            () -> from(qReview).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewResponseDto> findReviewListByProductNo(Pageable pageable, Long productNo) {

        QReview qReview = QReview.review;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QMember qMember = QMember.member;

        JPQLQuery<ReviewResponseDto> reviewListQuery =
            from(qReview)
                .innerJoin(qReview.orderProduct, qOrderProduct)
                .innerJoin(qReview.product, qProduct)
                .innerJoin(qReview.member, qMember)
                .select(Projections.constructor(ReviewResponseDto.class,
                    qReview.orderProductNo, qReview.product.productNo, qReview.product.name,
                    qReview.member.memberNo,
                    qReview.starPoint, qReview.content, qReview.image))
                .where(qReview.product.productNo.eq(productNo).and(qReview.starPoint.ne(-1)))
                .orderBy(qReview.orderProductNo.desc());

        List<ReviewResponseDto> reviewList = reviewListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(reviewList, pageable,
            () -> from(qReview).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UnwrittenReviewOrderProductResponseDto> findUnwrittenReviewOrderProductList(
        Pageable pageable,
        Long memberNo) {

        QReview qReview = QReview.review;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QOrder qOrder = QOrder.order;
        QOrderMember qOrderMember = QOrderMember.orderMember;
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        JPQLQuery<UnwrittenReviewOrderProductResponseDto> unwrittenReviewOrderProductListQuery =
            from(qOrderProduct)
                .leftJoin(qReview)
                .on(qOrderProduct.orderProductNo.eq(qReview.orderProductNo))
                .rightJoin(qOrderMember)
                .on(qOrderProduct.order.orderNo.eq(qOrderMember.orderNo))
                .rightJoin(qOrderStatusHistory)
                .on(qOrderProduct.order.eq(
                    qOrderStatusHistory.order))
                .innerJoin(qOrderStatus).on(qOrderStatusHistory.orderStatus.orderStatusNo.eq(
                    qOrderStatus.orderStatusNo))
                .innerJoin(qOrder)
                .on(qOrderProduct.order.orderNo.eq(qOrder.orderNo))
                .innerJoin(qProduct)
                .on(qOrderProduct.product.productNo.eq(qProduct.productNo))
                .select(Projections.constructor(UnwrittenReviewOrderProductResponseDto.class,
                    qOrderProduct.orderProductNo, qProduct.productNo, qProduct.name,
                    qProduct.thumbnailUrl,
                    qOrder.orderCreatedAt))
                .where(qOrderMember.member.memberNo.eq(memberNo)
                    .and(qOrderStatus.orderStatusEnum.stringValue().eq("결제완료"))
                    .and(qReview.orderProductNo.isNull()))
                .orderBy(qOrderProduct.orderProductNo.desc());

        List<UnwrittenReviewOrderProductResponseDto> unwrittenReviewOrderProductList =
            unwrittenReviewOrderProductListQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(unwrittenReviewOrderProductList, pageable,
            () -> from(qReview).fetchCount());
    }
}
