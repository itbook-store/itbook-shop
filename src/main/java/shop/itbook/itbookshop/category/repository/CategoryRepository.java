package shop.itbook.itbookshop.category.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.itbook.itbookshop.category.dto.response.CategoryResponseProjectionDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * Category 와 관련한 DB 기능처리를 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c")
    List<CategoryResponseProjectionDto> findCategoryList();

    @SuppressWarnings("java:S100")
        // JPA 이름규칙에 의거 메소드 생성
    List<CategoryResponseProjectionDto> findAllByParentCategory_CategoryNo(
        Integer parentCategoryNo);
}
