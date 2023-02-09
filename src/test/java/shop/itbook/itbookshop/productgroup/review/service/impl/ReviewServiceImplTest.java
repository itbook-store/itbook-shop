package shop.itbook.itbookshop.productgroup.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.member.transfer.MemberTransfer;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductBookRequestDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.product.transfer.ProductTransfer;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.repository.ReviewRepository;
import shop.itbook.itbookshop.productgroup.review.service.ReviewService;
import shop.itbook.itbookshop.productgroup.review.transfer.ReviewTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@Import(ReviewServiceImpl.class)
class ReviewServiceImplTest {

    @Autowired
    ReviewService reviewService;

    @MockBean
    MemberService memberService;

    @MockBean
    ProductService productService;

    @MockBean
    FileService fileService;

    @MockBean
    ReviewRepository reviewRepository;

    ReviewRequestDto reviewRequestDto;

    ReviewResponseDto reviewResponseDto;

    MockMultipartFile mockImageFile;

    ProductRequestDto productRequestDto;

    MemberRequestDto memberRequestDto;

    @BeforeEach
    void setUp() throws IOException {
        String imageContentType = "image/png";
        String path = "src/test/resources/";

        mockImageFile = new MockMultipartFile("image", "test.png", imageContentType,
            new FileInputStream(path + "test.png"));

        reviewRequestDto = ReviewRequestDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .memberNo(1L)
            .starPoint(5)
            .content("재미있습니다")
            .image("testurl")
            .build();

        reviewResponseDto = ReviewResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .productName("테스트 책")
            .memberNo(1L)
            .starPoint(5)
            .content("재미있습니다")
            .image("testurl")
            .build();

        productRequestDto = ProductBookRequestDummy.getProductRequest();

        memberRequestDto = new MemberRequestDto();
        ReflectionTestUtils.setField(memberRequestDto, "membershipName", "일반");
        ReflectionTestUtils.setField(memberRequestDto, "memberStatusName", "정상회원");
        ReflectionTestUtils.setField(memberRequestDto, "memberId", "user1000");
        ReflectionTestUtils.setField(memberRequestDto, "nickname", "감자");
        ReflectionTestUtils.setField(memberRequestDto, "name", "신짱구");
        ReflectionTestUtils.setField(memberRequestDto, "isMan", true);
        ReflectionTestUtils.setField(memberRequestDto, "birth",
            LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        ReflectionTestUtils.setField(memberRequestDto, "password", "1234");
        ReflectionTestUtils.setField(memberRequestDto, "phoneNumber", "010-9999-9999");
        ReflectionTestUtils.setField(memberRequestDto, "email", "user1000@test.com");
    }

    @Test
    void findReviewListByMemberNo() {

        given(reviewService.findReviewListByMemberNo(any(), any())).willReturn(
            new PageImpl<>(List.of(reviewResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ReviewResponseDto> page = reviewService.findReviewListByMemberNo(pageRequest, 1L);

        List<ReviewResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isEqualTo(1);
        assertThat(reviewList.get(0).getMemberNo()).isEqualTo(1L);
    }

    @Test
    void addReview() {

        Product product = ProductTransfer.dtoToEntityAdd(productRequestDto);
        Member member = MemberTransfer.dtoToEntity(memberRequestDto);

        Review review = ReviewTransfer.dtoToEntity(reviewRequestDto);
        review.setOrderProductNo(reviewRequestDto.getOrderProductNo());
        review.setProduct(product);
        review.setMember(member);

        given(reviewRepository.save(any(Review.class)))
            .willReturn(review);

        Long actual = reviewService.addReview(reviewRequestDto, mockImageFile);

        Assertions.assertThat(actual).isEqualTo(review.getOrderProductNo());
    }
}