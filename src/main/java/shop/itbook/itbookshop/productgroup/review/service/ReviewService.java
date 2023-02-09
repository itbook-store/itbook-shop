package shop.itbook.itbookshop.productgroup.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;

/**
 * 리뷰 서비스 인터페이스 입니다.
 *
 * @author 노수연
 * @since 1.0
 */
public interface ReviewService {


    /**
     * 현재 로그인 되어 있는 멤버의 멤버 번호로
     * 리뷰 리스트들을 불러옵니다.
     *
     * @param pageable 페이징 처리하여 불러옵니다.
     * @param memberNo 멤버 번호로 테이블에서 리뷰 데이터를 찾습니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ReviewResponseDto> findReviewListByMemberNo(Pageable pageable,
                                                     Long memberNo);


    /**
     * 프론트 서버에서 dto를 통해 리뷰 정보들이 넘어오면 이를 테이블에 저장합니다.
     *
     * @param reviewRequestDto 프론트에서 넘어온 정보들을 저장한 DTO 입니다.
     * @return the long 리뷰를 테이블에 저장한 뒤 저장된 orderProductNo를 반환합니다.
     * @author 노수연
     */
    Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images);
}
