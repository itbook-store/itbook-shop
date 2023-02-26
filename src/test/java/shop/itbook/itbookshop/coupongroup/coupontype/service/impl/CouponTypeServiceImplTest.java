package shop.itbook.itbookshop.coupongroup.coupontype.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.itbook.itbookshop.coupongroup.coupontype.coupontypeenum.CouponTypeEnum;
import shop.itbook.itbookshop.coupongroup.coupontype.dummy.CouponTypeDummy;
import shop.itbook.itbookshop.coupongroup.coupontype.entity.CouponType;
import shop.itbook.itbookshop.coupongroup.coupontype.exception.CouponTypeNotFoundException;
import shop.itbook.itbookshop.coupongroup.coupontype.repository.CouponTypeRepository;
import shop.itbook.itbookshop.coupongroup.coupontype.service.CouponTypeService;
import shop.itbook.itbookshop.coupongroup.usagestatus.exception.UsageStatusNotFoundException;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponTypeServiceImpl.class)
class CouponTypeServiceImplTest {

    @Autowired
    CouponTypeService couponTypeService;

    @MockBean
    CouponTypeRepository couponTypeRepository;

    @Test
    void findCouponType() {
        CouponType couponType = new CouponType(1, CouponTypeEnum.MEMBERSHIP_COUPON);

        given(couponTypeRepository.findByCouponTypeName(anyString())).willReturn(
            Optional.of(couponType));

        assertThat(couponTypeService.findCouponType(couponType.getCouponTypeEnum().getCouponType())
            .getCouponTypeEnum()).isEqualTo(couponType.getCouponTypeEnum());
    }

    @Test
    void findCouponType_fail() {
        CouponType couponType = new CouponType(1, CouponTypeEnum.MEMBERSHIP_COUPON);

        given(couponTypeRepository.findByCouponTypeName(anyString())).willThrow(
            new CouponTypeNotFoundException());

        CouponTypeNotFoundException exception = assertThrows(CouponTypeNotFoundException.class,
            ()-> couponTypeRepository.findByCouponTypeName("test"));
        assertThat(exception.getMessage()).isEqualTo("쿠폰 타입이 존재하지 않습니다.");
    }
}