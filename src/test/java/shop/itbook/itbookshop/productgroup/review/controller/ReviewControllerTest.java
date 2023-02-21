package shop.itbook.itbookshop.productgroup.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
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
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.review.dto.request.ReviewRequestDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dummy.ReviewDummy;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.repository.ReviewRepository;
import shop.itbook.itbookshop.productgroup.review.service.ReviewService;
import shop.itbook.itbookshop.productgroup.review.transfer.ReviewTransfer;

/**
 * @author 노수연
 * @since 1.0
 */
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ReviewController reviewController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ReviewService reviewService;

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

    Review dummyReview;
    OrderProduct dummyOrderProduct;
    Order dummyOrder;
    Product dummyProduct;
    Member dummyMember;
    MemberStatus dummyMemberStatus;
    Membership dummyMembership;
    MockMultipartFile mockImageFile;

    @BeforeEach
    void setUp() throws Exception {

        String imageContentType = "image/png";
        String path = "src/test/resources/";

        mockImageFile = new MockMultipartFile("image", "test.png", imageContentType,
            new FileInputStream(path + "test.png"));

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

    }

    @Test
    void reviewListByMemberNo() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(dummyReview, pageRequest, 10));

        given(reviewService.findReviewListByMemberNo(any(), any())).willReturn(page);

        mvc.perform(get("/api/reviews/list/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    // 다시 작성
    @Test
    void reviewAdd() throws Exception {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        ReflectionTestUtils.setField(reviewRequestDto, "orderProductNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "productNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "starPoint", 3);
        ReflectionTestUtils.setField(reviewRequestDto, "content", "최고입니다.");
        ReflectionTestUtils.setField(reviewRequestDto, "image", "testUrl");

        Long testNo = 1L;

        Review review = ReviewTransfer.dtoToEntity(reviewRequestDto);
        review.setOrderProductNo(reviewRequestDto.getOrderProductNo());

        given(reviewRepository.save(any(Review.class)))
            .willReturn(review);

        reviewService.addReview(reviewRequestDto, mockImageFile);

        mvc.perform(post("/api/reviews/add")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // 다시 작성하기
    @Test
    void reviewDetails() throws Exception {
        ReviewResponseDto reviewResponseDto = ReviewResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .productName("객체지향과 자바")
            .memberNo(1L)
            .starPoint(3)
            .content("최고입니다.")
            .image("testUrl")
            .build();

        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        ReflectionTestUtils.setField(reviewRequestDto, "orderProductNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "productNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "starPoint", 3);
        ReflectionTestUtils.setField(reviewRequestDto, "content", "최고입니다.");
        ReflectionTestUtils.setField(reviewRequestDto, "image", "testUrl");

        Long testNo = 1L;

        Review review = ReviewTransfer.dtoToEntity(reviewRequestDto);
        review.setOrderProductNo(reviewRequestDto.getOrderProductNo());

        given(reviewService.findReviewById(anyLong()))
            .willReturn(reviewResponseDto);

        mvc.perform(get("/api/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void reviewDetailsForModify() throws Exception {
        ReviewResponseDto reviewResponseDto = ReviewResponseDto
            .builder()
            .orderProductNo(1L)
            .productNo(1L)
            .productName("객체지향과 자바")
            .memberNo(1L)
            .starPoint(3)
            .content("최고입니다.")
            .image("testUrl")
            .build();

        given(reviewService.findReviewByIdAndMemberNo(anyLong(), anyLong())).willReturn(
            reviewResponseDto);

        mvc.perform(get("/api/reviews/modify-form/1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void reviewDelete() throws Exception {
        reviewService.deleteReview(1L);

        mvc.perform(put("/api/reviews/1/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void reviewModify() throws Exception {
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
        ReflectionTestUtils.setField(reviewRequestDto, "orderProductNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "productNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "memberNo", 1L);
        ReflectionTestUtils.setField(reviewRequestDto, "starPoint", 3);
        ReflectionTestUtils.setField(reviewRequestDto, "content", "최고입니다.");
        ReflectionTestUtils.setField(reviewRequestDto, "image", "testUrl");

        reviewService.modifyReview(1L, reviewRequestDto, null);

        mvc.perform(put("/api/reviews/1/modify")
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .accept(MediaType.IMAGE_PNG_VALUE)
                .content(objectMapper.writeValueAsString(reviewRequestDto)))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void reviewListByProductNo() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(dummyReview, pageRequest, 10));

        given(reviewService.findReviewListByProductNo(any(), any())).willReturn(page);

        mvc.perform(get("/api/reviews/list/product/1").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void unwrittenReviewOrderProductList() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page page = new PageImpl(
            List.of(dummyReview, pageRequest, 10));

        given(reviewService.findUnwrittenReviewOrderProductList(any(), any())).willReturn(page);

        mvc.perform(
                get("/api/reviews/unwritten-list/member/1").contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}