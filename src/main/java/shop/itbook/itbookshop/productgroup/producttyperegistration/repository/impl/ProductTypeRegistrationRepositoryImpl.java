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
                .where(qProduct.isSelled.eq(Boolean.TRUE))
                .where(qProduct.isDeleted.eq(Boolean.FALSE));

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
                qProduct.isPointApplying, qProduct.isSubscription, qProduct.isDeleted))
            .where(qProductTypeRegistration.productType.productTypeNo.eq(productTypeNo));
    }
    
}
