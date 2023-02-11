package shop.itbook.itbookshop.pointgroup.pointhistorychild.review.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
import shop.itbook.itbookshop.ordergroup.orderproducthistory.dummy.OrderProductHistoryDummy;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.entity.OrderProductHistory;
import shop.itbook.itbookshop.ordergroup.orderproducthistory.repository.OrderProductHistoryRepository;
import shop.itbook.itbookshop.ordergroup.orderstatus.dummy.OrderStatusDummy;
import shop.itbook.itbookshop.ordergroup.orderstatus.entity.OrderStatus;
import shop.itbook.itbookshop.ordergroup.orderstatus.repository.OrderStatusRepository;
import shop.itbook.itbookshop.ordergroup.orderstatusenum.OrderStatusEnum;
import shop.itbook.itbookshop.pointgroup.pointhistory.entity.PointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.PointHistoryRepository;
import shop.itbook.itbookshop.pointgroup.pointhistory.repository.dummy.PointHistoryDummy;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.gift.entity.GiftIncreaseDecreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointhistorychild.review.entity.ReviewIncreasePointHistory;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.entity.PointIncreaseDecreaseContent;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.PointIncreaseDecreaseContentRepository;
import shop.itbook.itbookshop.pointgroup.pointincreasedecreasecontent.repository.dummy.PointIncreaseDecreaseContentDummy;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;
import shop.itbook.itbookshop.productgroup.review.dto.response.ReviewResponseDto;
import shop.itbook.itbookshop.productgroup.review.dummy.ReviewDummy;
import shop.itbook.itbookshop.productgroup.review.entity.Review;
import shop.itbook.itbookshop.productgroup.review.repository.ReviewRepository;

/**
 * @author 최겸준
 * @since 1.0
 */
@DataJpaTest
@Disabled("리뷰 맡은 분 테스트가 종료되면 리뷰가져와서 null 위치에 넣을 예정")
class ReviewIncreasePointHistoryRepositoryTest {

    @Autowired
    ReviewIncreasePointHistoryRepository reviewIncreasePointHistoryRepository;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    PointHistoryRepository pointHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    MemberStatusRepository memberStatusRepository;

    @Autowired
    PointIncreaseDecreaseContentRepository pointIncreaseDecreaseContentRepository;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderStatusRepository orderStatusRepository;
    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderProductHistoryRepository orderProductHistoryRepository;

    @Autowired
    ReviewRepository reviewRepository;

    Member member1;
    Membership membership1;
    MemberStatus normalMemberStatus1;
    PointIncreaseDecreaseContent orderIncreasePointIncreaseDecreaseContent;
    PointHistory dummyPointHistory1;
    PointHistory dummyPointHistory2;
    ReviewIncreasePointHistory reviewIncreasePointHistory1;
    ReviewIncreasePointHistory reviewIncreasePointHistory2;

    Review review;

    OrderProduct orderProduct;
    OrderStatus orderStatus;


    @BeforeEach
    void setUp() {

        orderIncreasePointIncreaseDecreaseContent =
            PointIncreaseDecreaseContentDummy.getOrderIncreasePointIncreaseDecreaseContent();

        pointIncreaseDecreaseContentRepository.save(orderIncreasePointIncreaseDecreaseContent);
        member1 = MemberDummy.getMember1();

        membership1 = MembershipDummy.getMembership();
        membershipRepository.save(membership1);

        normalMemberStatus1 = MemberStatusDummy.getNormalMemberStatus();
        memberStatusRepository.save(normalMemberStatus1);

        member1.setMemberStatus(normalMemberStatus1);
        member1.setMembership(membership1);
        memberRepository.save(member1);


        dummyPointHistory1 = PointHistoryDummy.getPointHistory();
        dummyPointHistory1.setMember(member1);
        dummyPointHistory1.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory1);

        dummyPointHistory2 = PointHistoryDummy.getPointHistory();
        dummyPointHistory2.setMember(member1);
        dummyPointHistory2.setPointIncreaseDecreaseContent(
            orderIncreasePointIncreaseDecreaseContent);

        pointHistoryRepository.save(dummyPointHistory2);


        Order order = orderRepository.save(OrderDummy.getOrder());
        Product product = productRepository.save(ProductDummy.getProductSuccess());
        orderProduct = OrderProductDummy.createOrderProduct(order, product);
        orderProductRepository.save(orderProduct);

        orderStatus = OrderStatusDummy.createByEnum(OrderStatusEnum.PAYMENT_COMPLETE);
        orderStatusRepository.save(orderStatus);


        review = ReviewDummy.getReview();
        review.setOrderProduct(orderProduct);
        review.setProduct(product);
        review.setMember(member1);
        review.setOrderProductNo(orderProduct.getOrderProductNo());

        reviewRepository.save(review);

        reviewIncreasePointHistory2 = new ReviewIncreasePointHistory(
            dummyPointHistory2.getPointHistoryNo(), review);

        reviewIncreasePointHistoryRepository.save(reviewIncreasePointHistory2);


        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("리뷰적립 포인트내역이 잘 저장된다.")
    @Test
    void save() {
        reviewIncreasePointHistory1 = new ReviewIncreasePointHistory(
            dummyPointHistory1.getPointHistoryNo(), review);

        reviewIncreasePointHistoryRepository.save(reviewIncreasePointHistory1);

        entityManager.flush();
        entityManager.clear();

        ReviewIncreasePointHistory actual =
            entityManager.find(ReviewIncreasePointHistory.class,
                reviewIncreasePointHistory1.getPointHistoryNo());

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(reviewIncreasePointHistory1.getPointHistoryNo());
    }

    @DisplayName("리뷰적립 포인트내역이 잘 조회된다.")
    @Test
    void find() {
        ReviewIncreasePointHistory actual =
            reviewIncreasePointHistoryRepository.findById(
                reviewIncreasePointHistory2.getPointHistoryNo()).get();

        assertThat(actual.getPointHistoryNo())
            .isEqualTo(reviewIncreasePointHistory2.getPointHistoryNo());
    }


    @DisplayName("리뷰적립 포인트 상세조회가 잘 이루어 진다.")
    @Test
    void findPointHistoryReviewDetailsDto() {
        ReviewResponseDto actual =
            pointHistoryRepository.findPointHistoryReviewDetailsDto(
                dummyPointHistory2.getPointHistoryNo());

        assertThat(actual.getImage())
            .isEqualTo(review.getImage());
        assertThat(actual.getProductNo())
            .isEqualTo(review.getProduct().getProductNo());
        assertThat(actual.getStarPoint())
            .isEqualTo(review.getStarPoint());
        assertThat(actual.getContent())
            .isEqualTo(review.getContent());
    }

    @DisplayName("특정 멤버의 리뷰적립 포인트 상세조회가 잘 이루어 진다.")
    @Test
    void findMyPointHistoryReviewDetailsDto() {
        ReviewResponseDto actual =
            pointHistoryRepository.findMyPointHistoryReviewDetailsDto(
                dummyPointHistory2.getPointHistoryNo(), member1.getMemberNo());

        assertThat(actual.getImage())
            .isEqualTo(review.getImage());
        assertThat(actual.getProductNo())
            .isEqualTo(review.getProduct().getProductNo());
        assertThat(actual.getStarPoint())
            .isEqualTo(review.getStarPoint());
        assertThat(actual.getContent())
            .isEqualTo(review.getContent());
    }
    
}