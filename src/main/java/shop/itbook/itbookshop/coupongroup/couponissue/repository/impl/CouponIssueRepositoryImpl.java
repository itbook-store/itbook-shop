package shop.itbook.itbookshop.coupongroup.couponissue.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CustomCouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.QUsageStatus;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponIssueRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCouponIssueRepository {
    public CouponIssueRepositoryImpl() {
        super(CouponIssue.class);
    }

    @Override
    public Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberId(Pageable pageable,
                                                                              String memberId) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;

        JPQLQuery<Long> jpqlQuery = from(qCouponIssue)
            .select(qCouponIssue.count())
            .join(qCouponIssue.member, qMember);

        List<UserCouponIssueListResponseDto> couponList = jpqlQuery
            .select(Projections.fields(UserCouponIssueListResponseDto.class,
                qCouponIssue.couponIssueNo,
                qCoupon.name,
                qCoupon.code,
                qCoupon.amount,
                qCoupon.percent,
                qCoupon.point,
                qCouponType.couponTypeEnum.stringValue().as("couponTypeName"),
                qUsageStatus.usageStatusName.stringValue().as("usageStatusName"),
                qCouponIssue.couponIssueCreatedAt,
                qCouponIssue.couponExpiredAt,
                qCouponIssue.couponUsageCreatedAt))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .where(qMember.memberId.eq(memberId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        return PageableExecutionUtils.getPage(couponList, pageable, jpqlQuery::fetchOne);
    }

    @Override
    public CouponIssue findByIdFetchJoin(Long couponIssueNo) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;

        return from(qCouponIssue)
            .select(qCouponIssue)
            .join(qCouponIssue.coupon, qCoupon)
            .fetchJoin()
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .fetchJoin()
            .join(qCoupon.couponType, qCouponType)
            .fetchJoin()
            .join(qCouponIssue.member, qMember)
            .fetchJoin()
            .where(qCouponIssue.couponIssueNo.eq(couponIssueNo))
            .fetchOne();
    }
}
