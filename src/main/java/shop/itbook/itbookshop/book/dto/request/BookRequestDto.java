package shop.itbook.itbookshop.book.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 도서를 등록하기 위한 데이터를 전달하는 requestDto 클래스입니다.
 *
 * @author 이하늬
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    private String isbn;

    @NotNull(message = "null을 허용하지 않습니다.")
    @PositiveOrZero(message = "페이지 수는 0원 이상이어야 합니다.")
    private Integer pageCount;

    @NotNull(message = "null을 허용하지 않습니다.")
    private String bookCreatedAt;

    @NotNull(message = "null을 허용하지 않습니다.")
    private Boolean isEbook;

    private String ebookUrl;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "이름 길이는 1자-20자가 되어야 합니다.")
    private String publisherName;

    @NotBlank(message = "공백이 아닌 문자를 하나 이상 포함해야 됩니다.")
    @Length(max = 255, message = "이름 길이는 1자-255자가 되어야 합니다.")
    private String authorName;

}
