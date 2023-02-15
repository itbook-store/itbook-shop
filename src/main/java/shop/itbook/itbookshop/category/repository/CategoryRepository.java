package shop.itbook.itbookshop.category.repository;

import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * Category 와 관련한 DB 기능처리를 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryRepository
    extends JpaRepository<Category, Integer>, CustomCategoryRepository {

    @Modifying
    @Query("update Category c set c.sequence = c.sequence + 1 where c.parentCategory.categoryNo = :parentCategoryNo and c.level = 1 and c.sequence >= :sequence")
    void modifyChildCategorySequence(Integer parentCategoryNo, Integer sequence);

    @Modifying
    @Query("update Category c set c.sequence = c.sequence + 1 where c.level = 0 and c.sequence >= :sequence")
    void modifyMainCategorySequence(Integer sequence);

    Optional<Category> findByCategoryNameAndLevel(String categoryName, int mainCategoryLevel);
}
