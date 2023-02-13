package shop.itbook.itbookshop.productgroup.productinquiryreply.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response.ProductInquiryReplyResponseDto;

/**
 * 커스텀 상품문의 답글 레포지토리입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomProductInquiryReplyRepository {

    List<ProductInquiryReplyResponseDto> findProductInquiryReply(Long productInquiryNo);
}
