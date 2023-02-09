package shop.itbook.itbookshop.productgroup.review.repository;

import java.util.Optional;
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

    Optional<Review> findById(Long orderProductNo);
}
