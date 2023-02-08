package shop.itbook.itbookshop.coupongroup.couponissue.repository;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dummy.CouponIssueDummy;
import shop.itbook.itbookshop.coupongroup.couponissue.entity.CouponIssue;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.OrderTotalCouponIssueResponseListDto;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository.OrderTotalCouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.ProductCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.coupongroup.usagestatus.dummy.UsageStatusDummy;
import shop.itbook.itbookshop.coupongroup.usagestatus.entity.UsageStatus;
import shop.itbook.itbookshop.coupongroup.usagestatus.repository.UsageStatusRepository;
import shop.itbook.itbookshop.membergroup.member.dummy.MemberDummy;
import shop.itbook.itbookshop.membergroup.member.entity.Member;
import shop.itbook.itbookshop.membergroup.member.repository.MemberRepository;
import shop.itbook.itbookshop.membergroup.membership.dummy.MembershipDummy;
import shop.itbook.itbookshop.membergroup.membership.entity.Membership;
import shop.itbook.itbookshop.membergroup.membership.repository.MembershipRepository;
import shop.itbook.itbookshop.membergroup.memberstatus.dummy.MemberStatusDummy;
import shop.itbook.itbookshop.membergroup.memberstatus.entity.MemberStatus;
import shop.itbook.itbookshop.membergroup.memberstatus.repository.MemberStatusRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CouponIssueRepositoryTest {

    @Autowired
    CouponIssueRepository couponIssueRepository;
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UsageStatusRepository usageStatusRepository;
    @Autowired
    CouponTypeRepository couponTypeRepository;
    @Autowired
    MemberStatusRepository memberStatusRepository;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OrderTotalCouponRepository orderTotalCouponRepository;
    @Autowired
    ProductCouponRepository productCouponRepository;
    @Autowired
    CategoryCouponRepository categoryCouponRepository;

    Membership membership;
    MemberStatus memberStatus;
    UsageStatus availableUsageStatus;
    UsageStatus completedUsageStatus;
    Member member1;
    Member member2;
    Category category;
    Product product;
    CouponType nomalCouponType;
    CouponType welcomeCouponType;
    List<OrderTotalCoupon> orderTotalCouponList;
    List<CategoryCoupon> categoryCouponList;
    List<ProductCoupon> productCouponList;
    List<Coupon> couponList;
    List<CouponIssue> couponIssueList;

    @BeforeEach
    void setup() {

        //상태코드
        availableUsageStatus =
            usageStatusRepository.save(UsageStatusDummy.getAvailableUsageStatus());
        completedUsageStatus =
            usageStatusRepository.save(UsageStatusDummy.getCompletedUsageStatus());
        nomalCouponType = couponTypeRepository.save(CouponTypeDummy.getCouponType());
        welcomeCouponType = couponTypeRepository.save(CouponTypeDummy.getWelcomeCouponType());
        memberStatus = memberStatusRepository.save(MemberStatusDummy.getWithdrawMemberStatus());
        membership = membershipRepository.save(MembershipDummy.getMembership());

        //couponIssue 에 들어갈 값
        member1 = MemberDummy.getMember1();
        member1.setMemberStatus(memberStatus);
        member1.setMembership(membership);

        member2 = MemberDummy.getMember1();
        member2.setMemberStatus(memberStatus);
        member2.setMembership(membership);

        memberRepository.save(member1);
        memberRepository.save(member2);

        product = ProductDummy.getProductSuccess();
        category = CategoryDummy.getCategoryNoHiddenBook();

        productRepository.save(product);
        categoryRepository.save(category);

        couponList = new ArrayList<>();
        orderTotalCouponList = new ArrayList<>();
        categoryCouponList = new ArrayList<>();
        productCouponList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Coupon coupon = CouponDummy.getPercentCoupon();
            if (i % 2 == 0) {
                coupon.setCouponType(welcomeCouponType);
            } else {
                coupon.setCouponType(nomalCouponType);
            }
            if (i % 3 == 0) {
                coupon.setName("OrderTotalCoupon");
                couponList.add(couponRepository.save(coupon));
                orderTotalCouponList.add(
                    orderTotalCouponRepository.save(new OrderTotalCoupon(coupon.getCouponNo())));
            } else if (i % 3 == 1) {
                coupon.setName("CategoryCoupon");
                couponList.add(couponRepository.save(coupon));
                categoryCouponList.add(categoryCouponRepository.save(
                    new CategoryCoupon(coupon.getCouponNo(), category)));
            } else {
                coupon.setName("ProductCoupon");
                couponList.add(couponRepository.save(coupon));
                productCouponList.add(
                    productCouponRepository.save(new ProductCoupon(coupon.getCouponNo(), product)));
            }
        }

        couponIssueList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CouponIssue couponIssue = CouponIssueDummy.getCouponIssue();
            if (i % 2 == 0) {
                couponIssue.setMember(member1);

            } else {
                couponIssue.setMember(member2);
            }
            couponIssue.setCoupon(couponList.get(i / 2));
            couponIssue.setUsageStatus(availableUsageStatus);
            if (i % 5 == 0) {
                couponIssue.setUsageStatus(completedUsageStatus);
            }
            if (i % 7 == 0) {
                couponIssue.setCouponExpiredAt(LocalDateTime.now().minusDays(1));
            }
            couponIssueList.add(couponIssueRepository.save(couponIssue));
        }
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void find_by_id() {
        CouponIssue couponIssue1 =
            couponIssueRepository.findById(couponIssueList.get(0).getCouponIssueNo()).orElseThrow();

        assertThat(couponIssue1.getCoupon().getCode()).isEqualTo(
            couponIssueList.get(0).getCoupon().getCode());
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 모든 주문 총액 쿠폰이 불러지는지 확인")
    void findAvailableOrderTotalCouponIssueByMemberNo_success_test() {
        List<OrderTotalCouponIssueResponseListDto> result1 =
            couponIssueRepository.findAvailableOrderTotalCouponIssueByMemberNo(
                member1.getMemberNo());

        assertThat(result1).hasSize(3);
        for(int i = 0; i<result1.size(); i++){
            CouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("OrderTotalCoupon");
            assertThat(result1.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result1.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }

        List<OrderTotalCouponIssueResponseListDto> result2 =
            couponIssueRepository.findAvailableOrderTotalCouponIssueByMemberNo(
                member2.getMemberNo());

        assertThat(result2).hasSize(3);
        for(int i = 0; i<result2.size(); i++){
            CouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("OrderTotalCoupon");
            assertThat(result2.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result2.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 모든 카테고리 쿠폰이 불러지는지 확인")
    void findAvailableCategoryCouponIssueByMemberNo_success_test() {
        List<CategoryCouponIssueListResponseDto> result1 =
            couponIssueRepository.findAvailableCategoryCouponIssueByMemberNo(
                member1.getMemberNo());

        assertThat(result1).hasSize(2);
        for(int i = 0; i<result1.size(); i++){
            CouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("CategoryCoupon");
            assertThat(result1.get(i).getCategoryNo()).isEqualTo(category.getCategoryNo());
            assertThat(result1.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result1.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }

        List<CategoryCouponIssueListResponseDto> result2 =
            couponIssueRepository.findAvailableCategoryCouponIssueByMemberNo(
                member2.getMemberNo());

        assertThat(result2).hasSize(2);
        for(int i = 0; i<result2.size(); i++){
            CouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("CategoryCoupon");
            assertThat(result2.get(i).getCategoryNo()).isEqualTo(category.getCategoryNo());
            assertThat(result2.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result2.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 모든 상품 쿠폰이 불러지는지 확인")
    void findAvailableProductCouponIssueByMemberNo_success_test() {
        List<ProductCouponIssueListResponseDto> result1 =
            couponIssueRepository.findAvailableProductCouponIssueByMemberNo(
                member1.getMemberNo());

        assertThat(result1).hasSize(2);
        for(int i = 0; i<result1.size(); i++){
            CouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("ProductCoupon");
            assertThat(result1.get(i).getProductNo()).isEqualTo(product.getProductNo());
            assertThat(result1.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result1.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }

        List<ProductCouponIssueListResponseDto> result2 =
            couponIssueRepository.findAvailableProductCouponIssueByMemberNo(
                member2.getMemberNo());

        assertThat(result2).hasSize(2);
        for(int i = 0; i<result2.size(); i++){
            CouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("ProductCoupon");
            assertThat(result2.get(i).getProductNo()).isEqualTo(product.getProductNo());
            assertThat(result2.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result2.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }
    }
}