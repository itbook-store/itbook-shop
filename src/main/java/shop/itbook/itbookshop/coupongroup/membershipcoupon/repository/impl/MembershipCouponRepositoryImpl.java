package shop.itbook.itbookshop.coupongroup.membershipcoupon.repository.impl;

import com.querydsl.core.types.Projections;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.QCategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.ServiceCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.entity.QCoupon;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.QCouponIssue;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.dto.response.MembershipCouponResponseDto;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.MembershipCoupon;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.entity.QMembershipCoupon;
import shop.itbook.itbookshop.coupongroup.membershipcoupon.repository.CustomMembershipCouponRepository;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.QProductCoupon;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.QUsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.usagestatusenum.UsageStatusEnum;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.membergroup.membership.entity.QMembership;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;

/**
 * @author 송다혜
 * @since 1.0
 */
public class MembershipCouponRepositoryImpl extends QuerydslRepositorySupport
    implements CustomMembershipCouponRepository {
    public MembershipCouponRepositoryImpl() {
        super(MembershipCoupon.class);
    }

    @Override
    public List<MembershipCouponResponseDto> findAvailableMembershipCouponList() {
        QMembershipCoupon qMembershipCoupon = QMembershipCoupon.membershipCoupon;
        QMembership qMembership = QMembership.membership;
        QCoupon qCoupon = QCoupon.coupon;
        QCouponType qCouponType = QCouponType.couponType;
        QProductCoupon qProductCoupon = QProductCoupon.productCoupon;
        QProduct qProduct = QProduct.product;
        QCategoryCoupon qCategoryCoupon = QCategoryCoupon.categoryCoupon;
        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");


        return from(qMembershipCoupon)
            .select(Projections.fields(MembershipCouponResponseDto.class,
                qMembershipCoupon.membership.membershipGrade,
                Projections.fields(ServiceCouponListResponseDto.class,
                    qCoupon.couponNo, qCoupon.name, qCoupon.code,
                    qCouponType.couponTypeEnum.stringValue().as("couponType"),
                    qCoupon.point, qCoupon.amount, qCoupon.percent,
                    qProduct.productNo, qProduct.name.as("productName"),
                    qCategory.categoryNo, qCategory.categoryName,
                    qParentCategory.categoryName.as("parentCategoryName"),
                    qCoupon.couponCreatedAt,
                    qCoupon.couponExpiredAt)).as("coupon"))
            .join(qCoupon.couponType, qCouponType)
            .leftJoin(qProductCoupon).on(qCoupon.couponNo.eq(qProductCoupon.couponNo))
            .leftJoin(qProduct).on(qProductCoupon.product.productNo.eq(qProduct.productNo))
            .leftJoin(qCategoryCoupon).on(qCoupon.couponNo.eq(qCategoryCoupon.couponNo))
            .leftJoin(qCategory).on(qCategoryCoupon.category.categoryNo.eq(qCategory.categoryNo))
            .leftJoin(qParentCategory)
            .on(qCategory.parentCategory.categoryNo.eq(qParentCategory.categoryNo))
            .where(qCoupon.couponExpiredAt.after(LocalDateTime.now()))
            .where(qCoupon.couponCreatedAt.before(LocalDateTime.now()))
            .orderBy(qMembershipCoupon.membership.membershipStandardAmount.desc())
            .fetch();

    }

}
