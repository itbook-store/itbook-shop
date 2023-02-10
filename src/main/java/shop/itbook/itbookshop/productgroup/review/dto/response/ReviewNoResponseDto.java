package shop.itbook.itbookshop.productgroup.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 등록된 리뷰 번호를 담은 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewNoResponseDto {

    private Long orderProductNo;
}
