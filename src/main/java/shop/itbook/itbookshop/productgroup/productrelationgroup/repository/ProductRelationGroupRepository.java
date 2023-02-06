package shop.itbook.itbookshop.productgroup.productrelationgroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepositoryCustom;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;

/**
 * 연관상품 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductRelationGroupRepository extends JpaRepository<ProductRelationGroup, Long> {

    void deleteByBasedProduct_productNo(Long productNo);
}
