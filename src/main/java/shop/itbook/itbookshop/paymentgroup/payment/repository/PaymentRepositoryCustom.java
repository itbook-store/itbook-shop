package shop.itbook.itbookshop.paymentgroup.payment.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 쿼리 dsl을 사용하기 위한 ProductRepository 인터페이스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@NoRepositoryBean
public interface PaymentRepositoryCustom {

    /**
     * <관리자> 주문 번호로 paymentKey를 조회해 반환하는 기능을 담당합니다.
     *
     * @param orderNo 조회할 주문 번호입니다.
     * @return 조회한 주문의 paymentKey를 반환합니다.
     * @author 이하늬
     */
    Optional<String> findPaymentKeyWithOrderNo(Long orderNo);

}
