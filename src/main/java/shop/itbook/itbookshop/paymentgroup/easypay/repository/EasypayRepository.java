package shop.itbook.itbookshop.paymentgroup.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.paymentgroup.easypay.entity.Easypay;

/**
 * easypay 엔티티의 Repository 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface EasypayRepository extends JpaRepository<Easypay, Long> {

}
