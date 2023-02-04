package shop.itbook.itbookshop.productgroup.producttype.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductNoResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.producttype.dto.response.ProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttype.entity.QProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepositoryCustom;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductTypeRepositoryImpl extends QuerydslRepositorySupport
    implements ProductTypeRepositoryCustom {
    public ProductTypeRepositoryImpl() {
        super(ProductTypeRegistration.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductTypeResponseDto> findProductTypeList(Pageable pageable) {
        QProductType qProductType = QProductType.productType;

        JPQLQuery<ProductTypeResponseDto> productTypeListQuery =
            from(qProductType)
                .select(Projections.constructor(ProductTypeResponseDto.class,
                    qProductType.productTypeNo,
                    qProductType.productTypeEnum.stringValue()
                ));

        List<ProductTypeResponseDto> productTypeList = productTypeListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productTypeList, pageable,
            () -> from(qProductType).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findNewBookListUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getNewIssue(qProduct, qBook)
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findNewBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getNewIssue(qProduct, qBook);

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findDiscountBookListUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getDiscount(qProduct, qBook)
                .where(qProduct.isSelled.eq(true))
                .where(qProduct.isDeleted.eq(Boolean.FALSE));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findDiscountBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getDiscount(qProduct, qBook);

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findBestSellerBookListUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrder qOrder = QOrder.order;

        JPQLQuery<ProductDetailsResponseDto> productNoList =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrder);

//        JPQLQuery<ProductDetailsResponseDto> productListQuery =
//            from(qBook)
//                .rightJoin(qBook.product, qProduct)
//                .select(Projections.constructor(ProductDetailsResponseDto.class,
//                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
//                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
//                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
//                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
//                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
//                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
//                    qProduct.isPointApplyingBasedSellingPrice,
//                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
//                    qProduct.dailyHits))
//                .where(qProduct.productNo.in(productNoList))
//                .where(qProduct.isSelled.eq(Boolean.TRUE))
//                .where(qProduct.isDeleted.eq(Boolean.FALSE));

        List<ProductDetailsResponseDto> productList = productNoList
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findBestSellerBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrder qOrder = QOrder.order;

        JPQLQuery<ProductDetailsResponseDto> productNoList =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrder);

//        JPQLQuery<ProductDetailsResponseDto> productListQuery =
//            from(qBook)
//                .rightJoin(qBook.product, qProduct)
//                .select(Projections.constructor(ProductDetailsResponseDto.class,
//                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
//                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
//                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
//                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
//                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
//                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
//                    qProduct.isPointApplyingBasedSellingPrice,
//                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted,
//                    qProduct.dailyHits))
//                .where(qProduct.productNo.in(productNoList));

        List<ProductDetailsResponseDto> productList = productNoList
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findPopularityBookListUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getPopularity(qProduct, qBook)
                .where(qProduct.isDeleted.eq(Boolean.FALSE));


        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findPopularityBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getPopularity(qProduct, qBook);

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findRecommendationBookListUser(Pageable pageable) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findRecommendationBookListAdmin(
        Long recentlyPurchaseProductNo, Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;
        QOrder qOrder = QOrder.order;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            from(qOrder)
                .innerJoin(qOrderProduct.order, qOrder)
                .innerJoin(qOrderProduct.product, qProduct)
                .on(qProduct.productNo.eq(recentlyPurchaseProductNo))
                .innerJoin(qProduct.book, qBook)
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
                .where(qProduct.productNo.ne(recentlyPurchaseProductNo));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    // duplication code

    private JPQLQuery<ProductDetailsResponseDto> getBestSeller(QProduct qProduct, QBook qBook,
                                          QOrderProduct qOrderProduct,
                                          QOrder qOrder) {
        return from(qOrder)
            .innerJoin(qOrder.orderProducts, qOrderProduct)
            .innerJoin(qOrderProduct.product, qProduct)
            .innerJoin(qProduct.book, qBook)
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
            .where(qOrder.orderCreatedAt.between(LocalDateTime.now().minusDays(31),
                LocalDateTime.now().minusDays(1)))
            .groupBy(qProduct.productNo)
            .orderBy(qOrderProduct.count.sum().desc());

    }

    private JPQLQuery<ProductDetailsResponseDto> getDiscount(QProduct qProduct, QBook qBook) {
        return from(qBook)
            .innerJoin(qBook.product, qProduct)
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
            .where(qProduct.discountPercent.ne(0.0))
            .orderBy(qProduct.discountPercent.desc());
    }

    private JPQLQuery<ProductDetailsResponseDto> getNewIssue(QProduct qProduct, QBook qBook) {
        return from(qBook)
            .innerJoin(qBook.product, qProduct)
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
            .where(qBook.bookCreatedAt.after(LocalDateTime.now().minusDays(7)))
            .orderBy(qBook.bookCreatedAt.desc());
    }

    private JPQLQuery<ProductDetailsResponseDto> getPopularity(QProduct qProduct, QBook qBook) {
        return from(qBook)
            .innerJoin(qBook.product, qProduct)
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
            .orderBy(qProduct.dailyHits.desc());
    }
}
