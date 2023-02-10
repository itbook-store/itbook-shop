package shop.itbook.itbookshop.bookmark.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 즐겨찾기 요청에 대한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkRequestDto {

    @NotNull(message = "회원번호는 필수 입니다.")
    private Long memberNo;

    @NotNull(message = "상품번호는 필수 입니다.")
    private Long productNo;

}
