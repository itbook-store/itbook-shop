package shop.itbook.itbookshop.productgroup.review.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import shop.itbook.itbookshop.fileservice.FileService;
import shop.itbook.itbookshop.membergroup.member.dto.request.MemberRequestDto;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.member.service.serviceapi.MemberService;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.ordergroup.order.dummy.OrderDummy;
import shop.itbook.itbookshop.ordergroup.order.entity.Order;
import shop.itbook.itbookshop.ordergroup.order.repository.OrderRepository;
import shop.itbook.itbookshop.ordergroup.orderproduct.dummy.OrderProductDummy;
import shop.itbook.itbookshop.ordergroup.orderproduct.entity.OrderProduct;
import shop.itbook.itbookshop.ordergroup.orderproduct.repository.OrderProductRepository;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.service.ReviewIncreasePointHistoryService;
import shop.itbook.itbookshop.productgroup.product.dto.request.ProductAddRequestDto;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.product.service.ProductService;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.review.dummy.ReviewDummy;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewComeCloseOtherMemberException;
import shop.itbook.itbookshop.productgroup.review.exception.ReviewNotFoundException;
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
    ReviewIncreasePointHistoryService reviewIncreasePointHistoryService;

    @MockBean
    FileService fileService;

    @MockBean
    ReviewRepository reviewRepository;

    @MockBean
    MemberStatusRepository memberStatusRepository;

    @MockBean
    MembershipRepository membershipRepository;

    @MockBean
    OrderRepository orderRepository;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    OrderProductRepository orderProductRepository;

    ReviewRequestDto reviewRequestDto;

    ReviewResponseDto reviewResponseDto;

    MockMultipartFile mockImageFile;

    ProductAddRequestDto productAddRequestDto;

    MemberRequestDto memberRequestDto;

    UnwrittenReviewOrderProductResponseDto unwrittenReviewOrderProductResponseDto;

    Review dummyReview;
    OrderProduct dummyOrderProduct;
    Order dummyOrder;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;


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

        dummyMemberStatus = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(dummyMemberStatus);

        dummyMembership = MembershipDummy.getMembership();
        membershipRepository.save(dummyMembership);

        dummyMember = MemberDummy.getMember1();
        dummyMember.setMemberStatus(dummyMemberStatus);
        dummyMember.setMembership(dummyMembership);
        memberRepository.save(dummyMember);

        dummyOrder = OrderDummy.getOrder();
        orderRepository.save(dummyOrder);

        dummyProduct = ProductDummy.getProductSuccess();
        productRepository.save(dummyProduct);

        dummyOrderProduct = OrderProductDummy.createOrderProduct(dummyOrder, dummyProduct);
        orderProductRepository.save(dummyOrderProduct);

        dummyReview = ReviewDummy.getReview();
        dummyReview.setOrderProductNo(dummyProduct.getProductNo());
        dummyReview.setOrderProduct(dummyOrderProduct);
        dummyReview.setProduct(dummyProduct);
        dummyReview.setMember(dummyMember);
        reviewRepository.save(dummyReview);

        unwrittenReviewOrderProductResponseDto =
            UnwrittenReviewOrderProductResponseDto.builder().orderProductNo(4L).productNo(1L)
                .name("재미있습니다.").thumbnailUrl("testurl").orderCreatedAt(
                    LocalDateTime.now()).build();
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

        Product product = dummyProduct;
        Member member = dummyMember;

        Review review = ReviewTransfer.dtoToEntity(reviewRequestDto);
        review.setOrderProductNo(reviewRequestDto.getOrderProductNo());
        review.setProduct(product);
        review.setMember(member);

        given(reviewRepository.save(any(Review.class)))
            .willReturn(review);

        Long actual = reviewService.addReview(reviewRequestDto, mockImageFile);

        Assertions.assertThat(actual).isEqualTo(review.getOrderProductNo());
    }

    @Test
    void findReviewById() {

        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(dummyReview));

        ReviewResponseDto actualReview = reviewService.findReviewById(1L);

        Assertions.assertThat(actualReview.getOrderProductNo())
            .isEqualTo(dummyReview.getOrderProductNo());
    }

    @Test
    void findReviewByIdFailure() {
        given(reviewRepository.findById(anyLong())).willReturn(Optional.empty());
        Assertions.assertThatThrownBy(() -> reviewService.findReviewById(1L))
            .isInstanceOf(ReviewNotFoundException.class)
            .hasMessage(ReviewNotFoundException.MESSAGE);
    }

    @Test
    void findReviewByIdAndMemberNo() {

        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(dummyReview));

        Assertions.assertThatThrownBy(() -> reviewService.findReviewByIdAndMemberNo(1L, 1L))
            .isInstanceOf(ReviewComeCloseOtherMemberException.class)
            .hasMessage(ReviewComeCloseOtherMemberException.MESSAGE);
    }

    @Test
    void deleteReview() {
        given(reviewRepository.findById(any())).willReturn(Optional.of(dummyReview));

        Review review = reviewRepository.findById(1L).orElseThrow();
        reviewService.deleteReview(review.getOrderProductNo());

    }

    @Test
    void modifyReview() {

        given(reviewRepository.findById(any())).willReturn(Optional.of(dummyReview));

        Review review = reviewRepository.findById(1L).orElseThrow();
        reviewService.modifyReview(review.getOrderProductNo(), reviewRequestDto, null);
    }

    @Test
    void findReviewListByProductNo() {
        given(reviewService.findReviewListByProductNo(any(), any())).willReturn(
            new PageImpl<>(List.of(reviewResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<ReviewResponseDto> page = reviewService.findReviewListByProductNo(pageRequest, 1L);

        List<ReviewResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isEqualTo(1);
        assertThat(reviewList.get(0).getMemberNo()).isEqualTo(1L);
    }

    @Test
    void findUnwrittenReviewOrderProductList() {
        given(reviewService.findUnwrittenReviewOrderProductList(any(), any())).willReturn(
            new PageImpl<>(List.of(unwrittenReviewOrderProductResponseDto)));

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<UnwrittenReviewOrderProductResponseDto> page =
            reviewService.findUnwrittenReviewOrderProductList(pageRequest, 1L);

        List<UnwrittenReviewOrderProductResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isEqualTo(1);
    }
}