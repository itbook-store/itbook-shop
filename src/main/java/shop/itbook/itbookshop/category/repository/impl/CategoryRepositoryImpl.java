package shop.itbook.itbookshop.category.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.category.dto.response.CategoryAllFieldResponseDto;
import shop.itbook.itbookshop.category.dto.response.CategoryWithoutParentFieldResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.category.repository.CustomCategoryRepository;

/**
 * CustomCategoryRepository 의 기능들을 구현하기 위해서 만든 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCategoryRepository {

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryAllFieldResponseDto> findCategoryListFetch(Boolean isHidden) {

        QCategory qChildCategory = new QCategory("childCategory");
        QCategory qParentCategory = new QCategory("parentCategory");


        return from(qChildCategory)
            .leftJoin(qChildCategory.parentCategory, qParentCategory)
            .select(Projections.fields(CategoryAllFieldResponseDto.class,
                qChildCategory.categoryNo,
                qChildCategory.categoryName,
                qChildCategory.isHidden,
                qChildCategory.parentCategory.categoryNo.as("parentCategoryNo"),
                qChildCategory.parentCategory.categoryName.as("parentCategoryName"),
                qChildCategory.parentCategory.isHidden.as("parentCategoryIsHidden")))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryWithoutParentFieldResponseDto> findCategoryChildListThroughParentCategoryNo(
        Integer parentCategoryNo, Boolean isHidden) {


        QCategory qCategory = QCategory.category;

        return from(qCategory)
            .where(qCategory.parentCategory.categoryNo.eq(parentCategoryNo))
            .select(Projections.fields(CategoryWithoutParentFieldResponseDto.class,
                qCategory.categoryNo,
                qCategory.categoryName,
                qCategory.isHidden))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findCategoryFetch(Integer categoryNo) {
        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");

        return Optional.of(from(qCategory)
            .leftJoin(qCategory.parentCategory, qParentCategory)
            .fetchJoin()
            .where(qCategory.categoryNo.eq(categoryNo))
            .select(qCategory)
            .fetchOne());
    }
}
