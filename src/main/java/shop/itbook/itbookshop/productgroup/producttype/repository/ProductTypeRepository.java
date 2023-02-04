package shop.itbook.itbookshop.productgroup.producttype.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.ProductType;

/**
 * 상품 유형 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public interface ProductTypeRepository
    extends JpaRepository<ProductType, Integer>, ProductTypeRepositoryCustom {
}
