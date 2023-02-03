package shop.itbook.itbookshop.productgroup.product.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.book.entity.QBook;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;
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
                    qProduct.isPointApplying, qProduct.isSubscription));

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
                    qProduct.isPointApplying, qProduct.isSubscription))
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE));

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
                    qProduct.isPointApplying, qProduct.isSubscription))
                .where(qProduct.productNo.eq(productNo));
        return Optional.ofNullable(product.fetchOne());
    }

    //product no List 로 프로덕트 리스트 가져오기

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductDetailsResponseDto> findProductListByProductNoList(Pageable pageable,
                                                                          List<Long> productNoList) {
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
                    qProduct.isPointApplying, qProduct.isSubscription))
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE))
                .where(qProduct.productNo.in(productNoList));

        List<ProductDetailsResponseDto> productList = productListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }
}
