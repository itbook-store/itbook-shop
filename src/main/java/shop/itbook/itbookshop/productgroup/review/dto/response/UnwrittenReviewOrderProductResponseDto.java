package shop.itbook.itbookshop.productgroup.review.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아직 리뷰가 쓰여지지 않은 주문 상품 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnwrittenReviewOrderProductResponseDto {

    private Long orderProductNo;

    private Long productNo;

    private String name;

    private String thumbnailUrl;

    private LocalDateTime orderCreatedAt;
}
