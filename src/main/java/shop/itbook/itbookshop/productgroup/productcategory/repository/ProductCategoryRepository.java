package shop.itbook.itbookshop.productgroup.productcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;

/**
 * 상품-카테고리 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductCategoryRepository
    extends JpaRepository<ProductCategory, ProductCategory.Pk> {

    void deleteByPk_productNo(Long productNo);

}
