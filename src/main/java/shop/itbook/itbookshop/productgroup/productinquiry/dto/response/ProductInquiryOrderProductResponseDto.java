package shop.itbook.itbookshop.productgroup.productinquiry.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품문의를 작성할 수 있도록 주문한 상품 목록들을 가져옵니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInquiryOrderProductResponseDto {

    private Long orderProductNo;

    private Long productNo;

    private String name;

    private String thumbnailUrl;

    private LocalDateTime orderCreatedAt;
}
