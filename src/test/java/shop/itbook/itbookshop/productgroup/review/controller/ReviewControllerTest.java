package shop.itbook.itbookshop.productgroup.review.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.itbook.itbookshop.productgroup.review.dummy.ReviewDummy;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.repository.ReviewRepository;
import shop.itbook.itbookshop.productgroup.review.service.ReviewService;

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

    @BeforeEach
    void setUp() {
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
}