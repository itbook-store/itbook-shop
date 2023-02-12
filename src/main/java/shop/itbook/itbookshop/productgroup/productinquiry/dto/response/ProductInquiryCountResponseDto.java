package shop.itbook.itbookshop.productgroup.productinquiry.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 문의 카운트 개수를 받아오는 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiryCountResponseDto {

    private Long productInquiryCount;
    private Long repliedProductInquiryCount;
}
