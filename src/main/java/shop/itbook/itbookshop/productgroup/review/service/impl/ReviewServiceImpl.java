package shop.itbook.itbookshop.productgroup.review.service.impl;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.ReviewIncreasePointHistoryService;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.exception.InvalidInputException;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewAlreadyRegisteredException;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewNotFoundException;
import shop.itbook.itbookshop.productgroup.review.repository.ReviewRepository;
import shop.itbook.itbookshop.productgroup.review.service.ReviewService;
import shop.itbook.itbookshop.productgroup.review.transfer.ReviewTransfer;

/**
 * 리뷰 서비스 인터페이스를 구현한 클래스입니다.
 *
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final MemberService memberService;

    private final ProductService productService;

    private final ReviewIncreasePointHistoryService reviewIncreasePointHistoryService;

    private final FileService fileService;

    @Value("${object.storage.folder-path.review}")
    private String folderPathImage;

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewResponseDto> findReviewListByMemberNo(Pageable pageable, Long memberNo) {

        return reviewRepository.findReviewListByMemberNo(pageable, memberNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Long addReview(ReviewRequestDto reviewRequestDto, MultipartFile images) {

        if (reviewRepository.existsByOrderProductNo(reviewRequestDto.getOrderProductNo())) {
            throw new ReviewAlreadyRegisteredException();
        }

        Review review = ReviewTransfer.dtoToEntity(reviewRequestDto);

        Product product = productService.findProductEntity(reviewRequestDto.getProductNo());

        Member member = memberService.findMemberByMemberNo(reviewRequestDto.getMemberNo());

        review.setOrderProductNo(reviewRequestDto.getOrderProductNo());
        review.setProduct(product);
        review.setMember(member);

        Long reviewNo;

        try {
            review.setImage(uploadAndSetFile(images));
            reviewNo = reviewRepository.save(review).getOrderProductNo();

            // TODO jun : 메타 데이터의 포인트 값 가져오기
            reviewIncreasePointHistoryService.savePointHistoryAboutReviewIncrease(member, review,
                100L);


        } catch (DataIntegrityViolationException e) {
            throw new InvalidInputException();
        }

        return reviewNo;
    }

    private String uploadAndSetFile(MultipartFile images) {

        String imageUrl;

        if (Objects.isNull(images)) {
            imageUrl =
                "https://api-storage.cloud.toast.com/v1/AUTH_fcb81f74e379456b8ca0e091d351a7af/itbook-test/review/0a8ca476-4a6b-4963-ac60-8c0e6bd6d8ed.png";
        } else {
            imageUrl = fileService.uploadFile(images, folderPathImage);
        }

        return imageUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReviewResponseDto findReviewById(Long orderProductNo) {

        return ReviewTransfer.entityToDto(
            reviewRepository.findById(orderProductNo).orElseThrow(ReviewNotFoundException::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteReview(Long orderProductNo) {
        Review review =
            reviewRepository.findById(orderProductNo).orElseThrow(ReviewNotFoundException::new);

        review.setStarPoint(-1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void modifyReview(Long orderProductNo, ReviewRequestDto reviewRequestDto,
                             MultipartFile images) {

        if (Objects.nonNull(images)) {
            reviewRequestDto.setImage(uploadAndSetFile(images));
        }

        Review review =
            reviewRepository.findById(orderProductNo).orElseThrow(ReviewNotFoundException::new);

        review.modifyReview(reviewRequestDto.getStarPoint(), reviewRequestDto.getContent(),
            reviewRequestDto.getImage());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReviewResponseDto> findReviewListByProductNo(Pageable pageable, Long productNo) {

        return reviewRepository.findReviewListByProductNo(pageable, productNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UnwrittenReviewOrderProductResponseDto> findUnwrittenReviewOrderProductList(
        Pageable pageable, Long memberNo) {
        return reviewRepository.findUnwrittenReviewOrderProductList(pageable, memberNo);
    }
}
