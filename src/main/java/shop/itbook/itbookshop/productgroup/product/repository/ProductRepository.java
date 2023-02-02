package shop.itbook.itbookshop.productgroup.product.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;

/**
 * 상품 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findById(Long productNo);

    void deleteById(Long productNo);

}
