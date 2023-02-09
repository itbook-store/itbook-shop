package shop.itbook.itbookshop.paymentgroup.paymentcancel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.paymentgroup.paymentcancel.entity.PaymentCancel;

/**
 * 결제 취소 엔티티의 Repository 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface PaymentCancelRepository extends JpaRepository<PaymentCancel, Long> {

}
