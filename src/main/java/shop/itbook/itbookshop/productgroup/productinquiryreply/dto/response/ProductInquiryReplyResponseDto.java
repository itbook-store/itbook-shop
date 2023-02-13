package shop.itbook.itbookshop.productgroup.productinquiryreply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품문의 답글 받아오는 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiryReplyResponseDto {

    private Integer productInquiryReplyNo;

    private Long productInquiryNo;

    private Long memberNo;

    private String productInquiryReplyTitle;

    private String productInquiryReplyContent;

}
