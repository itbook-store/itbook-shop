package shop.itbook.itbookshop.productgroup.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;

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
     * @param images           함께 저장한 이미지 파일이 넘어갑니다.
     * @return the long 리뷰를 테이블에 저장한 뒤 저장된 orderProductNo를 반환합니다.
     * @author 노수연
     */
    Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images);

    /**
     * pk인 orderProductNo로 테이블에서 리뷰 데이터를 찾습니다.
     *
     * @param orderProductNo 데이터를 찾을 파라미터입니다.
     * @return 리뷰 엔티티를 반환합니다.
     * @author 노수연
     */
    ReviewResponseDto findReviewById(Long orderProductNo);

    ReviewResponseDto findReviewByIdAndMemberNo(Long memberNo, Long orderProductNo);

    /**
     * orderProductNo로 테이블에서 리뷰 데이터를 찾아 삭제합니다.
     *
     * @param orderProductNo 데이터를 찾을 파라미터입니다.
     * @author 노수연
     */
    void deleteReview(Long orderProductNo);


    /**
     * 리뷰를 등록한 회원이 수정할 때
     * orderproductNo로 해당 리뷰의 데이터를 찾아 수정합니다.
     *
     * @param orderProductNo   데이터를 찾을 파라미터 입니다.
     * @param reviewRequestDto 수정할 데이터가 담긴 DTO 입니다.
     * @param multipartFile    수정할 이미지가 넘어옵니다.
     * @author 노수연
     */
    void modifyReview(Long orderProductNo,
                      ReviewRequestDto reviewRequestDto,
                      MultipartFile multipartFile);

    /**
     * 상품 번호로
     * 리뷰 리스트들을 불러옵니다.
     *
     * @param pageable  페이징 처리하여 불러옵니다.
     * @param productNo 상품 번호로 테이블에서 리뷰 데이터를 찾습니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<ReviewResponseDto> findReviewListByProductNo(Pageable pageable,
                                                      Long productNo);


    /**
     * 멤버 번호로 해당 멤버가 주문한 상품들 중에서 아직 리뷰가 쓰여지지 않은
     * 주문 상품 리스트를 불러옵니다.
     *
     * @param pageable 페이징 처리하여 불러옵니다.
     * @param memberNo 멤버 번호로 테이블에서 주문 상품 리스트를 찾습니다.
     * @return 페이징 처리된 dto 리스트를 반환합니다.
     * @author 노수연
     */
    Page<UnwrittenReviewOrderProductResponseDto> findUnwrittenReviewOrderProductList(
        Pageable pageable,
        Long memberNo);
}
