package shop.itbook.itbookshop.productgroup.producttyperegistration.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
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

    @Override
    public Page<FindProductResponseDto> findProductListWithProductTypeNo(Pageable pageable,
                                                                         Integer productTypeNo,
                                                                         Boolean isExposed) {
        QProductTypeRegistration qProductTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProduct qProduct = QProduct.product;

        JPQLQuery<FindProductResponseDto> productListQuery =
            from(qProductTypeRegistration)
                .innerJoin(qProductTypeRegistration.product, qProduct)
                .select(Projections.constructor(FindProductResponseDto.class, qProduct))
                .where(qProductTypeRegistration.productType.productTypeNo.eq(productTypeNo)
                    .and(qProduct.isExposed).eq(isExposed));

        List<FindProductResponseDto> productList =
            productListQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productList, pageable,
            () -> from(qProduct).fetchCount());
    }
}
