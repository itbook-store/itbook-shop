package shop.itbook.itbookshop.coupongroup.categorycoupon.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.itbook.itbookshop.category.dummy.CategoryDummy;
import shop.itbook.itbookshop.category.entity.Category;
import shop.itbook.itbookshop.category.repository.CategoryRepository;
import shop.itbook.itbookshop.coupongroup.categorycoupon.entity.CategoryCoupon;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;

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

    Coupon amountDummyCoupon;

    CouponType couponType;

    Category categoryDummyBook;

    Category categoryDummyStuff;

    CategoryCoupon categoryCoupon;

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

        amountDummyCoupon = CouponDummy.getAmountCoupon();
        amountDummyCoupon.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon);

        categoryCoupon = new CategoryCoupon(amountDummyCoupon.getCouponNo(), categoryDummyStuff);
        categoryCouponRepository.save(categoryCoupon);
        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById(){
        CategoryCoupon categoryCoupon1 =
            categoryCouponRepository.findById(categoryCoupon.getCouponNo()).orElseThrow();

        assertThat(categoryCoupon1.getCategory().getCategoryName()).isEqualTo(categoryDummyStuff.getCategoryName());
    }

}