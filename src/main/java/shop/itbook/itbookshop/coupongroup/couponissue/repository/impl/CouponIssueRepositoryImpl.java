package shop.itbook.itbookshop.coupongroup.couponissue.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.QCategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.coupongroup.couponissue.repository.CustomCouponIssueRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.OrderTotalCouponIssueResponseListDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.QOrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.ProductCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.QProductCoupon;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.QUsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.productcategory.entity.QProductCategory;

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
    public Page<UserCouponIssueListResponseDto> findCouponIssueListByMemberNo(Pageable pageable,
                                                                              Long memberNo) {
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
                qCouponType.couponTypeEnum.stringValue().as("couponType"),
                qUsageStatus.usageStatusName.stringValue().as("usageStatusName"),
                qCouponIssue.couponIssueCreatedAt,
                qCouponIssue.couponExpiredAt,
                qCouponIssue.couponUsageCreatedAt))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .where(qMember.memberNo.eq(memberNo))
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

    @Override
    public List<OrderTotalCouponIssueResponseListDto> findAvailableOrderTotalCouponIssueByMemberNo(
        Long memberNo) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QOrderTotalCoupon qOrderTotalCoupon = QOrderTotalCoupon.orderTotalCoupon;

        return from(qCouponIssue)
            .select(Projections.fields(OrderTotalCouponIssueResponseListDto.class,
                qCouponIssue.couponIssueNo,
                qCouponIssue.couponExpiredAt,
                Projections.fields(OrderCouponListResponseDto.class,
                    qCouponIssue.couponIssueNo, qCoupon.name,
                    qCoupon.code, qCoupon.amount,
                    qCoupon.percent, qCoupon.point,
                    qCouponType.couponTypeEnum.stringValue().as("couponTypeName"),
                    qUsageStatus.usageStatusName.stringValue().as("usageStatusName"),
                    qCoupon.couponCreatedAt,
                    qCoupon.couponExpiredAt).as("couponListResponseDto")))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .join(qOrderTotalCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qOrderTotalCoupon.couponNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .fetch();

    }

    @Override
    public List<CategoryCouponIssueListResponseDto> findAvailableCategoryCouponIssueByMemberNo(
        Long memberNo) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;

        return from(qCouponIssue)
            .select(Projections.fields(CategoryCouponIssueListResponseDto.class,
                qCategoryCoupon.category.categoryNo.as("categoryNo"),
                qCouponIssue.couponIssueNo,
                qCouponIssue.couponExpiredAt,
                Projections.fields(OrderCouponListResponseDto.class,
                    qCoupon.couponNo, qCoupon.name,
                    qCoupon.code, qCoupon.amount,
                    qCoupon.percent, qCoupon.point,
                    qCouponType.couponTypeEnum.stringValue().as("couponTypeName"),
                    qUsageStatus.usageStatusName.stringValue().as("usageStatusName"),
                    qCoupon.couponCreatedAt,
                    qCoupon.couponExpiredAt).as("couponListResponseDto")))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .join(qCategoryCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qCategoryCoupon.couponNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .fetch();

    }

    @Override
    public List<ProductCouponIssueListResponseDto> findAvailableProductCouponIssueByMemberNo(
        Long memberNo) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;

        return from(qCouponIssue)
            .select(Projections.fields(ProductCouponIssueListResponseDto.class,
                qProductCoupon.product.productNo.as("productNo"),
                qCouponIssue.couponIssueNo,
                qCouponIssue.couponExpiredAt,
                Projections.fields(OrderCouponListResponseDto.class,
                    qCoupon.couponNo, qCoupon.name,
                    qCoupon.code, qCoupon.amount,
                    qCoupon.percent, qCoupon.point,
                    qCouponType.couponTypeEnum.stringValue().as("couponTypeName"),
                    qUsageStatus.usageStatusName.stringValue().as("usageStatusName"),
                    qCoupon.couponCreatedAt,
                    qCoupon.couponExpiredAt).as("couponListResponseDto")))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .join(qProductCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qProductCoupon.couponNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .fetch();
    }

    @Override
    public List<OrderCouponSimpleListResponseDto> findAvailableProductCouponByMemberNoAndProductNo(
        Long memberNo, Long productNo) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return from(qCouponIssue)
            .select(Projections.fields(OrderCouponSimpleListResponseDto.class,
                qCouponIssue.couponIssueNo,
                qCoupon.couponNo, qCoupon.name,
                qCoupon.code, qCoupon.amount,
                qCoupon.percent))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCouponIssue.member, qMember)
            .join(qProductCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qProductCoupon.couponNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .where(qProductCoupon.product.productNo.eq(productNo))
            .fetch();
    }

    @Override
    public List<OrderCouponSimpleListResponseDto> findAvailableCategoryCouponByMemberNoAndProductNo(
        Long memberNo, Long productNo) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return from(qCouponIssue)
            .select(Projections.fields(OrderCouponSimpleListResponseDto.class,
                qCouponIssue.couponIssueNo,
                qCoupon.couponNo, qCoupon.name,
                qCoupon.code, qCoupon.amount,
                qCoupon.percent))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCouponIssue.member, qMember)
            .join(qCategoryCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qCategoryCoupon.couponNo))
            .leftJoin(qProductCategory).on(qCategoryCoupon.category.categoryNo.eq(qProductCategory.category.categoryNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .where(qProductCategory.product.productNo.eq(productNo))
            .fetch();
    }

    @Override
    public List<OrderCouponSimpleListResponseDto> findAvailableTotalCouponByMemberNo(
        Long memberNo) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QOrderTotalCoupon qOrderTotalCoupon = QOrderTotalCoupon.orderTotalCoupon;

        return from(qCouponIssue)
            .select(Projections.fields(OrderCouponSimpleListResponseDto.class,
                qCouponIssue.couponIssueNo,
                qCoupon.couponNo, qCoupon.name,
                qCoupon.code, qCoupon.amount,
                qCoupon.percent))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCouponIssue.member, qMember)
            .join(qOrderTotalCoupon).on(qCouponIssue.coupon.couponNo.eq(
                qOrderTotalCoupon.couponNo))
            .where(qMember.memberNo.eq(memberNo))
            .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
            .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCouponIssue.couponUsageCreatedAt.isNull())
            .fetch();
    }

    @Override
    public Page<AdminCouponIssueListResponseDto> findAllCouponIssue(Pageable pageable) {
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");

        JPQLQuery<Long> jpqlQuery = from(qCouponIssue)
            .select(qCouponIssue.count());

        JPQLQuery<AdminCouponIssueListResponseDto> adminCouponIssueListResponseDtoJpqlQuery
            = getAdminCouponIssueListJpqlQuery(qCoupon, qCouponType, qCouponIssue, qUsageStatus,
            qMember, qProductCoupon, qProduct, qCategoryCoupon, qCategory, qParentCategory);

        List<AdminCouponIssueListResponseDto> couponIssueListResponseDtoList =
            adminCouponIssueListResponseDtoJpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(couponIssueListResponseDtoList, pageable,
            jpqlQuery::fetchOne);
    }

    @Override
    public Page<AdminCouponIssueListResponseDto> findAvailableCouponIssueList(Pageable pageable) {

        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QCouponIssue qCouponIssue = QCouponIssue.couponIssue;
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        QMember qMember = QMember.member;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");

        JPQLQuery<Long> jpqlQuery = from(qCouponIssue)
            .select(qCouponIssue.count());

        JPQLQuery<AdminCouponIssueListResponseDto> adminCouponIssueListResponseDtoJpqlQuery
            = getAdminCouponIssueListJpqlQuery(qCoupon, qCouponType, qCouponIssue, qUsageStatus,
            qMember, qProductCoupon, qProduct, qCategoryCoupon, qCategory, qParentCategory);

        List<AdminCouponIssueListResponseDto> couponIssueListResponseDtoList =
            adminCouponIssueListResponseDtoJpqlQuery
                .where(qUsageStatus.usageStatusName.eq(UsageStatusEnum.AVAILABLE))
                .where(qCouponIssue.couponExpiredAt.after(LocalDateTime.now()))
                .where(qCouponIssue.couponUsageCreatedAt.isNull())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(couponIssueListResponseDtoList, pageable,
            jpqlQuery::fetchOne);

    }

    private JPQLQuery<AdminCouponIssueListResponseDto> getAdminCouponIssueListJpqlQuery(
        QCoupon qCoupon, QCouponType qCouponType,
        QCouponIssue qCouponIssue, QUsageStatus qUsageStatus,
        QMember qMember, QProductCoupon qProductCoupon,
        QProduct qProduct, QCategoryCoupon qCategoryCoupon,
        QCategory qCategory, QCategory qParentCategory) {
        return from(qCouponIssue)
            .select(Projections.fields(AdminCouponIssueListResponseDto.class,
                qCouponIssue.couponIssueNo,
                qMember.memberNo, qMember.memberId,
                qCoupon.couponNo, qCoupon.name, qCoupon.code,
                qCoupon.point,
                qCouponType.couponTypeEnum.stringValue().as("couponType"),
                qProduct.productNo, qProduct.name.as("productName"),
                qCategory.categoryNo, qCategory.categoryName,
                qParentCategory.categoryName.as("parentCategoryName"),
                qCouponIssue.couponIssueCreatedAt,
                qCouponIssue.couponExpiredAt,
                qCouponIssue.couponUsageCreatedAt))
            .join(qCouponIssue.coupon, qCoupon)
            .join(qCouponIssue.usageStatus, qUsageStatus)
            .join(qCoupon.couponType, qCouponType)
            .join(qCouponIssue.member, qMember)
            .leftJoin(qProductCoupon).on(qCouponIssue.coupon.couponNo.eq(qProductCoupon.couponNo))
            .leftJoin(qProduct).on(qProductCoupon.product.productNo.eq(qProduct.productNo))
            .leftJoin(qCategoryCoupon).on(qCouponIssue.coupon.couponNo.eq(qCategoryCoupon.couponNo))
            .leftJoin(qCategory).on(qCategoryCoupon.category.categoryNo.eq(qCategory.categoryNo))
            .leftJoin(qParentCategory)
            .on(qCategory.parentCategory.categoryNo.eq(qParentCategory.categoryNo));
    }
}
