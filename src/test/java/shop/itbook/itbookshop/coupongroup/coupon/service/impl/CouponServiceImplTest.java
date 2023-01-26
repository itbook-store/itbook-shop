package shop.itbook.itbookshop.coupongroup.coupon.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.itbook.itbookshop.coupongroup.coupon.dto.request.CouponRequestDto;
import shop.itbook.itbookshop.coupongroup.coupon.dto.response.CouponResponseDto;
import shop.itbook.itbookshop.coupongroup.coupon.dummy.CouponDummy;
import shop.itbook.itbookshop.coupongroup.coupon.entity.Coupon;
import shop.itbook.itbookshop.coupongroup.coupon.repository.CouponRepository;
import shop.itbook.itbookshop.coupongroup.coupon.service.CouponService;
import shop.itbook.itbookshop.coupongroup.coupon.transfer.CouponTransfer;

/**
 * @author 송다혜
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(CouponServiceImpl.class)
class CouponServiceImplTest {

    @Autowired
    CouponService couponService;

    @MockBean
    CouponRepository couponRepository;

    Coupon percentDummyCoupon;

    CouponRequestDto couponRequestDto;
    @BeforeEach
    public void setup(){
        percentDummyCoupon = CouponDummy.getPercentCoupon();

        couponRequestDto = new CouponRequestDto();
        ReflectionTestUtils.setField(couponRequestDto, "name", "쿠폰이름");

    }
    @Test
    void addCoupon() {
        //given
        percentDummyCoupon.setCouponNo(1L);

        given(couponRepository.save(any(Coupon.class)))
            .willReturn(percentDummyCoupon);

        //when
        Long actual = couponService.addCoupon(couponRequestDto);

        //then
        assertThat(actual).isEqualTo(percentDummyCoupon.getCouponNo());

    }

    @Test
    void deleteCoupon() {

        couponService.deleteCoupon(0L);
        verify(couponRepository).deleteById(0L);
    }

    @Test
    void findByCouponResponseDto() {
        //given
        ReflectionTestUtils.setField(couponRequestDto, "code", percentDummyCoupon.getCode());

        given(couponRepository.findCouponByCode(anyString()))
            .willReturn(Optional.of(percentDummyCoupon));

        //when
        CouponResponseDto actual = couponService.findByCouponResponseDto(percentDummyCoupon.getCode());

        //then
        assertThat(actual.getCode()).isEqualTo(percentDummyCoupon.getCode());

    }
}