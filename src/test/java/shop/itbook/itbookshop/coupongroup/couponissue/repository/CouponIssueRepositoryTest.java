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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponSimpleListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.AdminCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.CategoryCouponIssueListResponseDto;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.categorycoupon.repository.CategoryCouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.OrderCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.couponissue.dto.response.UserCouponIssueListResponseDto;
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
import shop.itbook.itbookshop.productgroup.productcategory.entity.ProductCategory;
import shop.itbook.itbookshop.productgroup.productcategory.repository.ProductCategoryRepository;

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

    @Autowired
    ProductCategoryRepository productCategoryRepository;

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
    ProductCategory productCategory;
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

        productCategory = productCategoryRepository.save(new ProductCategory(product, category));
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
            OrderCouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("OrderTotalCoupon");
            assertThat(result1.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result1.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }

        List<OrderTotalCouponIssueResponseListDto> result2 =
            couponIssueRepository.findAvailableOrderTotalCouponIssueByMemberNo(
                member2.getMemberNo());

        assertThat(result2).hasSize(3);
        for(int i = 0; i<result2.size(); i++){
            OrderCouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
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
            OrderCouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
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
            OrderCouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
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
            OrderCouponListResponseDto couponResult = result1.get(i).getCouponListResponseDto();
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
            OrderCouponListResponseDto couponResult = result2.get(i).getCouponListResponseDto();
            assertThat(couponResult.getName()).isEqualTo("ProductCoupon");
            assertThat(result2.get(i).getProductNo()).isEqualTo(product.getProductNo());
            assertThat(result2.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result2.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
        }
    }

    @Test
    @DisplayName("멤버가 가지고 있는 모든 쿠폰이 페이지네이션되어 불러지는지 확인")
    void findCouponIssueListByMemberNo() {

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserCouponIssueListResponseDto> page =
            couponIssueRepository.findCouponIssueListByMemberNo(pageRequest, member1.getMemberNo());

        List<UserCouponIssueListResponseDto> result = page.getContent();

        assertThat(result).hasSize(10);
        for(int i = 0; i<result.size(); i++){
            assertThat(result.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result.get(i).getCouponIssueCreatedAt()).isBefore(LocalDateTime.now());
        }
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 쿠폰이 페이지네이션 되어 불러지는지 확인")
    void findAvailableCouponIssueListByMemberNo() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserCouponIssueListResponseDto> page =
            couponIssueRepository.findAvailableCouponIssueListByMemberNo(pageRequest, member1.getMemberNo());

        List<UserCouponIssueListResponseDto> result = page.getContent();

        assertThat(result).hasSize(7);
        for(int i = 0; i<result.size(); i++){
            assertThat(result.get(i).getCouponIssueNo()).isNotNull();
            assertThat(result.get(i).getCouponExpiredAt()).isAfter(LocalDateTime.now());
            assertThat(result.get(i).getCouponUsageCreatedAt()).isNull();
        }
    }


    @Test
    @DisplayName("멤버가 가지고 있는 사용 불가능한 쿠폰이 페이지네이션 되어 불러지는지 확인")
    void findNotAvailableCouponIssueListByMemberNo() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserCouponIssueListResponseDto> page =
            couponIssueRepository.findNotAvailableCouponIssueListByMemberNo(pageRequest, member1.getMemberNo());

        List<UserCouponIssueListResponseDto> result = page.getContent();

        assertThat(result).hasSize(2);

        Page<UserCouponIssueListResponseDto> page1 =
            couponIssueRepository.findNotAvailableCouponIssueListByMemberNo(pageRequest, member2.getMemberNo());

        List<UserCouponIssueListResponseDto> result1 = page1.getContent();

        assertThat(result).hasSize(2);
    }


    @Test
    @DisplayName("멤버가 가지고 있는 쿠폰중 기간 지난 쿠폰을 가져오는 메소드")
    void changePeriodExpiredByMemberNo() {
        List<CouponIssue> couponIssues = couponIssueRepository.changePeriodExpiredByMemberNo(member1.getMemberNo());

        assertThat(couponIssues).hasSize(1);
        for (CouponIssue couponIssue : couponIssues){
            assertThat(couponIssue.getCouponUsageCreatedAt()).isNull();
            assertThat(couponIssue.getCouponExpiredAt()).isBefore(LocalDateTime.now());
        }
    }


    @Test
    @DisplayName("fetchjoin 해서 쿠폰 이슈 한개 가져오기")
    void findByIdFetchJoin() {
        CouponIssue couponIssue = couponIssueList.get(0);
        CouponIssue result = couponIssueRepository.findByIdFetchJoin(couponIssue.getCouponIssueNo());

        assertThat(result.getCouponIssueNo()).isEqualTo(couponIssue.getCouponIssueNo());
        assertThat(result.getMember().getMemberNo()).isEqualTo(couponIssue.getMember().getMemberNo());
        assertThat(result.getCoupon().getCouponNo()).isEqualTo(couponIssue.getCoupon().getCouponNo());
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 모든 상품 쿠폰이 불러지는지 확인")
    void findAvailableProductCouponByMemberNoAndProductNo() {

        Long memberNo = couponIssueList.get(1).getMember().getMemberNo();
        Long productNo = productCouponList.get(0).getProduct().getProductNo();
        List<OrderCouponSimpleListResponseDto> couponList =
            couponIssueRepository.findAvailableProductCouponByMemberNoAndProductNo(memberNo, productNo);

        assertThat(couponList).hasSize(2);
    }

    @Test
    @DisplayName("멤버가 가지고 있는 사용 가능한 모든 카테고리 쿠폰이 불러지는지 확인")
    void findAvailableCategoryCouponByMemberNoAndProductNo() {

        Long memberNo = couponIssueList.get(0).getMember().getMemberNo();
        Long productNo = productCouponList.get(0).getProduct().getProductNo();
        List<OrderCouponSimpleListResponseDto> couponList =
            couponIssueRepository.findAvailableCategoryCouponByMemberNoAndProductNo(memberNo, productNo);

        assertThat(couponList).hasSize(2);
    }

    @Test
    @DisplayName("한 멤버의 사용가능한 모든 총액 쿠폰을 사용")
    void findAvailableTotalCouponByMemberNo() {

        List<OrderCouponSimpleListResponseDto> couponList =
            couponIssueRepository.findAvailableTotalCouponByMemberNo(member1.getMemberNo());

        assertThat(couponList).hasSize(3);
    }

    @Test
    @DisplayName("모든 쿠폰을 페이지네이션해서 가져오는지 확인")
    void findAllCouponIssue() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<AdminCouponIssueListResponseDto> page =
            couponIssueRepository.findAllCouponIssue(pageRequest);

        List<AdminCouponIssueListResponseDto> couponList = page.getContent();
        assertThat(couponList).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(20);
    }

    @Test
    @DisplayName("쿠폰 멤버아이디로 검")
    void findCouponIssueSearchMemberId() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<AdminCouponIssueListResponseDto> page =
            couponIssueRepository.findCouponIssueSearchMemberId(pageRequest, couponIssueList.get(0).getMember()
                .getMemberId());

        List<AdminCouponIssueListResponseDto> couponList = page.getContent();
        assertThat(couponList).hasSize(10);
        for(AdminCouponIssueListResponseDto coupon : couponList){
            assertThat(coupon.getMemberId()).isEqualTo(couponIssueList.get(0).getMember().getMemberId());
        }
    }

    @Test
    @DisplayName("쿠폰 이름으로 검색")
    void findCouponIssueSearchCouponName() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<AdminCouponIssueListResponseDto> page =
            couponIssueRepository.findCouponIssueSearchCouponName(pageRequest, couponIssueList.get(0).getCoupon().getName());

        List<AdminCouponIssueListResponseDto> couponList = page.getContent();
        assertThat(couponList).hasSize(8);
        for(AdminCouponIssueListResponseDto coupon : couponList){
            assertThat(coupon.getName()).isEqualTo(couponIssueList.get(0).getCoupon().getName());
        }
    }

    @Test
    @DisplayName("쿠폰 코드로 검색")
    void findCouponIssueSearchCouponCode() {

        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<AdminCouponIssueListResponseDto> page =
            couponIssueRepository.findCouponIssueSearchCouponCode(pageRequest, couponIssueList.get(0).getCoupon().getCode());

        List<AdminCouponIssueListResponseDto> couponList = page.getContent();
        assertThat(couponList).hasSize(2);
        for(AdminCouponIssueListResponseDto coupon : couponList){
            assertThat(coupon.getCode()).isEqualTo(couponIssueList.get(0).getCoupon().getCode());
        }
    }
}