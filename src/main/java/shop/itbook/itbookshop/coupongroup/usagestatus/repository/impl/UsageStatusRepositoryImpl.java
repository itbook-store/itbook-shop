package shop.itbook.itbookshop.coupongroup.usagestatus.repository.impl;

import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.QUsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.CustomUsageStatusRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
public class UsageStatusRepositoryImpl extends QuerydslRepositorySupport
    implements CustomUsageStatusRepository {

    public UsageStatusRepositoryImpl() {
        super(UsageStatus.class);
    }

    @Override
    public Optional<UsageStatus> findByUsageStatusName(String statusName) {
        QUsageStatus qUsageStatus = QUsageStatus.usageStatus;
        return Optional.of(
            from(qUsageStatus).select(qUsageStatus)
                .where(qUsageStatus.usageStatusEnum.stringValue().eq(statusName)).fetchOne());
    }
}
