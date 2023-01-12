package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;

/**
 * 상품유형등록 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductTypeRegistrationRepository
    extends JpaRepository<ProductTypeRegistration, ProductTypeRegistration.Pk>,
    ProductTypeRegistrationRepositoryCustom {
}
