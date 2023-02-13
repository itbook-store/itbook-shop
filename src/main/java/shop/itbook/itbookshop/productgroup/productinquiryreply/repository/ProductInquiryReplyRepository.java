package shop.itbook.itbookshop.productgroup.productinquiryreply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.itbook.itbookshop.productgroup.productinquiryreply.entity.ProductInquiryReply;

/**
 * 상품 문의 답글 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ProductInquiryReplyRepository
    extends JpaRepository<ProductInquiryReply, Integer>, CustomProductInquiryReplyRepository {
}
