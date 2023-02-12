package shop.itbook.itbookshop.productgroup.productinquiry.repository.impl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.ProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.QProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiry.repository.CustomProductInquiryRepository;

/**
 * 커스텀 상품문의 레포지토리 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public class ProductInquiryRepositoryImpl extends QuerydslRepositorySupport implements
    CustomProductInquiryRepository {

    public ProductInquiryRepositoryImpl() {
        super(ProductInquiry.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryResponseDto> findProductInquiryList(Pageable pageable) {

        QProductInquiry qProductInquiry = QProductInquiry.productInquiry;
        QProduct qProduct = QProduct.product;
        QMember qMember = QMember.member;

        JPQLQuery<ProductInquiryResponseDto> productInquiryListQuery =
            from(qProductInquiry)
                .innerJoin(qProductInquiry.product, qProduct)
                .innerJoin(qProductInquiry.member, qMember)
                .select(Projections.constructor(ProductInquiryResponseDto.class,
                    qProductInquiry.productInquiryNo, qMember.memberNo, qMember.memberId,
                    qProduct.productNo,
                    qProduct.name, qProductInquiry.title, qProductInquiry.content,
                    qProductInquiry.isPublic,
                    qProductInquiry.isReplied))
                .orderBy(productAnswerOrNot())
                .orderBy(qProductInquiry.productInquiryNo.desc());

        List<ProductInquiryResponseDto> productInquiryList = productInquiryListQuery
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productInquiryList, pageable,
            () -> from(qProductInquiry).fetchCount());

    }

    private OrderSpecifier<Integer> productAnswerOrNot() {
        QProductInquiry qProductInquiry = QProductInquiry.productInquiry;

        NumberExpression<Integer> cases = new CaseBuilder()
            .when(qProductInquiry.isReplied.eq(false))
            .then(1)
            .otherwise(2);
        return new OrderSpecifier<>(Order.ASC, cases);
    }
}
