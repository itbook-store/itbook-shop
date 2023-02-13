package shop.itbook.itbookshop.productgroup.productinquiry.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * 상품 문의 request Dto 입니다.
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
public class ProductInquiryRequestDto {

    @NotNull(message = "멤버 번호는 null을 허용하지 않습니다.")
    private Long memberNo;

    @NotNull(message = "상품 번호는 null을 허용하지 않습니다.")
    private Long productNo;

    @Length(min = 2, max = 20, message = "상품문의 제목은 최소 2자, 최대 20자까지 허용합니다.")
    @NotBlank(message = "상품문의 제목은 null이거나 공백이어서는 안됩니다.")
    private String title;

    @NotBlank(message = "상품문의 내용은 null이거나 공백이어서는 안됩니다.")
    private String content;

    @NotNull(message = "공개여부는 null값을 허용하지 않습니다.")
    private Boolean isPublic;

}
