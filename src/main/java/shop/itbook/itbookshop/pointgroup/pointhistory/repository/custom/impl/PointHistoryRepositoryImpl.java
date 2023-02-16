package shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.membergroup.membership.entity.QMembership;
import shop.itbook.itbookshop.ordergroup.order.dto.response.OrderDetailsResponseDto;
import shop.itbook.itbookshop.ordergroup.order.exception.OrderNotFoundException;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.dto.response.PointHistoryCouponDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.coupon.entity.QCouponIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.dto.response.PointHistoryGiftDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.dto.response.PointHistoryListResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.QPointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.custom.CustomPointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.QGiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.dto.response.PointHistoryGradeDetailsResponseDto;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.grade.entity.QGradeIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.order.entity.QOrderIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.ordercancel.entity.QOrderCancelIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.QReviewIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.QPointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.increasepointplaceenum.PointIncreaseDecreaseContentEnum;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.entity.QReview;

/**
 * @author 최겸준
 * @since 1.0
 */
public class PointHistoryRepositoryImpl extends QuerydslRepositorySupport
    implements CustomPointHistoryRepository {

    public PointHistoryRepositoryImpl() {
        super(PointHistory.class);
    }

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryListResponseDto(Pageable pageable,
                                                                             PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListResponseDto> pointHistoryList =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListResponseDto> countJpql =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum);

        return PageableExecutionUtils.getPage(pointHistoryList, pageable,
            countJpql.fetch()::size);
    }

    @Override
    public Page<PointHistoryListResponseDto> findPointHistoryListResponseDtoThroughSearch(
        Pageable pageable,
        PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum,
        String searchWord) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListResponseDto> pointHistoryList =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .where(qMember.memberId.startsWith(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListResponseDto> countJpql =
            getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                pointIncreaseDecreaseContentEnum)
                .where(qMember.memberId.startsWith(searchWord));

        return PageableExecutionUtils.getPage(pointHistoryList, pageable,
            countJpql.fetch()::size);
    }

    @Override
    public Page<PointHistoryListResponseDto> findMyPointHistoryListResponseDto(Long memberNo,
                                                                               Pageable pageable,
                                                                               PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;
        QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent =
            QPointIncreaseDecreaseContent.pointIncreaseDecreaseContent;

        List<PointHistoryListResponseDto> myPointHistoryList =
            this.getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                    pointIncreaseDecreaseContentEnum)
                .where(qPointHistory.member.memberNo.eq(memberNo))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<PointHistoryListResponseDto> countJpql =
            this.getPointHistoryList(qPointHistory, qMember, qPointIncreaseDecreaseContent,
                    pointIncreaseDecreaseContentEnum)
                .where(qPointHistory.member.memberNo.eq(memberNo));

        return PageableExecutionUtils.getPage(myPointHistoryList, pageable,
            countJpql.fetch()::size);
    }


    private JPQLQuery<PointHistoryListResponseDto> getPointHistoryList(QPointHistory qPointHistory,
                                                                       QMember qMember,
                                                                       QPointIncreaseDecreaseContent qPointIncreaseDecreaseContent,
                                                                       PointIncreaseDecreaseContentEnum pointIncreaseDecreaseContentEnum) {

        JPQLQuery<PointHistoryListResponseDto> jpqlQuery = from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qPointHistory.pointIncreaseDecreaseContent, qPointIncreaseDecreaseContent)
            .orderBy(qPointHistory.pointHistoryNo.desc())
            .select(Projections.fields(PointHistoryListResponseDto.class,
                qMember.memberNo,
                qMember.memberId,
                qMember.name.as("memberName"),
                qPointHistory.pointHistoryNo,
                qPointHistory.increaseDecreasePoint,
                qPointHistory.pointIncreaseDecreaseContent.contentEnum.stringValue().as("content"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt,
                qPointHistory.isDecrease));

        if (Objects.nonNull(pointIncreaseDecreaseContentEnum)) {
            jpqlQuery
                .where(
                    qPointIncreaseDecreaseContent.contentEnum.eq(pointIncreaseDecreaseContentEnum));
        }

        return jpqlQuery;
    }


    @Override
    public PointHistoryGiftDetailsResponseDto findPointHistoryGiftDetailsResponseDto(
        Long pointHistoryNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QGiftIncreaseDecreasePointHistory qGiftIncreaseDecreasePointHistory =
            QGiftIncreaseDecreasePointHistory.giftIncreaseDecreasePointHistory;
        QMember qMainMember = QMember.member;
        QMember qSubMember = new QMember("sub_member");

        return getGiftDetailsJpql(pointHistoryNo, qPointHistory, qGiftIncreaseDecreasePointHistory,
            qMainMember, qSubMember)
            .fetchOne();
    }

    @Override
    public PointHistoryGiftDetailsResponseDto findMyPointHistoryGiftDetailsResponseDto(
        Long pointHistoryNo, Long memberNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QGiftIncreaseDecreasePointHistory qGiftIncreaseDecreasePointHistory =
            QGiftIncreaseDecreasePointHistory.giftIncreaseDecreasePointHistory;
        QMember qMainMember = QMember.member;
        QMember qSubMember = new QMember("sub_member");

        return getGiftDetailsJpql(pointHistoryNo, qPointHistory, qGiftIncreaseDecreasePointHistory,
            qMainMember, qSubMember)
            .where(qMainMember.memberNo.eq(memberNo))
            .fetchOne();
    }

    private JPQLQuery<PointHistoryGiftDetailsResponseDto> getGiftDetailsJpql(Long pointHistoryNo,
                                                                             QPointHistory qPointHistory,
                                                                             QGiftIncreaseDecreasePointHistory qGiftIncreaseDecreasePointHistory,
                                                                             QMember qMainMember,
                                                                             QMember qSubMember) {
        return from(qPointHistory)
            .innerJoin(qPointHistory.member, qMainMember)
            .innerJoin(qGiftIncreaseDecreasePointHistory)
            .on(qPointHistory.pointHistoryNo.eq(qGiftIncreaseDecreasePointHistory.pointHistoryNo))
            .innerJoin(qGiftIncreaseDecreasePointHistory.member, qSubMember)
            .where(qPointHistory.pointHistoryNo.eq(pointHistoryNo))
            .select(Projections.fields(PointHistoryGiftDetailsResponseDto.class,
                qMainMember.memberId.as("mainMemberId"),
                qMainMember.name.as("mainMemberName"),
                qSubMember.memberId.as("subMemberId"),
                qPointHistory.increaseDecreasePoint.as("point"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt,
                qPointHistory.isDecrease
            ));
    }

    @Override
    public PointHistoryGradeDetailsResponseDto findMembershipResponseDtoThroughPointHistory(
        Long pointHistoryNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QGradeIncreasePointHistory qGradeIncreasePointHistory =
            QGradeIncreasePointHistory.gradeIncreasePointHistory;
        QMembership qMembership = QMembership.membership;
        QMember qMember = QMember.member;

        return getMembershipDetailsJpql(pointHistoryNo, qPointHistory, qGradeIncreasePointHistory,
            qMembership,
            qMember)
            .fetchOne();
    }

    @Override
    public PointHistoryGradeDetailsResponseDto findMyMembershipResponseDtoThroughPointHistory(
        Long pointHistoryNo, Long memberNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QGradeIncreasePointHistory qGradeIncreasePointHistory =
            QGradeIncreasePointHistory.gradeIncreasePointHistory;
        QMembership qMembership = QMembership.membership;
        QMember qMember = QMember.member;

        return getMembershipDetailsJpql(pointHistoryNo, qPointHistory, qGradeIncreasePointHistory,
            qMembership,
            qMember)
            .where(qMember.memberNo.eq(memberNo))
            .fetchOne();
    }

    private JPQLQuery<PointHistoryGradeDetailsResponseDto> getMembershipDetailsJpql(
        Long pointHistoryNo,
        QPointHistory qPointHistory,
        QGradeIncreasePointHistory qGradeIncreasePointHistory,
        QMembership qMembership,
        QMember qMember) {
        return from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qGradeIncreasePointHistory)
            .on(qPointHistory.pointHistoryNo.eq(qGradeIncreasePointHistory.pointHistoryNo))
            .innerJoin(qGradeIncreasePointHistory.membership, qMembership)
            .where(qPointHistory.pointHistoryNo.eq(pointHistoryNo))
            .select(Projections.fields(PointHistoryGradeDetailsResponseDto.class,
                qMember.memberId,
                qMember.name.as("memberName"),
                qMembership.membershipNo,
                qMembership.membershipGrade,
                qMembership.membershipStandardAmount,
                qMembership.membershipPoint,
                qPointHistory.increaseDecreasePoint.as("point"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt,
                qPointHistory.isDecrease
            ));
    }

    @Override
    public ReviewResponseDto findPointHistoryReviewDetailsDto(Long pointHistoryNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QReviewIncreasePointHistory qReviewIncreasePointHistory =
            QReviewIncreasePointHistory.reviewIncreasePointHistory;
        QReview qReview = QReview.review;
        QProduct qProduct = QProduct.product;
        QMember qMember = QMember.member;

        return getReviewDetailsJpql(pointHistoryNo, qPointHistory, qReviewIncreasePointHistory,
            qReview,
            qProduct,
            qMember)
            .fetchOne();
    }

    @Override
    public ReviewResponseDto findMyPointHistoryReviewDetailsDto(Long pointHistoryNo,
                                                                Long memberNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QReviewIncreasePointHistory qReviewIncreasePointHistory =
            QReviewIncreasePointHistory.reviewIncreasePointHistory;
        QReview qReview = QReview.review;
        QProduct qProduct = QProduct.product;
        QMember qMember = QMember.member;

        return getReviewDetailsJpql(pointHistoryNo, qPointHistory, qReviewIncreasePointHistory,
            qReview,
            qProduct,
            qMember)
            .where(qMember.memberNo.eq(memberNo))
            .fetchOne();
    }

    private JPQLQuery<ReviewResponseDto> getReviewDetailsJpql(Long pointHistoryNo,
                                                              QPointHistory qPointHistory,
                                                              QReviewIncreasePointHistory qReviewIncreasePointHistory,
                                                              QReview qReview, QProduct qProduct,
                                                              QMember qMember) {
        return from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qReviewIncreasePointHistory)
            .on(qPointHistory.pointHistoryNo.eq(qReviewIncreasePointHistory.pointHistoryNo))
            .innerJoin(qReviewIncreasePointHistory.review, qReview)
            .innerJoin(qReview.product, qProduct)
            .where(qPointHistory.pointHistoryNo.eq(pointHistoryNo))
            .select(Projections.fields(ReviewResponseDto.class,
                qReview.orderProductNo,
                qProduct.productNo,
                qProduct.name.as("productName"),
                qMember.memberNo,
                qReview.starPoint,
                qReview.content,
                qReview.image
            ));
    }

    @Override
    public PointHistoryCouponDetailsResponseDto findPointHistoryCouponDetailsDto(
        Long pointHistoryNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QCouponIncreasePointHistory qCouponIncreasePointHistory =
            QCouponIncreasePointHistory.couponIncreasePointHistory;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QCoupon qCoupon = QCoupon.coupon;
        QMember qMember = QMember.member;

        return getCouponDetailsJpql(pointHistoryNo, qPointHistory, qCouponIncreasePointHistory,
            qCouponIssue,
            qCoupon,
            qMember)
            .fetchOne();
    }

    @Override
    public PointHistoryCouponDetailsResponseDto findMyPointHistoryCouponDetailsDto(
        Long pointHistoryNo, Long memberNo) {

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QCouponIncreasePointHistory qCouponIncreasePointHistory =
            QCouponIncreasePointHistory.couponIncreasePointHistory;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QCoupon qCoupon = QCoupon.coupon;
        QMember qMember = QMember.member;

        return getCouponDetailsJpql(pointHistoryNo, qPointHistory, qCouponIncreasePointHistory,
            qCouponIssue,
            qCoupon,
            qMember)
            .where(qMember.memberNo.eq(memberNo))
            .fetchOne();
    }

    private JPQLQuery<PointHistoryCouponDetailsResponseDto> getCouponDetailsJpql(
        Long pointHistoryNo,
        QPointHistory qPointHistory,
        QCouponIncreasePointHistory qCouponIncreasePointHistory,
        QCouponIssue qCouponIssue,
        QCoupon qCoupon,
        QMember qMember) {
        return from(qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .innerJoin(qCouponIncreasePointHistory)
            .on(qPointHistory.pointHistoryNo.eq(qCouponIncreasePointHistory.pointHistoryNo))
            .innerJoin(qCouponIncreasePointHistory.couponIssue, qCouponIssue)
            .innerJoin(qCouponIssue.coupon, qCoupon)
            .where(qPointHistory.pointHistoryNo.eq(pointHistoryNo))
            .select(Projections.fields(PointHistoryCouponDetailsResponseDto.class,
                qMember.memberId,
                qMember.name.as("memberName"),
                qCoupon.name.as("couponName"),
                qCoupon.couponType.couponTypeEnum.stringValue().as("couponType"),
                qCoupon.point.as("couponPoint"),
                qCoupon.code.as("couponCode"),
                qCoupon.isDuplicateUse.as("isDuplicateUse"),
                qPointHistory.increaseDecreasePoint.as("point"),
                qPointHistory.remainedPoint,
                qPointHistory.historyCreatedAt,
                qPointHistory.isDecrease
            ));
    }

    @Override
    public Long findOrderNoThroughPointHistory(Long pointHistoryNo) {

        QOrderIncreaseDecreasePointHistory qOrderIncreaseDecreasePointHistory =
            QOrderIncreaseDecreasePointHistory.orderIncreaseDecreasePointHistory;
        QOrderCancelIncreasePointHistory qOrderCancelIncreasePointHistory =
            QOrderCancelIncreasePointHistory.orderCancelIncreasePointHistory;

        Long orderNo = from(qOrderIncreaseDecreasePointHistory)
            .where(qOrderIncreaseDecreasePointHistory.pointHistoryNo.eq(pointHistoryNo))
            .select(qOrderIncreaseDecreasePointHistory.order.orderNo)
            .fetchOne();

        if (Objects.isNull(orderNo)) {
            orderNo = from(qOrderCancelIncreasePointHistory)
                .where(qOrderCancelIncreasePointHistory.pointHistoryNo.eq(pointHistoryNo))
                .select(qOrderCancelIncreasePointHistory.order.orderNo)
                .fetchOne();
        }

        if (Objects.isNull(orderNo)) {
            throw new OrderNotFoundException();
        }

        return orderNo;
    }

    @Override
    public Long findMyOrderNoThroughPointHistory(Long pointHistoryNo, Long memberNo) {

        QOrderIncreaseDecreasePointHistory qOrderIncreaseDecreasePointHistory =
            QOrderIncreaseDecreasePointHistory.orderIncreaseDecreasePointHistory;
        QOrderCancelIncreasePointHistory qOrderCancelIncreasePointHistory =
            QOrderCancelIncreasePointHistory.orderCancelIncreasePointHistory;

        QPointHistory qPointHistory = QPointHistory.pointHistory;
        QMember qMember = QMember.member;

        Long orderNo = from(qOrderIncreaseDecreasePointHistory)
            .innerJoin(qOrderIncreaseDecreasePointHistory.pointHistory, qPointHistory)
            .innerJoin(qPointHistory.member, qMember)
            .where(qOrderIncreaseDecreasePointHistory.pointHistoryNo.eq(pointHistoryNo)
                .and(qMember.memberNo.eq(memberNo)))
            .select(qOrderIncreaseDecreasePointHistory.order.orderNo)
            .fetchOne();

        if (Objects.isNull(orderNo)) {
            orderNo = from(qOrderCancelIncreasePointHistory)
                .innerJoin(qOrderCancelIncreasePointHistory.pointHistory, qPointHistory)
                .innerJoin(qPointHistory.member, qMember)
                .where(qOrderCancelIncreasePointHistory.pointHistoryNo.eq(pointHistoryNo)
                    .and(qMember.memberNo.eq(memberNo)))
                .select(qOrderCancelIncreasePointHistory.order.orderNo)
                .fetchOne();
        }

        if (Objects.isNull(orderNo)) {
            throw new OrderNotFoundException();
        }

        return orderNo;
    }
}
