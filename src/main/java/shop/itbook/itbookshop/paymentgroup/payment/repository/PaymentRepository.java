package shop.itbook.itbookshop.paymentgroup.payment.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.paymentgroup.payment.entity.Payment;

/**
 * 결제 엔티티의 Repository 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {
    Optional<Payment> findPaymentByOrder_OrderNo(Long orderNo);
}
