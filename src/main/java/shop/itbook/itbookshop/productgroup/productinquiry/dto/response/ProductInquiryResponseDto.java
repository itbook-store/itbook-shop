package shop.itbook.itbookshop.productgroup.productinquiry.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 문의 정보를 받아오는 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiryResponseDto {

    private Long productInquiryNo;
    private Long memberNo;
    private String memberId;
    private Long productNo;
    private String name;
    private String thumbnailUrl;
    private String authorName;
    private String title;
    private String content;
    private Boolean isPublic;
    private Boolean isReplied;
}
