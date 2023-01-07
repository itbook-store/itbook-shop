package shop.itbook.itbookshop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * Category 와 관련한 DB 기능처리를 담당하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
