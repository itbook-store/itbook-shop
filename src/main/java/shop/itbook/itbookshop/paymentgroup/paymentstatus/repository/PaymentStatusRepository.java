package shop.itbook.itbookshop.paymentgroup.paymentstatus.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.entity.PaymentStatus;
import shop.itbook.itbookshop.paymentgroup.paymentstatus.paymentstatusenum.PaymentStatusEnum;

/**
 * 결제상태 엔티티의 Repository 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    Optional<PaymentStatus> findPaymentStatusByPaymentStatusEnum(
        PaymentStatusEnum paymentStatusEnum);

}
