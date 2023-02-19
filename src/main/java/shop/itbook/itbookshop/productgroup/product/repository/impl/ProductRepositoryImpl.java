package shop.itbook.itbookshop.productgroup.product.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductSalesRankResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepositoryCustom;

/**
 * ProductRepositoryCustom을 구현한 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
public class ProductRepositoryImpl extends QuerydslRepositorySupport
    implements ProductRepositoryCustom {
    public ProductRepositoryImpl() {
        super(Product.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice,
                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
                    qProduct.dailyHits))
                .orderBy(qProduct.productNo.desc());

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice,
                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
                    qProduct.dailyHits))
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE))
                .orderBy(qProduct.productNo.desc());

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductDetailsResponseDto> findProductDetails(Long productNo) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> product =
            from(qBook)
                .rightJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice,
                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
                    qProduct.dailyHits))
                .where(qProduct.productNo.eq(productNo));
        return Optional.ofNullable(product.fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductNoListForUser(Pageable pageable,
                                                                                 List<Long> productNoList) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getProductListByProductNoList(productNoList, qProduct, qBook)
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable, productListQuery::fetchCount);
    }

    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductNoListForAdmin(Pageable pageable,
                                                                                  List<Long> productNoList) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getProductListByProductNoList(productNoList, qProduct, qBook);

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable, productListQuery::fetchCount);
    }

    private JPQLQuery<ProductDetailsResponseDto> getProductListByProductNoList(
        List<Long> productNoList,
        QProduct qProduct, QBook qBook) {
        return from(qBook)
            .rightJoin(qBook.product, qProduct)
            .select(Projections.constructor(ProductDetailsResponseDto.class,
                qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                qProduct.isPointApplyingBasedSellingPrice,
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
                qProduct.dailyHits))
            .where(qProduct.productNo.in(productNoList));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductSalesRankResponseDto> findCompleteRankProducts(Pageable pageable) {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;
        QProduct qProduct = QProduct.product;

        JPQLQuery<ProductSalesRankResponseDto> productSalesRankQuery =
            from(qOrderStatusHistory)
                .innerJoin(qOrderProduct).on(qOrderStatusHistory.order.eq(qOrderProduct.order))
                .innerJoin(qOrderProduct.product, qProduct)
                .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
                .select(
                    Projections.constructor(ProductSalesRankResponseDto.class, qProduct.productNo,
                        qProduct.name, qOrderProduct.count.sum(),
                        qOrderProduct.productPrice.sum()))
                .where(qOrderStatus.orderStatusEnum.eq(OrderStatusEnum.PAYMENT_COMPLETE))
                .groupBy(qOrderProduct.product)
                .orderBy(qOrderProduct.count.sum().desc(), qOrderProduct.productPrice.sum().desc());

        List<ProductSalesRankResponseDto> productList = productSalesRankQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductSalesRankResponseDto> findCanceledRankProducts(Pageable pageable) {
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;
        QProduct qProduct = QProduct.product;

        JPQLQuery<ProductSalesRankResponseDto> productSalesRankQuery =
            from(qOrderStatusHistory)
                .innerJoin(qOrderProduct).on(qOrderStatusHistory.order.eq(qOrderProduct.order))
                .innerJoin(qOrderProduct.product, qProduct)
                .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
                .select(Projections.constructor(ProductSalesRankResponseDto.class,
                    qProduct.productNo, qProduct.name, qOrderProduct.count.sum(),
                    qOrderProduct.productPrice.sum()))
                .where(qOrderStatus.orderStatusEnum.eq(OrderStatusEnum.REFUND_COMPLETED)
                    .or(qOrderStatus.orderStatusEnum.eq(OrderStatusEnum.CANCELED)))
                .groupBy(qOrderProduct.product)
                .orderBy(qOrderProduct.count.sum().desc(), qOrderProduct.productPrice.sum().desc());

        List<ProductSalesRankResponseDto> productList = productSalesRankQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductSalesRankResponseDto> findSelledPriceRankProducts(Pageable pageable) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;
        QProduct qProduct = QProduct.product;

        NumberPath<Long> oneHundred = Expressions.numberPath(Long.class, "100");
        NumberPath<Double> decimal = Expressions.numberPath(Double.class, "0.01");

        NumberExpression<Long> sellingPrice = qProduct.fixedPrice.multiply(
            ((oneHundred.subtract(qProduct.discountPercent)).multiply(decimal))
                .longValue());


        JPQLQuery<ProductSalesRankResponseDto> productSalesRankQuery =
            from(qOrderProduct)
                .innerJoin(qOrderProduct.product, qProduct)
                .innerJoin(qOrderStatusHistory)
                .on(qOrderStatusHistory.order.eq(qOrderProduct.order))
                .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
                .select(Projections.constructor(ProductSalesRankResponseDto.class,
                    qOrderProduct.product.productNo, qOrderProduct.product.name,
                    qOrderProduct.count.sum(),
                    sellingPrice.multiply(qOrderProduct.count.sum())))
                .where(qOrderStatus.orderStatusEnum.eq(OrderStatusEnum.PURCHASE_COMPLETE))
                .groupBy(qOrderProduct.product)
                .orderBy((sellingPrice.multiply(qOrderProduct.count.sum())).desc(),
                    qOrderProduct.count.sum().desc());

        List<ProductSalesRankResponseDto> productList = productSalesRankQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qOrderProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductSalesRankResponseDto> findTotalSalesRankProducts(Pageable pageable) {

        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        JPQLQuery<ProductSalesRankResponseDto> productSalesRankQuery =
            from(qOrderProduct)
                .innerJoin(qOrderStatusHistory)
                .on(qOrderStatusHistory.order.eq(qOrderProduct.order))
                .innerJoin(qOrderStatusHistory.orderStatus, qOrderStatus)
                .select(Projections.constructor(ProductSalesRankResponseDto.class,
                    qOrderProduct.product.productNo, qOrderProduct.product.name,
                    qOrderProduct.count.sum(), qOrderProduct.productPrice.sum()))
                .where(qOrderStatus.orderStatusEnum.eq(OrderStatusEnum.PURCHASE_COMPLETE))
                .groupBy(qOrderProduct.product)
                .orderBy(qOrderProduct.productPrice.sum().desc(),
                    qOrderProduct.count.sum().desc());

        List<ProductSalesRankResponseDto> productList = productSalesRankQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qOrderProduct).fetchCount());
    }
}
