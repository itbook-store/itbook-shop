package shop.itbook.itbookshop.productgroup.productcategory.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.itbook.itbookshop.category.dto.response.CategoryNoResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;

/**
 * 상품-카테고리 Repository 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Repository
public interface ProductCategoryRepository
    extends JpaRepository<ProductCategory, ProductCategory.Pk>, ProductCategoryRepositoryCustom {

    void deleteByPk_productNo(Long productNo);
    
}
