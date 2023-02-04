package shop.itbook.itbookshop.productgroup.producttyperegistration.repository.impl;

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
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.QProductTypeRegistration;
import shop.itbook.itbookshop.productgroup.producttyperegistration.repository.ProductTypeRegistrationRepositoryCustom;

/**
 * @author 이하늬
 * @since 1.0
 */
public class ProductTypeRegistrationRepositoryImpl extends QuerydslRepositorySupport
    implements ProductTypeRegistrationRepositoryCustom {
    public ProductTypeRegistrationRepositoryImpl() {
        super(ProductTypeRegistration.class);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public Page<FindProductTypeResponseDto> findProductTypeListWithProductNo(Pageable pageable,
                                                                             Long productNo) {

        QProductTypeRegistration qProductTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProductType qProductType = QProductType.productType;

        JPQLQuery<FindProductTypeResponseDto> productTypeListQuery =
            from(qProductTypeRegistration)
                .innerJoin(qProductTypeRegistration.productType, qProductType)
                .select(Projections.constructor(FindProductTypeResponseDto.class,
                    qProductTypeRegistration.productType.productTypeEnum))
                .where(qProductTypeRegistration.product.productNo.eq(productNo));

        List<FindProductTypeResponseDto> productTypeList = productTypeListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productTypeList, pageable,
            () -> from(qProductType).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListUserWithProductTypeNo(Pageable pageable,
                                                                                Integer productTypeNo) {
        QProductTypeRegistration qProductTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProductType qProductType = QProductType.productType;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;


        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getProductTypeByProductNo(productTypeNo, qProductTypeRegistration, qProductType,
                qProduct, qBook)
                .where(qProduct.isSelled.eq(Boolean.TRUE));

        List<ProductDetailsResponseDto> productList =
            productListQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListAdminWithProductTypeNo(Pageable pageable,
                                                                                 Integer productTypeNo) {
        QProductTypeRegistration qProductTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProductType qProductType = QProductType.productType;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;


        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getProductTypeByProductNo(productTypeNo, qProductTypeRegistration, qProductType,
                qProduct, qBook);

        List<ProductDetailsResponseDto> productList =
            productListQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
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
                .where(qProduct.isSelled.eq(Boolean.TRUE));

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
                .where(qProduct.isSelled.eq(true));

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

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrder)
                .where(qProduct.isSelled.eq(true));

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
        QOrder qOrder = QOrder.order;

        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            getBestSeller(qProduct, qBook, qOrderProduct, qOrder);

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }

    // duplicated Code

    private JPQLQuery<ProductDetailsResponseDto> getProductTypeByProductNo(Integer productTypeNo,
                                                                           QProductTypeRegistration qProductTypeRegistration,
                                                                           QProductType qProductType,
                                                                           QProduct qProduct,
                                                                           QBook qBook) {
        return from(qProductTypeRegistration)
            .innerJoin(qProductTypeRegistration.productType, qProductType)
            .innerJoin(qProductTypeRegistration.product, qProduct)
            .innerJoin(qProduct.book, qBook)
            .select(Projections.constructor(ProductDetailsResponseDto.class,
                qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                qProduct.isPointApplyingBasedSellingPrice,
                qProduct.isPointApplying, qProduct.isSubscription))
            .where(qProductTypeRegistration.productType.productTypeNo.eq(productTypeNo));
    }

    private JPQLQuery<ProductDetailsResponseDto> getBestSeller(QProduct qProduct, QBook qBook,
                                                               QOrderProduct qOrderProduct,
                                                               QOrder qOrder) {
        return from(qBook)
            .innerJoin(qBook.product, qProduct)
            .innerJoin(qOrderProduct.product, qProduct)
            .innerJoin(qOrder)
            .on(qOrder.eq(qOrderProduct.order))
            .select(Projections.constructor(ProductDetailsResponseDto.class,
                qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                qProduct.detailsDescription, qProduct.isSelled, qProduct.isForceSoldOut,
                qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                qBook.ebookUrl, qBook.publisherName, qBook.authorName,
                qProduct.isPointApplyingBasedSellingPrice,
                qProduct.isPointApplying, qProduct.isSubscription))
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
                qProduct.isPointApplying, qProduct.isSubscription))
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
                qProduct.isPointApplying, qProduct.isSubscription))
            .where(qBook.bookCreatedAt.after(LocalDateTime.now().minusDays(7)));
    }
}
