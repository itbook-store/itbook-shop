package shop.itbook.itbookshop.productgroup.productrelationgroup.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.productrelationgroup.dto.response.ProductRelationResponseDto;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.ProductRelationGroup;
import shop.itbook.itbookshop.productgroup.productrelationgroup.entity.QProductRelationGroup;
import shop.itbook.itbookshop.productgroup.productrelationgroup.repository.ProductRelationGroupRepositoryCustom;

/**
 * ProductRelationGroupRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductRelationGroupRepositoryImpl extends QuerydslRepositorySupport
    implements ProductRelationGroupRepositoryCustom {

    public ProductRelationGroupRepositoryImpl() {
        super(ProductRelationGroup.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getRelationProductNoListWithBasedProductNoAdmin(Long basedProductNo) {
        QProductRelationGroup qProductRelationGroup = QProductRelationGroup.productRelationGroup;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<Long> productNoListQuery =
            from(qProductRelationGroup)
                .innerJoin(qProductRelationGroup.basedProduct, qProduct)
                .leftJoin(qProduct.book, qBook)
                .select(qProductRelationGroup.product.productNo)
                .where(qProductRelationGroup.basedProduct.productNo.eq(basedProductNo));

        return productNoListQuery.fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getProductNoListToAddRelationAdmin(Long basedProductNo) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<Long> productNoListQuery =
            from(qProduct)
                .leftJoin(qProduct.book, qBook)
                .select(qProduct.productNo)
                .where(qProduct.productNo.ne(basedProductNo));

        return productNoListQuery.fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> getRelationProductNoListWithBasedProductNoUser(Long basedProductNo) {
        QProductRelationGroup qProductRelationGroup = QProductRelationGroup.productRelationGroup;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<Long> productNoListQuery =
            from(qProductRelationGroup)
                .innerJoin(qProductRelationGroup.basedProduct, qProduct)
                .leftJoin(qProduct.book, qBook)
                .select(qProductRelationGroup.product.productNo)
                .where(qProductRelationGroup.basedProduct.productNo.eq(basedProductNo))
                .where(qProductRelationGroup.isDeleted.eq(false));

        return productNoListQuery.fetch();
    }

    @Override
    public Page<ProductRelationResponseDto> getAllBasedProductNoListAdmin(Pageable pageable) {
        QProductRelationGroup qProductRelationGroup = QProductRelationGroup.productRelationGroup;
        QProduct qProduct = QProduct.product;

        JPQLQuery<ProductRelationResponseDto> productListQuery =
            getProductListQuery(qProductRelationGroup, qProduct);

        List<ProductRelationResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> getProductListQuery(qProductRelationGroup, qProduct).fetch()
                .size());
    }

    private JPQLQuery<ProductRelationResponseDto> getProductListQuery(
        QProductRelationGroup qProductRelationGroup, QProduct qProduct) {
        return from(qProduct)
            .leftJoin(qProductRelationGroup)
            .on(qProductRelationGroup.basedProduct.productNo.eq(qProduct.productNo))
            .select(Projections.constructor(ProductRelationResponseDto.class,
                qProduct.productNo, qProduct.name,
                qProductRelationGroup.basedProduct.productNo.count()))
            .groupBy(qProduct.productNo)
            .orderBy(qProductRelationGroup.basedProduct.productNo.count().desc(),
                qProduct.productNo.desc());
    }
}
