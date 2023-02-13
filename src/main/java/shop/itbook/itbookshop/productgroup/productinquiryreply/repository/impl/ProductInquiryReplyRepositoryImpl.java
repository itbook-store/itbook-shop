package shop.itbook.itbookshop.productgroup.productinquiryreply.repository.impl;

import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.itbook.itbookshop.membergroup.member.entity.QMember;
import shop.itbook.itbookshop.productgroup.productinquiry.entity.QProductInquiry;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.QProductInquiryReply;
import shop.itbook.itbookshop.productgroup.productinquiryreply.repository.CustomProductInquiryReplyRepository;

/**
 * 커스텀 상품문의 레포지토리 구현 클래스입니다.
 *
 * @author 노수연
 * @since 1.₩0
 */
public class ProductInquiryReplyRepositoryImpl extends QuerydslRepositorySupport
    implements CustomProductInquiryReplyRepository {

    public ProductInquiryReplyRepositoryImpl() {
        super(ProductInquiryReply.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductInquiryReplyResponseDto> findProductInquiryReply(Long productInquiryNo) {

        QProductInquiryReply qProductInquiryReply = QProductInquiryReply.productInquiryReply;
        QProductInquiry qProductInquiry = QProductInquiry.productInquiry;
        QMember qMember = QMember.member;

        return from(qProductInquiryReply)
            .innerJoin(qProductInquiryReply.productInquiry, qProductInquiry)
            .innerJoin(qProductInquiryReply.member, qMember)
            .select(Projections.constructor(ProductInquiryReplyResponseDto.class,
                qProductInquiryReply.productInquiryReplyNo, qProductInquiry.productInquiryNo,
                qMember.memberNo, qMember.name, qProductInquiryReply.productInquiryReplyTitle,
                qProductInquiryReply.productInquiryReplyContent))
            .where(qProductInquiryReply.productInquiry.productInquiryNo.eq(productInquiryNo))
            .fetch();
    }
}
