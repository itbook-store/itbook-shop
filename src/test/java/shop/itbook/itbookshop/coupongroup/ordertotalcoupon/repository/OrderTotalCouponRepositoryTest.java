package shop.itbook.itbookshop.coupongroup.ordertotalcoupon.repository;

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
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.AdminCouponListResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.ordertotalcoupon.entity.OrderTotalCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.entity.ProductCoupon;
import shop.itbook.itbookshop.coupongroup.productcoupon.repository.ProductCouponRepository;
import shop.itbook.itbookshop.productgroup.product.dummy.ProductDummy;
import shop.itbook.itbookshop.productgroup.product.entity.Product;
import shop.itbook.itbookshop.productgroup.product.repository.ProductRepository;

/**
 * @author 송다혜
 * @since 1.0
 */
@DataJpaTest
class OrderTotalCouponRepositoryTest {
    @Autowired
    OrderTotalCouponRepository orderTotalCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponTypeRepository couponTypeRepository;


    @Autowired
    TestEntityManager testEntityManager;

    Coupon amountDummyCoupon1;

    Coupon amountDummyCoupon2;

    CouponType couponType;

    OrderTotalCoupon orderTotalCoupon1;

    OrderTotalCoupon orderTotalCoupon2;


    @BeforeEach
    void setUp() {

        //given

        couponType = CouponTypeDummy.getCouponType();
        couponType = couponTypeRepository.save(couponType);

        amountDummyCoupon1 = CouponDummy.getAmountCoupon();
        amountDummyCoupon1.setCouponType(couponType);

        amountDummyCoupon2 = CouponDummy.getAmountCoupon();
        amountDummyCoupon2.setCouponType(couponType);

        couponRepository.save(amountDummyCoupon1);
        couponRepository.save(amountDummyCoupon2);

        orderTotalCoupon1 = new OrderTotalCoupon(amountDummyCoupon1.getCouponNo());
        orderTotalCoupon2 = new OrderTotalCoupon(amountDummyCoupon2.getCouponNo());
        orderTotalCouponRepository.save(orderTotalCoupon1);
        orderTotalCouponRepository.save(orderTotalCoupon2);

        testEntityManager.flush();
        testEntityManager.clear();
    }

    @Test
    void findById(){
        OrderTotalCoupon orderTotalCoupon =
            orderTotalCouponRepository.findById(orderTotalCoupon1.getCouponNo()).orElseThrow();

        assertThat(orderTotalCoupon.getCoupon().getCode()).isEqualTo(amountDummyCoupon1.getCode());
    }

    @Test
    @DisplayName("총액 쿠폰 리스트를 제대로 반환하는지 테스트입니다.")
    void findCategoryCouponList_success(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<AdminCouponListResponseDto> page = orderTotalCouponRepository.findTotalCouponPageList(pageRequest);

        //when
        List<AdminCouponListResponseDto> orderTotalCouponList = page.getContent();

        //then
        Assertions.assertThat(orderTotalCouponList.size()).isEqualTo(2);

        Assertions.assertThat(orderTotalCouponList.get(1).getCouponType()).isEqualTo(couponType.getCouponTypeEnum().getCouponType());
        Assertions.assertThat(orderTotalCouponList.get(1).getIsDuplicateUse()).isEqualTo(amountDummyCoupon1.getIsDuplicateUse());
    }
}