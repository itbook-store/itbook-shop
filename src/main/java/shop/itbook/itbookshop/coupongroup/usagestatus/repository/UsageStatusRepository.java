package shop.itbook.itbookshop.coupongroup.usagestatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;

/**
 * @author 송다혜
 * @since 1.0
 */
@Repository
public interface UsageStatusRepository extends JpaRepository<UsageStatus, Integer>,
    CustomUsageStatusRepository {
}
