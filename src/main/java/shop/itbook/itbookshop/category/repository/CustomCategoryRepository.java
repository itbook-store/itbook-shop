package shop.itbook.itbookshop.category.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryChildResponseProjectionDto;
import shop.itbook.itbookshop.category.dto.response.CategoryWithoutParentFieldResponseDto;
import shop.itbook.itbookshop.category.entity.Category;

/**
 * 쿼리 dsl 을 처리하기 위한 레포지토리 인터페이스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomCategoryRepository {

    /**
     * 모든 카테고리리스트를 반환하는 기능을 담당합니다.
     *
     * @return 모든 카테고리 리스트를 반환합니다.
     * @author 최겸준
     */
    List<CategoryAllFieldResponseDto> findCategoryListFetch();

    /**
     * 부모카테고리를 통해서 자식카테고리들을 찾는 기능을 담당합니다.
     * 이곳에서는 부모카테고리의 정보가 필요없기때문에 lazy loading 을 그대로 이용합니다.
     *
     * @param parentCategoryNo 부모카테고리의 번호입니다.
     * @return 부모카테고리의 정보를 제외한 자식카테고리들의 정보를 반환합니다.
     */
    List<CategoryWithoutParentFieldResponseDto> findCategoryChildListThroughParentCategoryNo(
        Integer parentCategoryNo);

    /**
     * 카테고리를 조회할때 부모카테고리까지 조인하여 조회합니다.
     *
     * @param categoryNo 조회하고자하는 카테고리의 번호입니다.
     * @return 카테고리엔티티를 옵셔널하게 반환합니다.
     */
    Optional<Category> findCategoryFetch(Integer categoryNo);
}
