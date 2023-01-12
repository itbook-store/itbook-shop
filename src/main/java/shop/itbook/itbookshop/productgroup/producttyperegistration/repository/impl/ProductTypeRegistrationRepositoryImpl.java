package shop.itbook.itbookshop.productgroup.producttyperegistration.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.producttype.entity.QProductType;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.dto.response.FindProductTypeResponseDto;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.ProductTypeRegistration;
import shop.itbook.itbookshop.productgroup.producttyperegistration.entity.QProductTypeRegistration;

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
    public List<FindProductTypeResponseDto> findProductTypeListWithProductNo(Long productNo) {

        QProductTypeRegistration productTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProductType productType = QProductType.productType;

        JPQLQuery<FindProductTypeResponseDto> productTypeList =
            from(productTypeRegistration)
                .innerJoin(productTypeRegistration.productType, productType).fetchJoin()
                .select(Projections.constructor(FindProductTypeResponseDto.class,
                    productTypeRegistration.productType.productTypeEnum.as("product_type_name")))
                .where(productTypeRegistration.product.productNo.eq(productNo));

        return productTypeList.fetch();
    }

    @Override
    public List<FindProductResponseDto> findProductListWithProductTypeNo(Integer productTypeNo) {
        QProductTypeRegistration productTypeRegistration =
            QProductTypeRegistration.productTypeRegistration;
        QProduct product = QProduct.product;

        JPQLQuery<FindProductResponseDto> productList = from(productTypeRegistration)
            .select(Projections.constructor(FindProductResponseDto.class,
                productTypeRegistration.product))
            .where(productTypeRegistration.productType.productTypeNo.eq(productTypeNo));

        return productList.fetch();
    }
}
