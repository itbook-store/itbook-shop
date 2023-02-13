package shop.itbook.itbookshop.productgroup.productinquiryreply.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 상품 문의 답글 정보 입력받는 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInquiryReplyRequestDto {

    @NotNull(message = "상품문의 번호는 null을 허용하지 않습니다.")
    private Long productInquiryNo;

    @NotNull(message = "멤버 번호는 null을 허용하지 않습니다.")
    private Long memberNo;

    @Length(min = 2, max = 20, message = "상품문의 답글 제목은 최소 2자, 최대 20자까지 허용합니다.")
    @NotBlank(message = "상품문의 답글 제목은 null이거나 공백이어서는 안됩니다.")
    private String productInquiryReplyTitle;

    @NotBlank(message = "상품문의 답글 내용은 null이거나 공백이어서는 안됩니다.")
    private String productInquiryReplyContent;

}
