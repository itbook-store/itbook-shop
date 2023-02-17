package shop.itbook.itbookshop.productgroup.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 리뷰 데이터를 받아올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long orderProductNo;

    private Long productNo;

    private String productName;

    private String thumbnailUrl;

    private Long memberNo;

    private String memberId;

    private Integer starPoint;

    private String content;

    private String image;
}
