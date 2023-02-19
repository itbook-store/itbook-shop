package shop.itbook.itbookshop.coupongroup.categorycoupon.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
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
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.membergroup.member.dto.response.MemberExceptPwdResponseDto;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class CategoryCouponRepositoryTest {

    @Autowired
    CategoryCouponRepository categoryCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponTypeRepository couponTypeRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    TestEntityManager testEntityManager;

    Coupon amountDummyCoupon1;

    Coupon amountDummyCoupon2;

    CouponType couponType;

    Category categoryDummyBook;

    Category categoryDummyStuff;

    CategoryCoupon categoryCoupon1;

    CategoryCoupon categoryCoupon2;

    @BeforeEach
    void setUp() {

        //given
        categoryDummyBook = CategoryDummy.getCategoryNoHiddenBook();
        categoryDummyStuff = CategoryDummy.getCategoryHiddenStuff();

        categoryRepository.save(categoryDummyBook);
        categoryRepository.save(categoryDummyStuff);

        categoryDummyStuff.setParentCategory(categoryDummyBook);

        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon1 = CouponDummy.getAmountCoupon();
        amountDummyCoupon1.setCouponType(couponType);

        amountDummyCoupon2 = CouponDummy.getAmountCoupon();
        amountDummyCoupon2.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon1);
        couponRepository.save(amountDummyCoupon2);

        categoryCoupon1 = new CategoryCoupon(amountDummyCoupon1.getCouponNo(), categoryDummyStuff);
        categoryCouponRepository.save(categoryCoupon1);

        categoryCoupon2 = new CategoryCoupon(amountDummyCoupon2.getCouponNo(), categoryDummyStuff);
        categoryCouponRepository.save(categoryCoupon2);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById() {
        CategoryCoupon categoryCoupon =
            categoryCouponRepository.findById(categoryCoupon1.getCouponNo()).orElseThrow();

        assertThat(categoryCoupon.getCategory().getCategoryName()).isEqualTo(
            categoryDummyStuff.getCategoryName());
    }

    @Test
    @DisplayName("카테고리 쿠폰 리스트를 제대로 반환하는지 테스트입니다.")
    void findCategoryCouponList_success(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminCouponListResponseDto> page = categoryCouponRepository.findCategoryCouponList(pageRequest);

        //when
        List<AdminCouponListResponseDto> categoryCouponList = page.getContent();

        //then
        Assertions.assertThat(categoryCouponList).hasSize(2);
        Assertions.assertThat(categoryCouponList.get(1).getCouponType()).isEqualTo(couponType.getCouponTypeEnum().getCouponType());
        Assertions.assertThat(categoryCouponList.get(1).getCode()).isEqualTo(amountDummyCoupon1.getCode());
        Assertions.assertThat(categoryCouponList.get(1).getName()).isEqualTo(amountDummyCoupon1.getName());
        Assertions.assertThat(categoryCouponList.get(1).getCouponNo()).isEqualTo(amountDummyCoupon1.getCouponNo());
        Assertions.assertThat(categoryCouponList.get(1).getAmount()).isEqualTo(amountDummyCoupon1.getAmount());
        Assertions.assertThat(categoryCouponList.get(1).getPoint()).isEqualTo(amountDummyCoupon1.getPoint());
        Assertions.assertThat(categoryCouponList.get(1).getPercent()).isEqualTo(amountDummyCoupon1.getPercent());
        Assertions.assertThat(categoryCouponList.get(1).getCouponCreatedAt()).isEqualTo(amountDummyCoupon1.getCouponCreatedAt());
        Assertions.assertThat(categoryCouponList.get(1).getCouponExpiredAt()).isEqualTo(amountDummyCoupon1.getCouponExpiredAt());
        Assertions.assertThat(categoryCouponList.get(1).getTotalQuantity()).isEqualTo(amountDummyCoupon1.getTotalQuantity());
        Assertions.assertThat(categoryCouponList.get(1).getIssuedQuantity()).isEqualTo(amountDummyCoupon1.getIssuedQuantity());
        Assertions.assertThat(categoryCouponList.get(1).getIsDuplicateUse()).isEqualTo(amountDummyCoupon1.getIsDuplicateUse());
        Assertions.assertThat(categoryCouponList.get(1).getCategoryName()).isEqualTo(categoryDummyStuff.getCategoryName());
    }

}