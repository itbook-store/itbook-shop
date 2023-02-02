package shop.itbook.itbookshop.coupongroup.usagestatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
public interface UsageStatusRepository extends JpaRepository<UsageStatusRepository, Integer>, CustomUsageStatusRepository {
}
