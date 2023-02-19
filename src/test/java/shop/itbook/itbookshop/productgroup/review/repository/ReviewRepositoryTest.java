package shop.itbook.itbookshop.productgroup.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dto.response.UnwrittenReviewOrderProductResponseDto;
import shop.itbook.itbookshop.productgroup.review.dummy.ReviewDummy;
import shop.itbook.itbookshop.productgroup.review.entity.Review;

/**
 * @author 노수연
 * @since 1.0
 */
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    TestEntityManager entityManager;

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
        OrderProduct savedOrderProduct = orderProductRepository.save(dummyOrderProduct);

        dummyReview = ReviewDummy.getReview();
        dummyReview.setOrderProductNo(savedOrderProduct.getOrderProductNo());
        dummyReview.setProduct(dummyProduct);
        dummyReview.setMember(dummyMember);
        reviewRepository.save(dummyReview);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findById() {

        Optional<Review> review = reviewRepository.findById(dummyReview.getOrderProductNo());

        assertThat(review).isPresent();
        assertThat(review.get().getOrderProductNo())
            .isEqualTo(dummyReview.getOrderProductNo());
    }

    @Test
    void findReviewListByMemberNo() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ReviewResponseDto> page = reviewRepository.findReviewListByMemberNo(pageRequest,
            dummyReview.getMember().getMemberNo());

        List<ReviewResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isEqualTo(1);
    }

    @Test
    void findReviewListByProductNo() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ReviewResponseDto> page = reviewRepository.findReviewListByProductNo(pageRequest,
            dummyReview.getMember().getMemberNo());

        List<ReviewResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isNotIn(-1);
    }

    @Test
    void findUnwrittenReviewOrderProductList() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UnwrittenReviewOrderProductResponseDto> page =
            reviewRepository.findUnwrittenReviewOrderProductList(pageRequest,
                dummyReview.getMember().getMemberNo());

        List<UnwrittenReviewOrderProductResponseDto> reviewList = page.getContent();

        assertThat(reviewList.size()).isEqualTo(0);
    }

    @Test
    void existsByOrderProductNo() {

        Boolean isExists = reviewRepository.existsByOrderProductNo(1L);

        assertThat(isExists).isFalse();
    }
}