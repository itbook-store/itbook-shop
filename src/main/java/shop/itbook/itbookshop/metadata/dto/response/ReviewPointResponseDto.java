package shop.itbook.itbookshop.metadata.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 메타데이터 테이블에서 리뷰포인트 데이터를 가져올 dto 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewPointResponseDto {

    Long reviewPoint;
}
