package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * Product Repository입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductNo(Long productNo);

    void deleteByProductNo(Long productNo);

}
