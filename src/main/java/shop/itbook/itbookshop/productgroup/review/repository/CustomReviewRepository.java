package shop.itbook.itbookshop.productgroup.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;

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
     * @param memberNo 해당 멤버 번호로 작성된 리뷰들을 불러옵니다.
     * @return 페이지네이션 처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ReviewResponseDto> findReviewListByMemberNo(Pageable pageable,
                                                     Long memberNo);


    /**
     * 상품 NO로 해당 상품의 리뷰 목록을 불러옵니다.
     *
     * @param pageable  페이지네이션 처리하기 위해 파라미터로 넘깁니다.
     * @param productNo 해당 상품 번호로 작성된 리뷰들을 불러옵니다.
     * @return 페이지네이션 처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ReviewResponseDto> findReviewListByProductNo(Pageable pageable,
                                                      Long productNo);


    /**
     * 멤버 no로 해당 멤버가 아직 작성하지 않은 주문 상품들을 불러옵니다.
     *
     * @param pageable 페이지네이션 처리하기 위해 파라미터로 넘깁니다.
     * @param memberNo 해당 멤버 번호로 아직 작성하지 않은 주문 상품들을 불러옵니다.
     * @return 페이지네이션 처리된 리스트를 반환합니다.
     * @author 노수연
     */
    Page<UnwrittenReviewOrderProductResponseDto> findUnwrittenReviewOrderProductList(
        Pageable pageable, Long memberNo);

}
