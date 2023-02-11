package shop.itbook.itbookshop.productgroup.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * 리뷰 repository 인터페이스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>, CustomReviewRepository {


    /**
     * 주문 상품 번호로 테이블에서 해당 번호가 존재하는지 찾는 메서드입니다.
     *
     * @param orderProductNo 테이블에 존재하는지 여부를 판단할 주문 상품번호입니다.
     * @return 있으면 true, 없으면 false를 반환합니다.
     * @author 노수연
     */
    Boolean existsByOrderProductNo(Long orderProductNo);
}
