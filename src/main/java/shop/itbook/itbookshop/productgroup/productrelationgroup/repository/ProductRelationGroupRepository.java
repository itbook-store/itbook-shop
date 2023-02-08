package shop.itbook.itbookshop.productgroup.productrelationgroup.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepositoryCustom;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;

/**
 * 연관상품 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductRelationGroupRepository
    extends JpaRepository<ProductRelationGroup, Long>, ProductRelationGroupRepositoryCustom {

    void deleteByBasedProduct_productNo(Long productNo);

    ProductRelationGroup findProductRelationGroupByBasedProduct_ProductNoAndProduct_ProductNo(
        Long basedProductNo, Long productNo);

}
