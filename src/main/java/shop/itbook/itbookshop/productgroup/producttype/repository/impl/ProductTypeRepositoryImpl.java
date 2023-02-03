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
import shop.itbook.itbookshop.ordergroup.ordersubscription.entity.QOrderSubscription;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.producttype.entity.QProductType;
import shop.itbook.itbookshop.productgroup.producttype.repository.ProductTypeRepositoryCustom;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.QProductTypeRegistration;

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
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;
        QOrder qOrder = QOrder.order;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrderSubscription, qOrder)
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
    public Page<ProductDetailsResponseDto> findBestSellerBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QOrderSubscription qOrderSubscription = QOrderSubscription.orderSubscription;
        QOrder qOrder = QOrder.order;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrderSubscription, qOrder);

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
    public Page<ProductDetailsResponseDto> findRecommendationBookListAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;


        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                    qProduct.isPointApplyingBasedSellingPrice,
                    qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    // duplication code

    private JPQLQuery<ProductDetailsResponseDto> getBestSeller(QProduct qProduct, QBook qBook,
                                                               QOrderProduct qOrderProduct,
                                                               QOrderSubscription qOrderSubscription,
                                                               QOrder qOrder) {
        return from(qBook)
            .innerJoin(qBook.product, qProduct)
            .innerJoin(qOrderProduct.product, qProduct)
            .innerJoin(qOrderSubscription.product, qProduct)
            .innerJoin(qOrder)
            .on(qOrder.eq(qOrderProduct.order).and(qOrder.eq(qOrderSubscription.order)))
            .select(Projections.constructor(ProductDetailsResponseDto.class,
                qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                qProduct.isPointApplyingBasedSellingPrice,
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted))
            .where(qOrder.orderCreatedAt.between(LocalDateTime.now().minusDays(31),
                LocalDateTime.now().minusDays(1)));
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
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted))
            .where(qProduct.discountPercent.ne(0.0));
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
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted))
            .where(qBook.bookCreatedAt.after(LocalDateTime.now().minusDays(7)));
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
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted))
            .orderBy(qProduct.dailyHits.desc())
            .limit(10);
    }
}
