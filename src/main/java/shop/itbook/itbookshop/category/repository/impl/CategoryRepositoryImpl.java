package shop.itbook.itbookshop.category.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.category.dto.CategoryNoAndProductNoDto;
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
    public Page<CategoryListResponseDto> findCategoryListByEmployee(Pageable pageable) {

        QCategory qCategory = QCategory.category;
        QCategory qChildCategory = new QCategory("parent_category");
        QProductCategory qProductCategory = QProductCategory.productCategory;

        JPQLQuery<CategoryListResponseDto> categoryListJPQLQuery =
            getCategoryListJPQLQuery(qCategory, qChildCategory, qProductCategory);

        List<CategoryListResponseDto> content =
            categoryListJPQLQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return PageableExecutionUtils.getPage(content, pageable,
            () -> from(qCategory).fetchCount());
    }

    /**
     * @param pageable
     * @return
     */
    public Page<CategoryListResponseDto> findCategoryListByNotEmployee(Pageable pageable) {

        QCategory qCategory = QCategory.category;
        QCategory qChildCategory = new QCategory("parentCategory");
        QProductCategory qProductCategory = QProductCategory.productCategory;

        JPQLQuery<CategoryListResponseDto> jpqlQuery =
            getCategoryListJPQLQuery(qCategory, qChildCategory, qProductCategory)
                .where(qCategory.isHidden.eq(false)
                    .and(qChildCategory.isHidden.eq(false)));

        List<CategoryListResponseDto> categoryList =
            jpqlQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(categoryList, pageable, jpqlQuery::fetchCount);
    }

    private JPQLQuery<CategoryListResponseDto> getCategoryListJPQLQuery(QCategory qCategory,
                                                                        QCategory qChildCategory,
                                                                        QProductCategory qProductCategory) {
        return from(qCategory)
            .leftJoin(qChildCategory)
            .on(qCategory.categoryNo.eq(qChildCategory.parentCategory.categoryNo))
            .leftJoin(qProductCategory)
            .on(qProductCategory.category.categoryNo.eq(qChildCategory.categoryNo))
            .where(qCategory.level.eq(MAIN_CATEGORY_LEVEL))
            .groupBy(qCategory.categoryNo, qChildCategory.categoryNo)
            .orderBy(qCategory.sequence.asc(), qChildCategory.level.asc(),
                qChildCategory.sequence.asc())
            .select(Projections.fields(CategoryListResponseDto.class,
                qChildCategory.categoryNo,
                qChildCategory.parentCategory.categoryNo.as("parent_category_no"),
                qChildCategory.categoryName,
                qChildCategory.isHidden,
                qChildCategory.level,
                qChildCategory.sequence,
                qProductCategory.category.categoryNo.count().as("count")));
    }


    /**
     * {@inheritDoc}
     */
    public Page<CategoryListResponseDto> findCategoryListAboutChild(
        Integer parentCategoryNo, Pageable pageable) {
        QCategory qCategory = QCategory.category;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        JPQLQuery<Category> jpqlQuery = from(qCategory)
            .where(qCategory.parentCategory.categoryNo.eq(parentCategoryNo)
                .and(qCategory.categoryNo.eq(parentCategoryNo).not()));

        List<CategoryListResponseDto> childCategoryList = jpqlQuery
            .leftJoin(qProductCategory)
            .on(qProductCategory.category.categoryNo.eq(qCategory.categoryNo))
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
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(childCategoryList, pageable, jpqlQuery::fetchCount);
    }

    @Override
    public Page<CategoryListResponseDto> findMainCategoryList(Pageable pageable) {
        QCategory qCategory = QCategory.category;

        JPQLQuery<Category> jpqlQuery = from(qCategory)
            .where(qCategory.level.eq(MAIN_CATEGORY_LEVEL));

        List<CategoryListResponseDto> mainCategoryList = jpqlQuery
            .orderBy(qCategory.sequence.asc())
            .select(Projections.fields(CategoryListResponseDto.class,
                qCategory.categoryNo,
                qCategory.parentCategory.categoryNo.as("parentCategoryNo"),
                qCategory.categoryName,
                qCategory.isHidden,
                qCategory.level,
                qCategory.sequence
            ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(mainCategoryList, pageable, jpqlQuery::fetchCount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Category> findCategoryFetch(Integer categoryNo) {

        QCategory qCategory = QCategory.category;
        QCategory qParentCategory = new QCategory("parentCategory");

        return Optional.of(from(qCategory)
            .innerJoin(qCategory.parentCategory, qParentCategory)
            .fetchJoin()
            .where(qCategory.categoryNo.eq(categoryNo))
            .select(qCategory)
            .fetchOne());
    }

    @Override
    public List<CategoryNoAndProductNoDto> getMainCategoryNoAndProductNoForSettingCount(
        List<Integer> mainCategoryNoList) {

        QCategory qCategory = QCategory.category;
        QProductCategory qProductCategory = QProductCategory.productCategory;

        return from(qProductCategory)
            .innerJoin(qCategory)
            .on(qProductCategory.category.categoryNo.eq(qCategory.categoryNo))
            .groupBy(qProductCategory.product.productNo, qCategory.parentCategory.categoryNo)
            .where(qCategory.parentCategory.categoryNo.in(mainCategoryNoList))
            .select(Projections.fields(CategoryNoAndProductNoDto.class,
                qProductCategory.product.productNo,
                qCategory.parentCategory.categoryNo
            ))
            .fetch();
    }

    @Override
    public CategoryNoAndProductNoDto getMainCategoryNoAndProductNoDtoForContainsProducts(
        Integer categoryNo) {

        QProductCategory qProductCategory = QProductCategory.productCategory;
        QCategory qCategory = QCategory.category;

        return from(qProductCategory)
            .innerJoin(qCategory)
            .on(qProductCategory.category.categoryNo.eq(qCategory.categoryNo))
            .where(qCategory.parentCategory.categoryNo.eq(categoryNo))
            .groupBy(qCategory.parentCategory.categoryNo)
            .select(Projections.fields(CategoryNoAndProductNoDto.class,
                qCategory.parentCategory.categoryNo))
            .fetchOne();
    }

    @Override
    public List<CategoryNoAndProductNoDto> getSubCategoryNoAndProductNoDtoForContainsProducts(
        Integer categoryNo) {

        QProductCategory qProductCategory = QProductCategory.productCategory;
        QCategory qCategory = QCategory.category;

        return from(qProductCategory)
            .where(qProductCategory.category.categoryNo.eq(categoryNo))
            .select(Projections.fields(CategoryNoAndProductNoDto.class,
                qProductCategory.product.productNo,
                qCategory.categoryNo))
            .fetch();
    }
}
