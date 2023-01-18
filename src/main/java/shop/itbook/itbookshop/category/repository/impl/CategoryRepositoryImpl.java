package shop.itbook.itbookshop.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.category.dto.response.CategoryListResponseDto;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.entity.QCategory;
import shop.itbook.itbookshop.category.repository.CustomCategoryRepository;
import shop.itbook.itbookshop.productgroup.productcategory.entity.QProductCategory;

/**
 * CustomCategoryRepository 의 기능들을 구현하기 위해서 만든 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class CategoryRepositoryImpl extends QuerydslRepositorySupport implements
    CustomCategoryRepository {

    private static final Integer MAIN_CATEGORY_LEVEL = 0;

    public CategoryRepositoryImpl() {
        super(Category.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryListResponseDto> findCategoryListByEmployee() {

        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return getCategoryListJPQLQuery(qCategory, qParentCategory, qProductCategory)
                    .fetch();
    }

    public List<CategoryListResponseDto> findCategoryListByNotEmployee() {

        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return getCategoryListJPQLQuery(qCategory, qParentCategory, qProductCategory)
                   .where(qCategory.isHidden.eq(false)
                        .and(qParentCategory.isHidden.eq(false)))
                    .fetch();
    }

    private JPQLQuery<CategoryListResponseDto> getCategoryListJPQLQuery(QCategory qCategory, QCategory qParentCategory,
                                                         QProductCategory qProductCategory) {
        return from(qCategory)
                .leftJoin(qParentCategory)
                    .on(qCategory.categoryNo.eq(qParentCategory.parentCategory.categoryNo))
                .leftJoin(qProductCategory)
                    .on(qProductCategory.category.categoryNo.eq(qParentCategory.categoryNo))
                .where(qCategory.level.eq(MAIN_CATEGORY_LEVEL))
                .groupBy(qCategory.categoryNo, qParentCategory.categoryNo)
                .orderBy(qCategory.sequence.asc(), qParentCategory.level.asc(),
                    qParentCategory.sequence.asc())
                .select(Projections.fields(CategoryListResponseDto.class,
                    qParentCategory.categoryNo,
                    qParentCategory.parentCategory.categoryNo.as("parent_category_no"),
                    qParentCategory.categoryName,
                    qParentCategory.isHidden,
                    qParentCategory.level,
                    qParentCategory.sequence,
                    qProductCategory.category.categoryNo.count().as("count")));
    }


    /**
     * {@inheritDoc}
     */
    public List<CategoryListResponseDto> findCategoryListAboutChild(
        Integer parentCategoryNo) {
        QCategory qCategory = QCategory.category;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return from(qCategory)
            .leftJoin(qProductCategory)
                .on(qProductCategory.category.categoryNo.eq(qCategory.categoryNo))
            .where(qCategory.parentCategory.categoryNo.eq(parentCategoryNo))
            .orderBy(qCategory.level.asc(), qCategory.sequence.asc())
            .groupBy(qCategory.categoryNo)
            .select(Projections.fields(CategoryListResponseDto.class,
                qCategory.categoryNo,
                qCategory.parentCategory.categoryNo.as("parent_category_no"),
                qCategory.categoryName,
                qCategory.isHidden,
                qCategory.level,
                qCategory.sequence,
                qProductCategory.category.categoryNo.count().as("count")
            ))
            .fetch();
    }

    @Override
    public List<CategoryListResponseDto> findMainCategoryList() {
        QCategory qCategory = QCategory.category;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return from(qCategory)
            .leftJoin(qProductCategory)
                .on(qProductCategory.category.categoryNo.eq(qCategory.categoryNo))
            .where(qCategory.level.eq(MAIN_CATEGORY_LEVEL))
            .orderBy(qCategory.sequence.asc())
            .groupBy(qCategory.categoryNo)
            .select(Projections.fields(CategoryListResponseDto.class,
                qCategory.categoryNo,
                qCategory.parentCategory.categoryNo.as("parent_category_no"),
                qCategory.categoryName,
                qCategory.isHidden,
                qCategory.level,
                qCategory.sequence,
                qProductCategory.category.categoryNo.count().as("count")
            ))
            .fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findCategoryFetch(Integer categoryNo) {

        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");

        Optional<Category> category = Optional.of(from(qCategory)
            .leftJoin(qCategory.parentCategory, qParentCategory)
            .fetchJoin()
            .where(qCategory.categoryNo.eq(categoryNo))
            .select(qCategory)
            .fetchOne());
        return category;
    }
}
