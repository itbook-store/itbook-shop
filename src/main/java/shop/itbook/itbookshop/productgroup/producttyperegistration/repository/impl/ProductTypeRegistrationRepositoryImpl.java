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
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.producttype.entity.QProductType;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
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
    public Page<ProductDetailsResponseDto> findProductListWithProductTypeNo(Pageable pageable,
                                                                            Integer productTypeNo,
                                                                            Boolean isExposed) {
        QProductTypeRegistration qProductTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;


        JPQLQuery<ProductDetailsResponseDto> productListQuery =
            from(qProductTypeRegistration)
                .innerJoin(qProductTypeRegistration.product, qProduct)
                .innerJoin(qProduct, qBook.product)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProductTypeRegistration.productType.productTypeNo.eq(productTypeNo)
                    .and(qProduct.isExposed).eq(isExposed));

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
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qBook.bookCreatedAt.after(LocalDateTime.now().minusDays(7)))
                .where(qProduct.isExposed.eq(true));

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
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qBook.bookCreatedAt.after(LocalDateTime.now().minusDays(7)));

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
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProduct.discountPercent.ne(0.0))
                .where(qProduct.isExposed.eq(true));

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
            from(qBook)
                .innerJoin(qBook.product, qProduct)
                .select(Projections.constructor(ProductDetailsResponseDto.class,
                    qProduct.productNo, qProduct.name, qProduct.simpleDescription,
                    qProduct.detailsDescription, qProduct.isExposed, qProduct.isForceSoldOut,
                    qProduct.stock, qProduct.increasePointPercent, qProduct.rawPrice,
                    qProduct.fixedPrice, qProduct.discountPercent, qProduct.thumbnailUrl,
                    qBook.isbn, qBook.pageCount, qBook.bookCreatedAt, qBook.isEbook,
                    qBook.ebookUrl, qBook.publisherName, qBook.authorName))
                .where(qProduct.discountPercent.ne(0.0));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qBook).fetchCount());
    }
}
