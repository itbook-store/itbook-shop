package shop.itbook.itbookshop.paymentgroup.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.paymentgroup.card.entity.Card;

/**
 * card 엔티티의 Repository 입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface CardRepository extends JpaRepository<Card, Long> {

}
