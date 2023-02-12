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
import shop.itbook.itbookshop.ordergroup.order.entity.QOrder;
import shop.itbook.itbookshop.ordergroup.ordermember.entity.QOrderMember;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.QOrderProduct;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.QOrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatushistory.entity.QOrderStatusHistory;
import shop.itbook.itbookshop.productgroup.product.entity.QProduct;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryCountResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiry.dto.response.ProductInquiryOrderProductResponseDto;
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
                    qProduct.name, qProduct.thumbnailUrl, qProductInquiry.title,
                    qProductInquiry.content,
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductInquiryCountResponseDto productInquiryCount() {
        QProductInquiry qProductInquiry = QProductInquiry.productInquiry;

        return from(qProductInquiry)
            .select(Projections.constructor(ProductInquiryCountResponseDto.class,
                qProductInquiry.count(),
                new CaseBuilder()
                    .when(qProductInquiry.isReplied.eq(true))
                    .then(true).otherwise(false).count()))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ProductInquiryOrderProductResponseDto> ProductInquiryListOfPossibleOrderProducts(
        Pageable pageable, Long memberNo) {

        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;
        QProduct qProduct = QProduct.product;
        QOrder qOrder = QOrder.order;
        QOrderMember qOrderMember = QOrderMember.orderMember;
        QOrderStatusHistory qOrderStatusHistory = QOrderStatusHistory.orderStatusHistory;
        QOrderStatus qOrderStatus = QOrderStatus.orderStatus;

        JPQLQuery<ProductInquiryOrderProductResponseDto> productInquiryOrderProductListQuery =
            from(qOrderProduct)
                .innerJoin(qProduct)
                .on(qOrderProduct.product.productNo.eq(qProduct.productNo))
                .leftJoin(qOrder)
                .on(qOrderProduct.order.orderNo.eq(qOrder.orderNo))
                .leftJoin(qOrderMember)
                .on(qOrder.orderNo.eq(qOrderMember.orderNo))
                .leftJoin(qOrderStatusHistory)
                .on(qOrder.orderNo.eq(qOrderStatusHistory.order.orderNo))
                .innerJoin(qOrderStatus)
                .on(qOrderStatusHistory.orderStatus.orderStatusNo.eq(qOrderStatus.orderStatusNo))
                .select(Projections.constructor(ProductInquiryOrderProductResponseDto.class,
                    qOrderProduct.orderProductNo, qProduct.productNo, qProduct.name,
                    qProduct.thumbnailUrl, qOrderProduct.order.orderCreatedAt))
                .where(qOrderMember.member.memberNo.eq(memberNo)
                    .and(qOrderStatus.orderStatusEnum.stringValue().eq("결제완료")))
                .orderBy(qOrderProduct.orderProductNo.desc());

        List<ProductInquiryOrderProductResponseDto> productInquiryOrderProductList =
            productInquiryOrderProductListQuery.offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();

        return PageableExecutionUtils.getPage(productInquiryOrderProductList, pageable,
            () -> from(qOrderProduct).fetchCount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductInquiryResponseDto findProductInquiry(Long productInquiryNo) {
        QProductInquiry qProductInquiry = QProductInquiry.productInquiry;
        QMember qMember = QMember.member;
        QProduct qProduct = QProduct.product;

        return from(qProductInquiry)
            .innerJoin(qProductInquiry.product, qProduct)
            .innerJoin(qProductInquiry.member, qMember)
            .select(Projections.constructor(ProductInquiryResponseDto.class,
                qProductInquiry.productInquiryNo, qMember.memberNo, qMember.memberId,
                qProduct.productNo, qProduct.name, qProduct.thumbnailUrl, qProductInquiry.title,
                qProductInquiry.content, qProductInquiry.isPublic, qProductInquiry.isReplied))
            .where(qProductInquiry.productInquiryNo.eq(productInquiryNo)).fetchOne();

    }
}
