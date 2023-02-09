package shop.itbook.itbookshop.productgroup.review.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 리뷰 등록하기 위한 request dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequestDto {

    @NotNull(message = "주문 상품 번호는 null을 허용하지 않습니다.")
    private Long orderProductNo;

    @NotNull(message = "상품 번호는 null을 허용하지 않습니다.")
    private Long productNo;

    @NotNull(message = "멤버 번호는 null을 허용하지 않습니다.")
    private Long memberNo;

    @Positive(message = "별점은 양수여야 합니다.")
    private Integer starPoint;

    @NotBlank(message = "리뷰 내용은 null이거나 공백이어서는 안됩니다.")
    private String content;

    // TODO no_image 넣어야하므로 notblank 밸리데이션 주기
    private String image;

}
