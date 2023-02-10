package shop.itbook.itbookshop.productgroup.review.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
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
                .where(qReview.member.memberNo.eq(memberNo))
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
                .where(qReview.product.productNo.eq(productNo))
                .orderBy(qReview.orderProductNo.desc());

        List<ReviewResponseDto> reviewList = reviewListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(reviewList, pageable,
            () -> from(qReview).fetchCount());
    }
}
