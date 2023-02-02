package shop.itbook.itbookshop.coupongroup.usagestatus.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;

/**
 * @author 송다혜
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomUsageStatusRepository {

    Optional<UsageStatus> findByUsageStatusName(String statusName);
}
