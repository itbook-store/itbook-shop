package shop.itbook.itbookshop.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * Category 와 관련한 DB 기능처리를 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c left outer join fetch c.parentCategory")
    List<CategoryResponseProjectionDto> findCategoryListFetch();

    @Query("select c from Category c where c.parentCategory.categoryNo = :parentCategoryNo")
    List<CategoryChildResponseProjectionDto> findCategoryListFetchThroughParentCategoryNo(
        Integer parentCategoryNo);

    @Query("select c from Category c "
        + "left outer join fetch c.parentCategory "
        + "where c.categoryNo=:categoryNo")
    Optional<Category> findCategoryFetch(Integer categoryNo);
}
