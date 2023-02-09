package shop.itbook.itbookshop.productgroup.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * 커스텀 리뷰 repository 인터페이스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@NoRepositoryBean
public interface CustomReviewRepository {


    /**
     * 멤버no로 해당 멤버가 작성한 리뷰들의 목록을 불러옵니다.
     *
     * @param pageable 페이지네이션 처리하기 위해 파라미터로 넘깁니다.
     * @return 페이지네이션 처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ReviewResponseDto> findReviewListByMemberNo(Pageable pageable,
                                                     Long memberNo);

}
