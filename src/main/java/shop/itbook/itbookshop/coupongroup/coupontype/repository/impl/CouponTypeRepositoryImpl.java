package shop.itbook.itbookshop.coupongroup.coupontype.repository.impl;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.QCouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CustomCouponTypeRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
public class CouponTypeRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCouponTypeRepository {
    public CouponTypeRepositoryImpl() {
        super(CouponType.class);
    }

    @Override
    public Optional<CouponType> findByCouponTypeName(String couponTypeName) {

        QCouponType qCouponType = QCouponType.couponType;

        return Optional.of(
            from(qCouponType).select(qCouponType)
                .where(qCouponType.couponTypeEnum.stringValue().eq(couponTypeName)).fetchOne());
    }
}
