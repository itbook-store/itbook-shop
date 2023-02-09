package shop.itbook.itbookshop.bookmark.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import shop.itbook.itbookshop.productgroup.product.dto.response.ProductDetailsResponseDto;

/**
 * 즐겨찾기 응답에 대한 DTO 클래스 입니다.
 *
 * @author 강명관
 * @since 1.0
 */

@Getter
@AllArgsConstructor
public class BookmarkResponseDto {

    private LocalDateTime bookmarkCreateAt;

    private ProductDetailsResponseDto productDetailsResponseDto;
}
